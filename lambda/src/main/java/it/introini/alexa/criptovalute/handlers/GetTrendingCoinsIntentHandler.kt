package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import drewcarlson.coingecko.CoinGeckoClient
import it.introini.alexa.criptovalute.model.DefaultCurrencyValue
import it.introini.alexa.criptovalute.model.EmptySlotValue
import it.introini.alexa.criptovalute.model.ResolvedOptionalSlotValue
import it.introini.alexa.criptovalute.model.UnresolvedOptionaSlotValue
import it.introini.alexa.criptovalute.util.SlotsUtils
import it.introini.alexa.criptovalute.util.speechResponse
import kotlinx.coroutines.runBlocking
import java.util.*

class GetTrendingCoinsIntentHandler: AbstractRequestHandler("GetTrendingCoinsIntent") {
    override fun handle(input: HandlerInput, request: IntentRequest): Optional<Response> {
        val currency = when (val currencySlot = SlotsUtils.getOptionalResolvedValue(request, "currency")) {
            is ResolvedOptionalSlotValue -> currencySlot.resolvedValue
            is UnresolvedOptionaSlotValue -> DefaultCurrencyValue
            EmptySlotValue -> DefaultCurrencyValue
        }

        val trending = runBlocking {
            val geckoClient = CoinGeckoClient.create()
            val coins = geckoClient.getTrending().coins
            val priceChangeMap = geckoClient.getCoinMarkets(
                vsCurrency = currency.id,
                ids = coins.joinToString(",") { it.item.id },
            ).markets.associateBy { it.id }
            coins.filter { priceChangeMap.containsKey(it.item.id) }.map {
                val coin = priceChangeMap[it.item.id]!!
                Triple(it.item.name, coin.currentPrice, coin.priceChangePercentage24h)
            }
        }

        return input.speechResponse("TRENDING_COIN_UPDATES", true, trending.joinToString { "${it.first}: ${it.second} $currency, ${it.third.toString().format("%.2f")}" })
    }
}