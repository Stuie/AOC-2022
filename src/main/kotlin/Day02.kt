sealed class RPCMove {
    abstract val score: Int
    //abstract val pointsForWinningWithThisType: Int
    abstract fun versus(move: RPCMove): Int

    object Rock : RPCMove() {
        override val score = 1

        override fun versus(move: RPCMove): Int {
            return when (move) {
                is Scissors -> 6
                is Paper -> 0
                is Rock -> 3
            }
        }
    }

    object Paper : RPCMove() {
        override val score = 2

        override fun versus(move: RPCMove): Int {
            return when (move) {
                is Scissors -> 0
                is Paper -> 3
                is Rock -> 6
            }
        }
    }

    object Scissors : RPCMove() {
        override val score = 3

        override fun versus(move: RPCMove): Int {
            return when (move) {
                is Scissors -> 3
                is Paper -> 6
                is Rock -> 0
            }
        }
    }
}

fun main() {

    val abcToRPC = mapOf(
        "A" to RPCMove.Rock,
        "B" to RPCMove.Paper,
        "C" to RPCMove.Scissors,
        "X" to RPCMove.Rock,
        "Y" to RPCMove.Paper,
        "Z" to RPCMove.Scissors,
    )

    fun part1(input: List<String>): Int {
        var runningScore = 0

        input.forEach { round ->
            val (them, us) = round.split(" ", limit = 2)

            val theirMove = abcToRPC[them]!!
            val ourMove = abcToRPC[us]!!

            runningScore += ourMove.versus(theirMove) + ourMove.score
        }

        return runningScore
    }

    fun part2(input: List<String>): Int {
        var runningScore = 0

        input.forEach { round ->
            val (them, result) = round.split(" ", limit = 2)
            val theirMove = abcToRPC[them]!!
            val resultScore = when (result) {
                "X" -> 0
                "Y" -> 3
                "Z" -> 6
                else -> Int.MIN_VALUE
            }
            val pointsForWhatIHadToPlayToGetDesiredResult = when (theirMove) {
                is RPCMove.Rock -> {
                    when (result) {
                        "X" -> RPCMove.Scissors.score
                        "Y" -> RPCMove.Rock.score
                        "Z" -> RPCMove.Paper.score
                        else -> Int.MIN_VALUE
                    }
                }
                RPCMove.Paper -> {
                    when (result) {
                        "X" -> RPCMove.Rock.score
                        "Y" -> RPCMove.Paper.score
                        "Z" -> RPCMove.Scissors.score
                        else -> Int.MIN_VALUE
                    }
                }
                RPCMove.Scissors -> {
                    when (result) {
                        "X" -> RPCMove.Paper.score
                        "Y" -> RPCMove.Scissors.score
                        "Z" -> RPCMove.Rock.score
                        else -> Int.MIN_VALUE
                    }
                }
            }

            runningScore += resultScore + pointsForWhatIHadToPlayToGetDesiredResult
        }

        return runningScore
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
