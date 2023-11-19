package com.example.ktorcomposeproject.data.remote

import com.example.ktorcomposeproject.data.Country
import com.example.ktorcomposeproject.data.CountryDetails
import com.example.ktorcomposeproject.domain.CountryServices
import com.example.ktorcomposeproject.domain.repository.CountryRepository
import io.ktor.client.call.body

class CountryRepositoryImpl(
    private val services: CountryServices
): CountryRepository {
    override suspend fun getListOfCountries(): List<Country> {
        return services.getListOfCountries().body()
    }

    override suspend fun getCountryDetails(id: Int): CountryDetails {
        return services.getCountryDetails(id).body()
    }

    override suspend fun getRandomCountry(): List<Country> {
        return listOf(services.getRandomCountry().body())
    }

}