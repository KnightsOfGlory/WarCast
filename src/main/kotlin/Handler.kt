import utilities.ChatHelper
import utilities.ProtocolHelper

object Handler {

    suspend fun message(bot: Warnet, message: String) {
        if (message.startsWith("PING")) {
            val tokens = message.split(" ")
            val cookie = tokens[1]
            bot.send("/pong $cookie")
            return
        }

        if (message.startsWith("USER TALK ")) {
            val user = ProtocolHelper.parseUser(message)
            val talk = ProtocolHelper.parseQuoted(message)
            if (user == bot.username) return
            if (ChatHelper.isAntiIdle(talk)) return

            Discord.send(bot.channel, "<**$user**> $talk")
            return
        }

        if (message.startsWith("USER EMOTE ")) {
            val user = ProtocolHelper.parseUser(message)
            val emote = ProtocolHelper.parseQuoted(message)
            if (user == bot.username) return
            if (ChatHelper.isAntiIdle(emote)) return

            Discord.send(bot.channel, "<**$user**> _${emote}_")
            return
        }
    }
}
