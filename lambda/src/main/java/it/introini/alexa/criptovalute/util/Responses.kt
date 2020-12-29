package it.introini.alexa.criptovalute.util

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import java.util.*


fun coinNotRecognized(input: HandlerInput, coin: String): Optional<Response> =
    input.responseBuilder.withSpeech(SkillUtils.m(input, "COIN_NOT_RECOGNIZED", coin)).withShouldEndSession(true).build()

fun HandlerInput.canHandleIntent(intentName: String) =
    matches(Predicates.intentName(intentName))

fun HandlerInput.speechResponse(message: String, shouldEndSession: Boolean = true, vararg variables: String): Optional<Response> =
    responseBuilder.withSpeech(SkillUtils.m(this, message, *variables)).withShouldEndSession(shouldEndSession).build()
