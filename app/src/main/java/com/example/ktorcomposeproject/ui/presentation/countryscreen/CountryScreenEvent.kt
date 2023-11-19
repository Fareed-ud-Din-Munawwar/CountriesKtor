package com.example.ktorcomposeproject.ui.presentation.countryscreen

import com.example.ktorcomposeproject.data.Country

sealed class CountryScreenEvent {
    data class OnNewsCardClicked(var country: Country) : CountryScreenEvent()
    data class OnCategoryChanged(var category: String) : CountryScreenEvent()
    data class OnSearchQueryChanged(var searchQuery: String) : CountryScreenEvent()
    object OnSearchIconClicked: CountryScreenEvent()
    object OnCloseIconClicked: CountryScreenEvent()
}
