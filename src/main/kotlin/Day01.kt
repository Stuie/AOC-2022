fun main() {
    fun getElves(input: List<String>): List<Int> {
        val elves = mutableListOf<Int>()

        var runningTotal = 0
        input.forEach { calories ->
            if (calories.isBlank()) {
                elves.add(runningTotal)
                runningTotal = 0
            } else {
                runningTotal += calories.toInt()
            }
        }

        return elves
    }

    fun part1(input: List<String>): Int {
        val elves = getElves(input)

        return elves.maxOf { it }
    }

    fun part2(input: List<String>): Int {
        val elves = getElves(input)

        return elves.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
