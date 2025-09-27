fun main() {
    val input = readlnOrNull()?.trim() ?: return
    val evaluator = AkaiSangPandaMatematikawan(input)
    evaluator.evaluate()
}

class AkaiSangPandaMatematikawan(private var expr: String) {

    fun evaluate() {
        while (expr.contains(Regex("[+\\-\\*:]"))) {
            println(expr)
            val opInfo = findNextOperation()
            println(buildMarker(expr, opInfo))
            val result = calculate(opInfo.left, opInfo.right, opInfo.op)
            expr = expr.substring(0, opInfo.start) + result.toString() + expr.substring(opInfo.end)
        }
        println(expr)
    }

    private fun findNextOperation(): OperationInfo {
        val high = Regex("(-?\\d+)([\\*:])(-?\\d+)")
        val low = Regex("(-?\\d+)([+-])(-?\\d+)")

        val match = high.find(expr) ?: low.find(expr)
        match ?: throw IllegalArgumentException("Invalid expression")

        val left = match.groupValues[1].toInt()
        val op = match.groupValues[2][0]
        val right = match.groupValues[3].toInt()
        val start = match.range.first
        val end = match.range.last + 1

        return OperationInfo(start, end, op, left, right)
    }

    private fun calculate(left: Int, right: Int, op: Char) = when (op) {
        '+' -> left + right
        '-' -> left - right
        '*' -> left * right
        ':' -> left / right
        else -> 0
    }

    private fun buildMarker(expr: String, opInfo: OperationInfo): String {
        val marker = CharArray(expr.length) { '.' }
        for (i in opInfo.start until opInfo.end) {
            marker[i] = '-'
        }
        return marker.concatToString()
    }
}

data class OperationInfo(val start: Int, val end: Int, val op: Char, val left: Int, val right: Int)
