package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import it.introini.alexa.criptovalute.model.ResolvedRequiredSlotValue
import it.introini.alexa.criptovalute.model.UnresolvedRequiredSlotValue
import it.introini.alexa.criptovalute.util.SkillUtils
import it.introini.alexa.criptovalute.util.SlotsUtils
import it.introini.alexa.criptovalute.util.coinNotRecognized
import java.util.*

class RemoveFavoriteCoinIntentHandler: AbstractRequestHandler("RemoveFavoriteCoinIntent") {
    override fun handle(input: HandlerInput, intentRequest: IntentRequest): Optional<Response> =
        when (val slot = SlotsUtils.getRequiredResolvedValue(intentRequest, "coin")) {
            is UnresolvedRequiredSlotValue -> coinNotRecognized(input, slot.input)
            is ResolvedRequiredSlotValue -> {
                val persistentAttributes = input.attributesManager.persistentAttributes
                val coins = persistentAttributes["favoriteCoins"] as? MutableMap<String, String> ?: mutableMapOf()
                val resolvedValue = slot.resolvedValue
                if (coins.containsKey(resolvedValue.id)) {
                    coins.remove(resolvedValue.id)
                    input.attributesManager.savePersistentAttributes()
                }
                input.responseBuilder.withSpeech(SkillUtils.m(input, "FAVORITE_COIN_REMOVED", resolvedValue.name)).build()
            }
        }

}
