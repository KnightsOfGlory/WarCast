import dev.kord.common.entity.Snowflake
import utilities.StringHelper

object Bots {

    lateinit var bots: List<Warnet>

    suspend fun connect() {
        val server = "ash.wserv.org"
        val port = 6112
        val pattern = "WarCast-"
        val password = ""

        val warcast = Discord.warcast()

        val channels = Discord.channels()
        bots = channels
            .filter { channel ->
                channel.parentId?.value == warcast.id
            }
            .map { channel ->
                val username = pattern + StringHelper.random(4)

                Warnet(
                    channel,
                    server,
                    port,
                    username,
                    password,
                    channel.name.value!!
                )
            }

        bots.forEach {
            val thread = Thread(it)
            thread.start()
        }
    }

    suspend fun byChannel(id: Snowflake): Warnet {
        return bots.first { it.channel.id == id }
    }
}
