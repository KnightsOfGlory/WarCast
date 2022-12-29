package utilities

import dev.kord.common.entity.DiscordChannel
import dev.kord.core.entity.Message
import dev.kord.core.event.message.MessageCreateEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    private fun stamp(): String = LocalDateTime.now().format(formatter)

    private fun log(level: String, message: String) {
        println("[${stamp()}] [$level] $message")
    }

    fun info(message: String) { log("INFO", message) }
    fun error(message: String) { log("ERROR", message) }

    fun warnet(channel: String, user: String, talk: String) {
        info("[WARNET] #$channel @$user $talk")
    }
    fun discord(message: Message) {
        suspend {
            val channel = message.channel.asChannel().data.name.value!!
            val user = message.author!!.username
            val talk = message.content
            info("[DISCORD] #${channel} @$user $talk")
        }
    }
}
