package it.introini.alexa.criptovalute

import com.amazonaws.util.StringInputStream
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream

@RunWith(JUnit4::class)
class LambdaTest {

    private val launchJson = """
        {
          "version": "1.0",
          "session": {
            "new": true,
            "sessionId": "amzn1.echo-api.session.123456789012",
            "application": {
              "applicationId": "$SKILL_ID"
            },
            "user": {
              "userId": "amzn1.ask.account.testUser"
            },
            "attributes": {}
          },
          "context": {
            "AudioPlayer": {
              "playerActivity": "IDLE"
            },
            "System": {
              "application": {
                "applicationId": "$SKILL_ID"
              },
              "user": {
                "userId": "amzn1.ask.account.testUser"
              },
              "device": {
                "supportedInterfaces": {
                  "AudioPlayer": {}
                }
              }
            }
          },
          "request": {
            "type": "LaunchRequest",
            "requestId": "amzn1.echo-api.request.1234",
            "timestamp": "2016-10-27T18:21:44Z",
            "locale": "it-IT"
          }
        }
    """.trimIndent()

    @Test
    fun test() {

        val lambda = TestSkill()
        val baos = ByteArrayOutputStream()
        lambda.handleRequest(StringInputStream(launchJson), baos, TestContext())
    }
}
