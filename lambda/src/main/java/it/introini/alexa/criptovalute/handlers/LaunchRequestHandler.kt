/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at
         http://aws.amazon.com/apache2.0/
     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/
package it.introini.alexa.criptovalute.handlers

import com.amazon.ask.dispatcher.request.handler.HandlerInput
import com.amazon.ask.dispatcher.request.handler.RequestHandler
import com.amazon.ask.request.Predicates
import com.amazon.ask.model.LaunchRequest
import com.amazon.ask.model.Response
import java.util.Optional
import it.introini.alexa.criptovalute.util.SkillUtils

class LaunchRequestHandler : RequestHandler {
    override fun canHandle(input: HandlerInput): Boolean {
        return input.matches(Predicates.requestType(LaunchRequest::class.java))
    }

    override fun handle(input: HandlerInput): Optional<Response> {
        val speechText = SkillUtils.m(input, "GREETING")
        return input.responseBuilder
                .withSpeech(speechText)
                .withSimpleCard("Cripto Info", speechText)
                .withReprompt(SkillUtils.m(input, "GENERIC_REPROMPT"))
                .withShouldEndSession(false)
                .build()
    }
}
