package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.model.IntentRequest
import com.amazon.ask.model.Response
import com.amazon.ask.request.Predicates
import it.introini.alexa.criptovalute.util.canHandleIntent
import it.introini.alexa.criptovalute.util.speechResponse
import java.util.Optional

class HelpIntentHandler : AbstractRequestHandler("AMAZON.HelpIntent") {
    override fun handle(input: HandlerInput, intentRequest: IntentRequest) =
        input.speechResponse("HELP", shouldEndSession = false)
}
