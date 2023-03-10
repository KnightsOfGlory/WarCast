package utilities

object ChatHelper {

    private val antiIdles = listOf(
        "Apathy3 - Unstable and damn near unusable",
        "Apathy2 - Unstable and damn near unusable",
        "Its Hammer Time :) - DC v1.2.",
        "Its Hammer Time :) - DC v1.3b1 [Private Edition]",
        "Its Hammer Time :) - DC v1.3b1 [Private Edition].",
        ":+:~EwR 4 LyFe~:+: - Ghost 3.02",
        "-[ +|{W+ ]-[ Subaru Version 1.3.5 MoonGlade Series ]-",
        "is a Death Chat v0.8 by Unknown [Shinigami Edition]."
    )

    private val hmms = listOf(
        "hmm",
        "hmmm",
        "hrm"
    )

    fun isAntiIdle(message: String): Boolean {
        if (message.startsWith("is a SphtBot - Bot")) return true
        if (hmms.contains(message)) return true

        return antiIdles.contains(message)
    }
}
