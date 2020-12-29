package it.introini.alexa.criptovalute

import com.amazonaws.util.StringInputStream
import org.junit.Test
import java.io.ByteArrayOutputStream

class ListFavoriteCoinsTest {
    val request = """
        {
        	"version": "1.0",
        	"session": {
        		"new": true,
        		"sessionId": "amzn1.echo-api.session.ceb95555-e313-4f89-9e0d-c93561c1a0f2",
        		"application": {
        			"applicationId": "amzn1.ask.skill.5611a9ca-8b2e-4fa5-9805-03489611817a"
        		},
        		"user": {
        			"userId": "amzn1.ask.account.AHNZVMGW4WYBUYY6WHRKANVOU6B6T6BOLI5UWAOOZ473MMKQY46W6K6UYCPSKQBSEXXGWI5DTPGSU32SZ52WGXCZWAQWUCUWJEXDUUWRVAHO7WTSRKJTZW6IP5MR7BL6BSVHZMVC374MM55SF3Z5S2HY5HAY7DOFY7KYVT5DLV5MNZNHQSGEVHHZ67AQXAFTIPVMRG6FAQSYQRY"
        		}
        	},
        	"context": {
        		"Viewports": [
        			{
        				"type": "APL",
        				"id": "main",
        				"shape": "RECTANGLE",
        				"dpi": 160,
        				"presentationType": "STANDARD",
        				"canRotate": false,
        				"configuration": {
        					"current": {
        						"video": {
        							"codecs": [
        								"H_264_42",
        								"H_264_41"
        							]
        						},
        						"size": {
        							"type": "DISCRETE",
        							"pixelWidth": 1024,
        							"pixelHeight": 600
        						}
        					}
        				}
        			}
        		],
        		"Viewport": {
        			"experiences": [
        				{
        					"arcMinuteWidth": 246,
        					"arcMinuteHeight": 144,
        					"canRotate": false,
        					"canResize": false
        				}
        			],
        			"shape": "RECTANGLE",
        			"pixelWidth": 1024,
        			"pixelHeight": 600,
        			"dpi": 160,
        			"currentPixelWidth": 1024,
        			"currentPixelHeight": 600,
        			"touch": [
        				"SINGLE"
        			],
        			"video": {
        				"codecs": [
        					"H_264_42",
        					"H_264_41"
        				]
        			}
        		},
        		"Extensions": {
        			"available": {
        				"aplext:backstack:10": {}
        			}
        		},
        		"System": {
        			"application": {
        				"applicationId": "amzn1.ask.skill.5611a9ca-8b2e-4fa5-9805-03489611817a"
        			},
        			"user": {
        				"userId": "amzn1.ask.account.AHNZVMGW4WYBUYY6WHRKANVOU6B6T6BOLI5UWAOOZ473MMKQY46W6K6UYCPSKQBSEXXGWI5DTPGSU32SZ52WGXCZWAQWUCUWJEXDUUWRVAHO7WTSRKJTZW6IP5MR7BL6BSVHZMVC374MM55SF3Z5S2HY5HAY7DOFY7KYVT5DLV5MNZNHQSGEVHHZ67AQXAFTIPVMRG6FAQSYQRY"
        			},
        			"device": {
        				"deviceId": "amzn1.ask.device.AEUGLPDPNIC4QQVTJVPTDGEEMWFD6BOQOHNDH4YBL4TSDYRZGJQP5KJHBLNTDQCNQ5QVYJDSYVOQ3MEWUFHUICQTWMI5QITMMYRPYSTYFAPKTJ5LFREIQUKCAN2SPWW37FZXWEIMJROCSRMNFBAGTZ4ELMOWCM6I7HEXF6UA2TGTZEDVLJ6XS",
        				"supportedInterfaces": {}
        			},
        			"apiEndpoint": "https://api.eu.amazonalexa.com",
        			"apiAccessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ.eyJhdWQiOiJodHRwczovL2FwaS5hbWF6b25hbGV4YS5jb20iLCJpc3MiOiJBbGV4YVNraWxsS2l0Iiwic3ViIjoiYW16bjEuYXNrLnNraWxsLjU2MTFhOWNhLThiMmUtNGZhNS05ODA1LTAzNDg5NjExODE3YSIsImV4cCI6MTYwODYzMjY2MiwiaWF0IjoxNjA4NjMyMzYyLCJuYmYiOjE2MDg2MzIzNjIsInByaXZhdGVDbGFpbXMiOnsiY29udGV4dCI6IkFBQUFBQUFBQVFCSVF6T2VCWE5kOS8yUnVBbEVyV3dlS2dFQUFBQUFBQUNMODgvWmpYWDN6MER2b3BIYTZPM0loOU9XbG8zNTVtd1pjUnN0bGRycFB5Wk5uK2VkKzNuTjM4RVloZk9IdUlnRk1rU01OckRkdk0zY2o5NGNzMmNPSitqNXU5emNtOE1xMVdSM05LbUdNdWlIbFVFaTV6WkkzSitRb3JrMkl4ZmZwK2N4aENHbkIwWWEvaHhlanpTUGdtOUZpRHdBY3JpVDFTRlFpSHA3Z2lMZ2Z3aDkvVklJNGhIMmRzd2c5aWY1cEdyd3kwQTFob1V5QkhwbUVMWXVvNHJFL2ErcnhZOEJBWE1aNFdZWGw2UzlRb3BDUERZV2p4dGhhSlBKMkpQUHBPMUlMcG1XMWtPN0dQOU1vcW96TG5ZLzNLQTBnSUhQMnhxRHcwU0tYb0NHSno0cGUzMlpVdFNYc1RIRk55UUxYNVN2UExvbTBleWRHTW90YWZ2TTg4aWdITmF1cmRxbkt2MjhyRy9tUnp6aTlUamdRV2lLSFQ0Y3NJd2x5ajBmL2dIN21vMW16OVZKIiwiY29uc2VudFRva2VuIjpudWxsLCJkZXZpY2VJZCI6ImFtem4xLmFzay5kZXZpY2UuQUVVR0xQRFBOSUM0UVFWVEpWUFRER0VFTVdGRDZCT1FPSE5ESDRZQkw0VFNEWVJaR0pRUDVLSkhCTE5URFFDTlE1UVZZSkRTWVZPUTNNRVdVRkhVSUNRVFdNSTVRSVRNTVlSUFlTVFlGQVBLVEo1TEZSRUlRVUtDQU4yU1BXVzM3RlpYV0VJTUpST0NTUk1ORkJBR1RaNEVMTU9XQ002STdIRVhGNlVBMlRHVFpFRFZMSjZYUyIsInVzZXJJZCI6ImFtem4xLmFzay5hY2NvdW50LkFITlpWTUdXNFdZQlVZWTZXSFJLQU5WT1U2QjZUNkJPTEk1VVdBT09aNDczTU1LUVk0Nlc2SzZVWUNQU0tRQlNFWFhHV0k1RFRQR1NVMzJTWjUyV0dYQ1pXQVFXVUNVV0pFWERVVVdSVkFITzdXVFNSS0pUWlc2SVA1TVI3Qkw2QlNWSFpNVkMzNzRNTTU1U0YzWjVTMkhZNUhBWTdET0ZZN0tZVlQ1RExWNU1OWk5IUVNHRVZISFo2N0FRWEFGVElQVk1SRzZGQVFTWVFSWSJ9fQ.YXIGsYOnh886mjj43jl2xcgMV65sS52Q0pVQMRezn4ac0a5uPyfHSOXi2MJEjotcrMwUZ8wewT3FHfD1JxZliuxWqEspR5cWEFy8YktJKdibiSOQbp0-p9H2xZq7uyzmGapDO_cQhwx0YbOfjYsOx1Ter1OL-9y5wFLx-pgS7McYrMBBbaX754TrHAGjt_2H-9ZCbByHat33dgH13pvR_hGFW9lTRXQ66tXrGSEE5slxEU1zjFYHSI6gVIYRWqIM8H1DExoIAfdMJfGjs4mI9u0hzeeB2AP8mXRgSLW3Tg5j7FzqA2ShrCWLw1OnwrGVkdeY5MJYjm_qo8wcxCik8g"
        		}
        	},
        	"request": {
        		"type": "IntentRequest",
        		"requestId": "amzn1.echo-api.request.a4343ab3-03a0-4b8e-b14f-91ce00f44729",
        		"locale": "it-IT",
        		"timestamp": "2020-12-22T10:19:22Z",
        		"intent": {
        			"name": "ListFavoriteCoinsIntent",
        			"confirmationStatus": "NONE"
        		},
        		"dialogState": "STARTED"
        	}
        }
    """.trimIndent()

    fun test() {
        val lambda = TestSkill()
        val baos = ByteArrayOutputStream()
        lambda.handleRequest(StringInputStream(request), baos, TestContext())
    }

}
