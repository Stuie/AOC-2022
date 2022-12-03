fun main() {


    fun part1(input: List<String>): Int {
        var runningTotal = 0

        input.forEach { rucksack ->
            val (firstCompartment, secondCompartment) = rucksack
                .splitToSequence("")
                .drop(1)
                .chunked(rucksack.length / 2)
                .toList()

            val duplicate = firstCompartment
                .intersect(secondCompartment.toSet())
                .first()
                .toCharArray()
                .first()

            runningTotal += if (duplicate.isLowerCase()) {
                duplicate.code - 96
            } else {
                duplicate.code - 38
            }
        }

        return runningTotal
    }

    fun part2(input: List<String>): Int {
        var runningTotal = 0

        input.chunked(3).forEach { groupList ->
            val commonItem = groupList[0]
                .split("")
                .drop(1)
                .dropLast(1)
                .intersect(
                    groupList[1]
                        .split("")
                        .drop(1)
                        .dropLast(1)
                        .toSet()
                )
                .intersect(
                    groupList[2]
                        .split("")
                        .drop(1)
                        .dropLast(1)
                        .toSet()
                )

            val commonItemChar = commonItem.first().toCharArray().first()

            runningTotal += if (commonItemChar.isLowerCase()) {
                commonItemChar.code - 96
            } else {
                commonItemChar.code - 38
            }
        }

        return runningTotal
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
