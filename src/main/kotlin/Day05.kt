fun main() {
    val stacks = mutableListOf<MutableList<Char>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )

    var isParsingStacks = true
    var isParsingStackCount = false
    var isParsingInstructions = false

    fun parseStack(line: String) {
        println("Parsing stack: $line")
        line.chunked(4).forEachIndexed { index, crate ->
            if (crate.isNotBlank()) {
                val crateLetter = crate[1]
                stacks[index].add(crateLetter)
            }
        }
    }

    fun checkStackCount(line: String) {
        println("Parsing Stack Count: $line")
        val stackCount = line.chunked(4).size

        check(stackCount == stacks.filter { it.isNotEmpty() }.size) {
            "Stack Count: $stackCount, Stacks Size: ${stacks.size}"
        }
    }

    fun getOperationParameters(instruction: String): List<Int> {
        val numericOnly = instruction
            .replace("[^0-9 ]".toRegex(), "")
            .replace("  ", " ")
            .trim()

        return numericOnly.split(" ", limit = 3).map { it.toInt() }
    }

    fun operateOneAtATime(instruction: String) {
        if (instruction.isBlank()) return
        println("Handling instruction: $instruction")

        val (toMove, fromStack, toStack) = getOperationParameters(instruction)

        println("Moving $toMove from $fromStack to $toStack")
        for (i in 0 until toMove) {
            println("  Op ${i+1}: from ${stacks[fromStack-1]} to ${stacks[toStack-1]}")
            stacks[toStack-1].add(0, stacks[fromStack-1].removeFirst())
        }
    }

    fun operateMultipleAtATime(instruction: String) {
        if (instruction.isBlank()) return
        println("Handling instruction: $instruction")

        val (toMove, fromStack, toStack) = getOperationParameters(instruction)

        println("Moving $toMove from $fromStack to $toStack")
        val toAdd = stacks[fromStack-1].take(toMove)
        for (i in 0 until toMove) stacks[fromStack-1].removeFirst()
        stacks[toStack-1].addAll(0, toAdd)
        println("Resulting Stacks: $stacks")
    }

    fun parseAndOperate(input: List<String>, oneAtATime: Boolean): String {
        input.forEach { line ->
            if (isParsingStacks) {
                if (line.contains("[").not()) {
                    isParsingStacks = false
                    isParsingStackCount = true
                    println("Parsed stacks as this: $stacks")
                } else {
                    parseStack(line)
                }
            }

            if (isParsingStackCount) {
                if (line.isBlank()) {
                    isParsingStackCount = false
                } else {
                    checkStackCount(line)
                }
            }

            if (!isParsingStacks && !isParsingStackCount) isParsingInstructions = true

            if (isParsingInstructions) {
                if (oneAtATime) {
                    operateOneAtATime(line)
                } else {
                    operateMultipleAtATime(line)
                }
            }
        }

        val result = stacks.filter { it.isNotEmpty() }.map { it.first() }.joinToString("")

        isParsingStacks = true
        isParsingStackCount = false
        isParsingInstructions = false
        stacks.forEach { it.clear() }

        return result
    }

    fun part1(input: List<String>): String {
        return parseAndOperate(input, oneAtATime = true)
    }

    fun part2(input: List<String>): String {
        return parseAndOperate(input, oneAtATime = false)
    }

    val testInput = readInput("Day05-Example")
    val test1 = part1(testInput)
    val test2 = part2(testInput)
    check(test1 == "CMZ") { "Test 1 expected CMZ but got $test1"}
    check(test2 == "MCD") { "Test 2 expected MCD but got $test2" }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
