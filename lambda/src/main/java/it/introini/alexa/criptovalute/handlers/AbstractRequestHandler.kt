package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler
import com.amazon.ask.model.IntentRequest
import it.introini.alexa.criptovalute.util.canHandleIntent

abstract class AbstractRequestHandler(private val intentName: String): IntentRequestHandler {
    override fun canHandle(input: HandlerInput, intentRequest: IntentRequest) = canHandle(input)
    override fun canHandle(input: HandlerInput) = input.canHandleIntent(intentName)
}
