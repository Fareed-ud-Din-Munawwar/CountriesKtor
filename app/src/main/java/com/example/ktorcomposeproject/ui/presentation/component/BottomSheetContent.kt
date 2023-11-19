package com.example.ktorcomposeproject.ui.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ktorcomposeproject.ui.presentation.countryscreen.CountryScreenState
import com.example.ktorcomposeproject.ui.presentation.countryscreen.ImageHolder

@Composable
fun BottomSheetContent(
    state: CountryScreenState,
) {
    val country = state.detailedCountry
    Surface(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                    ,horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = country?.name ?: "",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = country?.smallDescription ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            ImageHolder(imageUrl = country?.imageUrl ?: "")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = country?.detailedDescription ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Independence Date: ",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = country?.independenceDate ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}