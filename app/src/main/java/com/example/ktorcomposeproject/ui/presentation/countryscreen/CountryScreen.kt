package com.example.ktorcomposeproject.ui.presentation.countryscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ktorcomposeproject.data.Country
import com.example.ktorcomposeproject.ui.presentation.component.BottomSheetContent
import com.example.ktorcomposeproject.ui.presentation.component.CategoryTabRow
import com.example.ktorcomposeproject.ui.presentation.component.CountryScreenTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun CountryScreen(
    state: CountryScreenState,
    onEvent: (CountryScreenEvent) -> Unit,
    onReadFullStoryButtonClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val categories = listOf(
        "Random", "All"
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var shouldBottomSheetShow by remember { mutableStateOf(false) }


    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.selectedCountry?.let {
                    BottomSheetContent(
                        state
                       /* onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it.url)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) shouldBottomSheetShow = false
                            }
                        }*/
                    )
                }
            }
        )
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(CountryScreenEvent.OnCategoryChanged(category = categories[page]))
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                CountryScreenTopBar(
                    scrollBehavior = scrollBehavior,
                )
                Divider(color = Color.Black, thickness = 2.dp)
            }

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CategoryTabRow(
                pagerState = pagerState,
                categories = categories,
                onTabSelected = { index ->
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                }
            )

            HorizontalPager(
                pageCount = categories.size,
                state = pagerState
            ) {
            CountryList(
                    state = state,
                    onCardClicked = { country ->
                        shouldBottomSheetShow = true
                        onEvent(CountryScreenEvent.OnNewsCardClicked(country = country))
                    },
                    onRetry = {
                        onEvent(CountryScreenEvent.OnCategoryChanged(state.category))
                    }
                )
            }
        }
    }
}


@Composable
fun CountryList(
    state: CountryScreenState,
    onCardClicked: (Country) -> Unit,
    onRetry: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.countries) { article ->
            CountryCard(
                country = article,
                onCardClicked = onCardClicked
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        /*if (state.error != null) {
            RetryContent(
                error = state.error,
                onRetry = onRetry
            )
        }*/
    }
}

@Composable
fun CountryCard(
    modifier: Modifier = Modifier,
    country: Country,
    onCardClicked: (Country) -> Unit
) {
    Card(
        modifier = modifier.clickable { onCardClicked(country) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ImageHolder(imageUrl = country.imageUrl)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = country.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ImageHolder(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Image",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        //placeholder = painterResource(R.drawable.placeholder_loading),
        //error = painterResource(R.drawable.placeholder_news)
    )
}
