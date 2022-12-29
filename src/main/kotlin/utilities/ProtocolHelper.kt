package utilities

object ProtocolHelper {

    fun parseUser(message: String): String {
        return message.split(" ")[6]
    }

    fun parseQuoted(message: String): String {
        return message
            .split(" ")
            .drop(7)
            .joinToString(" ")
    }
}
