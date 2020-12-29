package it.introini.alexa.criptovalute.model

import com.amazon.ask.model.slu.entityresolution.Value

sealed class RequiredSlotValue
sealed class OptionalSlotValue

data class ResolvedRequiredSlotValue(
        val input: String,
        val resolvedValue: Value
): RequiredSlotValue()

data class UnresolvedRequiredSlotValue(val input: String): RequiredSlotValue()

data class ResolvedOptionalSlotValue(
    val input: String,
    val resolvedValue: Value
): OptionalSlotValue()

object EmptySlotValue: OptionalSlotValue()
data class UnresolvedOptionaSlotValue(val input: String) : OptionalSlotValue()


val DefaultCurrencyValue: Value = Value.builder().withId("eur").withName("Euro").build()
