package it.introini.alexa.criptovalute

import com.amazon.ask.Skill
import com.amazon.ask.SkillStreamHandler
import com.amazon.ask.Skills
import it.introini.alexa.criptovalute.handlers.*

fun test(): Skill = Skills.standard()
        .addRequestHandlers(
                CancelandStopIntentHandler(),
                HelpIntentHandler(),
                LaunchRequestHandler(),
                ListFavoriteCoinsIntentHandler(),
                GetCoinInfoIntentHandler(),
                AddFavoriteCoinIntentHandler(),
                RemoveFavoriteCoinIntentHandler(),
                SessionEndedRequestHandler(),
                FallbackIntentHandler())
        .withSkillId(SKILL_ID)
        .build()

class TestSkill : SkillStreamHandler(test())
