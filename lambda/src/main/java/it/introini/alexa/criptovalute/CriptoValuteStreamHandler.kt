package it.introini.alexa.criptovalute

import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skill
import com.amazon.ask.Skills
import com.amazon.ask.attributes.persistence.PersistenceAdapter
import com.amazon.ask.attributes.persistence.impl.DynamoDbPersistenceAdapter
import com.amazon.ask.builder.StandardSkillBuilder
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import it.introini.alexa.criptovalute.CriptoValuteStreamHandler
import it.introini.alexa.criptovalute.handlers.*

const val SKILL_ID = "amzn1.ask.skill.5611a9ca-8b2e-4fa5-9805-03489611817a"
fun skill(): Skill = Skills.standard()
        .addRequestHandlers(
            CancelandStopIntentHandler(),
            HelpIntentHandler(),
            LaunchRequestHandler(),
            ListFavoriteCoinsIntentHandler(),
            GetCoinInfoIntentHandler(),
            AddFavoriteCoinIntentHandler(),
            GetFavoriteCoinsUpdatesIntentHandler(),
            RemoveFavoriteCoinIntentHandler(),
            SessionEndedRequestHandler(),
            FallbackIntentHandler())
        .withSkillId(SKILL_ID)
        .withTableName("cripto-info")
        .withAutoCreateTable(true)
        .build()

class CriptoValuteStreamHandler : SkillStreamHandler(skill())
