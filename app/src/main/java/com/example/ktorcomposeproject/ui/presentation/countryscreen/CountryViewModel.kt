package com.example.ktorcomposeproject.ui.presentation.countryscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktorcomposeproject.domain.repository.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val repo: CountryRepository) : ViewModel() {

    var state by mutableStateOf(CountryScreenState())

    fun onEvent(event: CountryScreenEvent) {
        when (event) {
            is CountryScreenEvent.OnCategoryChanged -> {
                state = state.copy(category = event.category)
                getCountries(category = state.category)
            }
            is CountryScreenEvent.OnNewsCardClicked -> {
                state = state.copy(selectedCountry = event.country)
                getCountryDetail(event.country.id)
            }
            else -> {

            }
        }
    }

    private fun getCountryDetail(id: Int) {
        viewModelScope.launch {
            try {
                val result = repo.getCountryDetails(id)
                state = state.copy(detailedCountry = result)
            }
            catch (e:Exception){
                Log.e("exception",e.message.toString())
            }
        }
    }

    private fun getCountries(category: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val result = if (category == "Random") {
                    repo.getRandomCountry()
                } else {
                    repo.getListOfCountries()
                }
                state = state.copy(
                    countries = result ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            } catch (ex: Exception) {
                state = state.copy(
                    countries = emptyList(),
                    isLoading = false,
                    error = "Error"
                )
            }
        }
    }
}
