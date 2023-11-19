package com.example.ktorcomposeproject.domain.repository

import com.example.ktorcomposeproject.data.Country
import com.example.ktorcomposeproject.data.CountryDetails
import io.ktor.client.statement.HttpResponse

interface CountryRepository {
    suspend fun getListOfCountries() : List<Country>
    suspend fun getCountryDetails(id:Int) : CountryDetails
    suspend fun getRandomCountry(): List<Country>
}