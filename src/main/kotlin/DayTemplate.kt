fun main() {
    fun part1(input: List<String>): Int {
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("DayFIXME-Example")
    val part1TestResult = part1(testInput)
    val part2TestResult = part2(testInput)
    val part1Expected = -1
    val part2Expected = -1

    check(part1TestResult == part1Expected) { "Part 1: Expected $part1Expected but got $part1TestResult" }
    check(part2TestResult == part2Expected) { "Part 2: Expected $part2Expected but got $part2TestResult" }

    val input = readInput("DayFIXME")
    println(part1(input))
    println(part2(input))
}
