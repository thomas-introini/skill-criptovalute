package it.introini.alexa.criptovalute.util

import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Slot
import com.amazon.ask.model.slu.entityresolution.Resolution
import com.amazon.ask.model.slu.entityresolution.StatusCode
import it.introini.alexa.criptovalute.model.*

object SlotsUtils {
    fun getRequiredResolvedValue(intentRequest: IntentRequest, slotName: String): RequiredSlotValue {
        val slot = intentRequest.intent.slots[slotName]
                ?: throw IllegalStateException("slot $slotName is required, but was not found")
        val resolutionCoin = getResolution(slot)

        return if (resolutionCoin == null) {
            UnresolvedRequiredSlotValue(slot.value)
        } else {
            ResolvedRequiredSlotValue(
                slot.value,
                resolutionCoin.values.map { it.value }.first()
            )
        }
    }

    fun getOptionalResolvedValue(intentRequest: IntentRequest, slotName: String): OptionalSlotValue {
        val slot = intentRequest.intent.slots[slotName] ?: return EmptySlotValue
        val resolution = getResolution(slot)

        return if (resolution == null) {
            UnresolvedOptionaSlotValue(slot.value)
        } else {
            ResolvedOptionalSlotValue(
                slot.value,
                resolution.values.map { it.value }.first()
            )
        }
    }

    private fun getResolution(slot: Slot): Resolution? {
        return slot.resolutions
                .resolutionsPerAuthority
                .firstOrNull { r: Resolution -> r.status.code == StatusCode.ER_SUCCESS_MATCH }
    }
}
