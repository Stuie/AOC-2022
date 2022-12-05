fun main() {

    fun String.getItemsAsList(): MutableList<Int> {
        return this.split("-").map { it.toInt() }.toMutableList()
    }

    fun MutableList<Int>.fillGaps(): List<Int> {
        return IntRange(first(), last()).toList()
    }

    fun getElfRangeItems(elfPair: String): Pair<List<Int>, List<Int>> {
        val (firstRangeString, secondRangeString) = elfPair.split(",", limit = 2)
        val firstRangeItems = firstRangeString.getItemsAsList().fillGaps()
        val secondRangeItems = secondRangeString.getItemsAsList().fillGaps()

        return Pair(firstRangeItems, secondRangeItems)
    }

    fun part1(input: List<String>): Int {
        var runningTotal = 0

        input.forEach { elfPair ->
            val (firstRangeItems, secondRangeItems) = getElfRangeItems(elfPair)

            if (firstRangeItems.containsAll(secondRangeItems)
                || secondRangeItems.containsAll(firstRangeItems))  {
                runningTotal++
            }
        }

        return runningTotal
    }

    fun part2(input: List<String>): Int {
        var runningTotal = 0

        input.forEach { elfPair ->
            val (firstRangeItems, secondRangeItems) = getElfRangeItems(elfPair)

            if (firstRangeItems.intersect(secondRangeItems.toSet()).isNotEmpty()) {
                runningTotal++
            }
        }

        return runningTotal
    }

    val testInput = readInput("Day04-Example")
    check(part1(testInput) == 2) { "Part 1" }
    check(part2(testInput) == 4) { "Part 2" }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
