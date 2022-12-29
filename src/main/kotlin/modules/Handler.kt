package modules

import utilities.ChatHelper
import utilities.Logger
import utilities.ProtocolHelper

object Handler {

    suspend fun message(bot: Warnet, message: String) {
        if (message.startsWith("PING")) {
            val tokens = message.split(" ")
            val cookie = tokens[1]

            Logger.info("Responding to ping $cookie")

            bot.send("/pong $cookie")
            return
        }

        if (message.startsWith("USER TALK ")) {
            val user = ProtocolHelper.parseUser(message)
            val talk = ProtocolHelper.parseQuoted(message)
            if (user == bot.username) return
            if (ChatHelper.isAntiIdle(talk)) return

            Logger.warnet(bot.home, bot.username, talk)

            Discord.send(bot.channel, "<**$user**> $talk")
            return
        }

        if (message.startsWith("USER EMOTE ")) {
            val user = ProtocolHelper.parseUser(message)
            val emote = ProtocolHelper.parseQuoted(message)
            if (user == bot.username) return
            if (ChatHelper.isAntiIdle(emote)) return

            Logger.warnet(bot.home, bot.username, emote)

            Discord.send(bot.channel, "<**$user**> _${emote}_")
            return
        }
    }
}
