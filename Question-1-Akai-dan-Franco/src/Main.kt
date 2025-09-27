fun main() {
    val t = readlnOrNull()?.trim()?.toIntOrNull() ?: return

    for (i in 1..t) {
        val parts = readlnOrNull()
            ?.trim()
            ?.split("\\s+".toRegex())
            ?: emptyList()

        if (parts.size < 2) {
            println("Case #$i: 100")
            continue
        }

        val (a, b) = parts.map { it.toInt() }
        val perlu = (100 - (a + b)).coerceAtLeast(0)

        println("Case #$i: $perlu")
    }
}
