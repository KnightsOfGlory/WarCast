import modules.Bots
import modules.Discord
import modules.Health
import utilities.Logger

suspend fun main() {

    Logger.info("Starting WarCast...")

    Logger.info("Connecting to Discord...")
    Discord.connect()

    Logger.info("Connecting to Warnet...")
    Bots.connect()

    Logger.info("Starting health monitor...")
    Health.start()
}
