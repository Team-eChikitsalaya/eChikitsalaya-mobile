package com.codeitcreative.echikitsalaya

import RocketLaunch
import daysUntilNewYear
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.random.Random

class Greeting {
    private val platform: Platform = getPlatform()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun greet(): List<String> = buildList {
        val rockets: List<RocketLaunch> =
            httpClient.get("https://api.spacexdata.com/v4/launches").body()
        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
        add(if (Random.nextBoolean()) "Hi! from eChikitsalaya" else "Hello eChikitsalaya!")
        add("Guess what it is! > ${platform.name.reversed()}!")
        add("\nThere are only ${daysUntilNewYear()} days left until New Year! 🎆")
        add("\nThe last successful launch was ${lastSuccessLaunch.launchDateUTC} 🚀")
    }

}