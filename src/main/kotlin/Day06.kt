fun main() {

    fun findEndPositionFirstSequenceOfUnique(input: String, size: Int = 4): Int {
        input.windowed(size = size, step = 1).forEachIndexed { index, s ->
            if (s.toCharArray().distinct().size == size) return index + size
        }
        return Int.MIN_VALUE
    }

    fun part1(input: List<String>): Int {
        return findEndPositionFirstSequenceOfUnique(input[0])
    }

    fun part2(input: List<String>): Int {
        return findEndPositionFirstSequenceOfUnique(input[0], size = 14)
    }

    val testInput = readInput("Day06-Example")
    val test1 = part1(testInput)
    check(test1 == 11) { "Part 1: Expected 11, got $test1" }
    val test2 = part2(testInput)
    check(test2 == 26) { "Part 2: Expected 26, got $test2" }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
