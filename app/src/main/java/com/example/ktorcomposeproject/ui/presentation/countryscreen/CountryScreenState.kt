package com.example.ktorcomposeproject.ui.presentation.countryscreen

import com.example.ktorcomposeproject.data.Country
import com.example.ktorcomposeproject.data.CountryDetails

data class CountryScreenState (
    val isLoading: Boolean = false,
    val countries: List<Country> = emptyList(),
    val error: String? = null,
    val isSearchBarVisible: Boolean= false,
    val selectedCountry: Country? = null,
    val category: String = "General",
    val searchQuery: String = "",
    val detailedCountry: CountryDetails? = null,
)