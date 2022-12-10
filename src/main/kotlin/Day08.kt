fun main() {
    fun part1(input: List<String>): Int {

        val forestWidth = input[0].length
        val forestLength = input.size
        var runningTotal = 0

        first@ for (i in 0 until forestWidth) {
            second@ for (j in 0 until forestLength) {

                var canBeSeen = true

                if (i == 0 || i == forestWidth - 1 || j == 0 || j == forestLength - 1) {
                    runningTotal++
                } else {
                    left@ for (k in 0 until j) {
                        canBeSeen = input[i][k] < input[i][j]
                        !canBeSeen && break@left
                    }

                    if (canBeSeen) {
                        runningTotal++
                        continue@second
                    }

                    right@ for (k in forestWidth-1 downTo j+1) {
                        canBeSeen = input[i][k] < input[i][j]
                        !canBeSeen && break@right
                    }

                    if (canBeSeen) {
                        runningTotal++
                        continue@second
                    }

                    above@ for (k in 0 until i) {
                        canBeSeen = input[k][j] < input[i][j]
                        !canBeSeen && break@above
                    }

                    if (canBeSeen) {
                        runningTotal++
                        continue@second
                    }

                    below@ for (k in forestLength-1 downTo i+1) {
                        canBeSeen = input[k][j] < input[i][j]
                        !canBeSeen && break@below
                    }

                    if (canBeSeen) {
                        runningTotal++
                        continue@second
                    }
                }
            }
        }

        return runningTotal
    }

    // TODO Implement this one properly :(
    fun part2(input: List<String>): Int {
        val forestWidth = input[0].length
        val forestLength = input.size

        var leftScore = 0
        var rightScore = 0
        var aboveScore = 0
        var belowScore = 0

        var highestScore = 0

        // Any of the edges will definitely have a score of 0, so skip them.
        first@ for (i in 1 until forestWidth-1) {
            second@ for (j in 1 until forestLength-1) {

                leftScore = 0
                rightScore = 0
                aboveScore = 0
                belowScore = 0

                left@ for (k in i downTo 1) {
                    if (input[k][j] > input[i][j]) {
                        break@left
                    } else if (input[k][j] == input[i][j]) {
                        leftScore++
                        break@left
                    } else {
                        leftScore++
                    }
                }

                right@ for (k in i until forestWidth-2) {
                    if (input[k][j] > input[i][j]) {
                        break@right
                    } else if (input[k][j] == input[i][j]) {
                        rightScore++
                        break@right
                    } else {
                        rightScore++
                    }
                }

                above@ for (k in j downTo 1) {
                    if (input[i][k] > input[i][j]) {
                        break@above
                    } else if (input[i][k] == input[i][j]) {
                        aboveScore++
                        break@above
                    } else {
                        aboveScore++
                    }
                }

                below@ for (k in j until forestLength-2) {
                    if (input[i][k] > input[i][j]) {
                        break@below
                    } else if (input[i][k] == input[i][j]) {
                        belowScore++
                        break@below
                    } else {
                        belowScore++
                    }
                }

                highestScore = maxOf(highestScore, leftScore * rightScore * aboveScore * belowScore)
            }
        }

        return highestScore
    }

    val testInput = readInput("Day08-Example")
    val part1TestResult = part1(testInput)
    val part2TestResult = part2(testInput)
    val part1Expected = 21
    val part2Expected = 8

    check(part1TestResult == part1Expected) { "Part 1: Expected $part1Expected but got $part1TestResult" }
    check(part2TestResult == part2Expected) { "Part 2: Expected $part2Expected but got $part2TestResult" }

    val input = readInput("Day08")
    println(part1(input))
    //println(part2(input))
}

data class Tree(
    val x: Int,
    val y: Int,
    val z: Int,
    val seenFromAnySide: Boolean = false,
)
