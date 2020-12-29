package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import drewcarlson.coingecko.CoinGeckoService
import it.introini.alexa.criptovalute.util.SkillUtils
import it.introini.alexa.criptovalute.util.speechResponse
import kotlinx.coroutines.runBlocking
import java.util.*

class GetFavoriteCoinsUpdatesIntentHandler: AbstractRequestHandler("GetFavoriteCoinsUpdatesIntent") {
    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val persistentAttributes = input.attributesManager.persistentAttributes
        val coins = persistentAttributes["favoriteCoins"] as? MutableMap<String, String>
        if (coins == null) {
            return input.responseBuilder
                .withSpeech(SkillUtils.m(input, "FAVORITE_COIN_LIST_EMPTY")).build()
        } else {
            val coingecko = CoinGeckoService()
            val currency = "eur"
            val prices = runBlocking {
                coingecko.getPrice(
                    ids = coins.keys.joinToString(","),
                    vsCurrencies = currency,
                    include24hrChange = true
                )
            }
            val priceString = prices.map {
                val coinName = coins[it.key]
                val price = it.value.getPrice(currency)
                "$coinName : $price euro"
            }.joinToString(", ")
            return input.speechResponse("FAVORITE_COIN_UPDATES", true, priceString)
        }
    }
}
