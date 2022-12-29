package modules

import dev.kord.common.entity.Snowflake
import utilities.Logger
import utilities.StringHelper

object Bots {

    lateinit var bots: List<Warnet>

    suspend fun connect() {
        val server = System.getenv("WARNET_SERVER")
        val port = 6112
        val pattern = System.getenv("WARNET_USERNAME_PATTERN")
        val password = System.getenv("WARNET_PASSWORD")

        Logger.info("Pulling Discord channel category...")
        val warcast = Discord.warcast()

        Logger.info("Creating bots...")
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
                    channel.name.value!!.replace("-", " ")
                )
            }

        Logger.info("Connecting bots...")
        bots.forEach {
            val thread = Thread(it)
            thread.start()
        }
    }

    suspend fun byChannel(id: Snowflake): Warnet {
        return bots.first { it.channel.id == id }
    }
}
