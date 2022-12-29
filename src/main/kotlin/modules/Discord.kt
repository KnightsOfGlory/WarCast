package modules

import dev.kord.common.entity.DiscordChannel
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import dev.kord.rest.service.RestClient
import kotlinx.coroutines.runBlocking
import utilities.Logger

object Discord {

    private val token = System.getenv("DISCORD_TOKEN")
    private val guild = Snowflake(System.getenv("DISCORD_GUILD_ID"))

    private lateinit var kord: Kord
    private var rest = RestClient(token)

    class Listener: Runnable {
        override fun run() {
            runBlocking {
                kord = Kord(token)

                Logger.info("Subscribing to Discord messages...")
                kord.on<MessageCreateEvent> {
                    Logger.discord(message)

                    val snowflake = message.channelId
                    val username = message.author?.username
                    val talk = message.content.take(237)

                    if (username != "WarCast") {
                        val send = "<$username> $talk"
                        val bot = Bots.byChannel(snowflake)

                        bot.send(send)
                    }
                }

                Logger.info("Logging in to Discord...")
                kord.login {
                    @OptIn(PrivilegedIntent::class)
                    intents += Intent.MessageContent
                }
            }
        }
    }

    suspend fun connect() {
        val listener = Listener()
        val thread = Thread(listener)

        thread.start()
    }

    suspend fun send(channel: DiscordChannel, message: String) {
        rest.channel.createMessage(channel.id) {
            content = message
        }
    }

    suspend fun warcast(): DiscordChannel {
        return channels().filter { it.name.value == System.getenv("DISCORD_CHANNEL_CATEGORY") }.first()
    }

    suspend fun channels(): List<DiscordChannel> {
        return rest.guild.getGuildChannels(guild)
    }
}
