package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import it.introini.alexa.criptovalute.util.SkillUtils
import java.util.*

class ListFavoriteCoinsIntentHandler: AbstractRequestHandler("ListFavoriteCoinsIntent") {
    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> {
        val persistentAttributes = input.attributesManager.persistentAttributes
        val coins = persistentAttributes["favoriteCoins"] as? MutableMap<String, String>
        return if (coins == null) {
            input.responseBuilder
                .withSpeech(SkillUtils.m(input, "FAVORITE_COIN_LIST_EMPTY")).build()
        } else {
            input.responseBuilder
                .withSpeech(SkillUtils.m(input, "FAVORITE_COIN_LIST", coins.values.sorted().joinToString(","))).build()
        }
    }
}
