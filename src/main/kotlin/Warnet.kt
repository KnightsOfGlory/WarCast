import dev.kord.common.entity.DiscordChannel
import kotlinx.coroutines.runBlocking
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.net.Socket

class Warnet(
    val channel: DiscordChannel,
    val server: String,
    val port: Int,
    val username: String,
    val password: String,
    val home: String
): Runnable {

    override fun run() {
        while (true) {
            connect()
            login()
            loop()
        }
    }

    private lateinit var socket: Socket
    private lateinit var writer: PrintWriter
    private lateinit var reader: BufferedReader

    private fun connect() {
        val address = InetSocketAddress(server, port)

        socket = Socket()
        socket.connect(address)
        writer = PrintWriter(socket.getOutputStream(), true)
        reader = BufferedReader(InputStreamReader(socket.inputStream))
    }

    private fun login() {
        writer.println("C1\r\n")
        writer.println("ACCT ${username}\r\n")
        writer.println("PASS ${password}\r\n")
        writer.println("HOME ${home}\r\n")
        writer.println("LOGIN\r\n")
    }

    fun send(data: String) {
        writer.println("${data}\r\n")
    }

    private fun loop() {
        while (!socket.isClosed && socket.isConnected) {
            val data = reader.readLine() ?: continue
            val bot = this
            runBlocking {
                Handler.message(bot, data)
            }
        }
    }
}
