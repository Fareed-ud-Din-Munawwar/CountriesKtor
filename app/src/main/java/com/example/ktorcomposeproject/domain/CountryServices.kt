package com.example.ktorcomposeproject.domain

import com.example.ktorcomposeproject.data.Country
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryServices @Inject constructor(val client: HttpClient) {

    val GET_COUNTRIES="/country?countryId="
    val GET_RANDOM_COUNTRY="randomCountry"

    suspend fun getListOfCountries(): HttpResponse =
        client.get(BASE_URL)

    suspend fun getCountryDetails(id:Int) =
        client.get("$BASE_URL$GET_COUNTRIES$id")

    suspend fun getRandomCountry() =
        client.get("$BASE_URL$GET_RANDOM_COUNTRY")

    companion object{
        const val BASE_URL = "http://192.168.18.104:8100/"
    }
}