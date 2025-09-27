import java.util.PriorityQueue
import java.util.Scanner

data class CPU(
    val id: Int,
    var finishTime: Long = 0,
    var tasksCount: Int = 0
) : Comparable<CPU> {
    override fun compareTo(other: CPU): Int {
        return when {
            this.finishTime != other.finishTime -> this.finishTime.compareTo(other.finishTime)
            this.tasksCount != other.tasksCount -> this.tasksCount.compareTo(other.tasksCount)
            else -> this.id.compareTo(other.id)
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val T = scanner.nextInt()
    repeat(T) { caseIndex ->
        val N = scanner.nextInt()
        val K = scanner.nextInt()
        val tasks = LongArray(N) { scanner.nextLong() }

        val pq = PriorityQueue<CPU>()
        for (i in 0 until K) {
            pq.add(CPU(i))
        }

        var totalTime = 0L

        for (task in tasks) {
            val cpu = pq.poll()!!
            cpu.finishTime += task
            cpu.tasksCount++
            totalTime = maxOf(totalTime, cpu.finishTime)
            pq.add(cpu)
        }

        println("Case #${caseIndex + 1}: $totalTime")
    }
}
