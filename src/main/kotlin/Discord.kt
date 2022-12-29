import dev.kord.common.entity.DiscordChannel
import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import dev.kord.rest.service.RestClient
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object Discord {

    private val token = ""
    private val guild = Snowflake("1057814485513027694")

    private lateinit var kord: Kord
    private var rest = RestClient(token)

    class Listener: Runnable {
        override fun run() {
            runBlocking {
                kord = Kord(token)

                kord.on<MessageCreateEvent> {
                    val snowflake = message.channelId
                    val username = message.author?.username
                    val talk = message.content

                    if (username != "WarCast") {
                        val send = "<$username> $talk"
                        val bot = Bots.byChannel(snowflake)

                        bot.send(send)
                    }
                }

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
        return channels().filter { it.name.value == "WarCast" }.first()
    }

    suspend fun channels(): List<DiscordChannel> {
        return rest.guild.getGuildChannels(guild)
    }
}
