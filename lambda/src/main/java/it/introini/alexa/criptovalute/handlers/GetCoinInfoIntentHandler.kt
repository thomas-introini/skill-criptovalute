package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import it.introini.alexa.criptovalute.model.*
import it.introini.alexa.criptovalute.util.SkillUtils
import it.introini.alexa.criptovalute.util.SlotsUtils
import it.introini.alexa.criptovalute.util.coinNotRecognized
import kotlinx.coroutines.runBlocking
import java.util.*
import drewcarlson.coingecko.CoinGeckoClient

class GetCoinInfoIntentHandler : AbstractRequestHandler("GetCoinInfoIntent") {
    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val cointSlot = SlotsUtils.getRequiredResolvedValue(intentRequest, "coin")
        val currencySlot = SlotsUtils.getOptionalResolvedValue(intentRequest, "currency")
        when (cointSlot) {
            is UnresolvedRequiredSlotValue -> {
                return coinNotRecognized(input, cointSlot.input)
            }
            is ResolvedRequiredSlotValue -> {
                val coinInfo = runBlocking {
                    CoinGeckoClient.create().getCoinById(
                        id = cointSlot.resolvedValue.id,
                        marketData = true
                    )
                }

                val currency = when (currencySlot) {
                    is ResolvedOptionalSlotValue -> currencySlot.resolvedValue
                    is UnresolvedOptionaSlotValue -> DefaultCurrencyValue
                    EmptySlotValue -> DefaultCurrencyValue
                }
                val currentPrice = coinInfo.marketData?.currentPrice?.get(currency.id)
                    ?: return input.responseBuilder
                        .withSpeech(SkillUtils.m(input, "COIN_DATA_NOT_AVAILABLE"))
                        .withShouldEndSession(true)
                        .build()

                val speech = SkillUtils.m(input, "COIN_CURRENT_DATA", coinInfo.name, currentPrice.toString(), currency.name)
                return input.responseBuilder
                    .withSpeech(speech)
                    .withSimpleCard(coinInfo.name, speech)
                    .withShouldEndSession(true)
                    .build()
            }
        }
    }
}
