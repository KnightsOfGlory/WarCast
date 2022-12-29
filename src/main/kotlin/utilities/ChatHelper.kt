package utilities

object ChatHelper {

    private val antiIdles = listOf(
        "Apathy3 - Unstable and damn near unusable",
        "Apathy2 - Unstable and damn near unusable",
        "Its Hammer Time :) - DC v1.2.",
        "Its Hammer Time :) - DC v1.3b1 [Private Edition]",
        ":+:~EwR 4 LyFe~:+: - Ghost 3.02",
        "-[ +|{W+ ]-[ Subaru Version 1.3.5 MoonGlade Series ]-"
    )

    fun isAntiIdle(message: String): Boolean {
        if (message.startsWith("is a SphtBot - Bot")) return true

        return antiIdles.contains(message)
    }
}
