fun main() {

    val top = FileStructureObject(
        "/",
        size = 0,
        parent = null,
        mutableListOf(),
    )

    fun buildDirectoryStructure(input: List<String>) {
        var currentDirectory = top

        input.forEach { line ->
            if (line.startsWith("$")) {
                if (line == "$ ls") {
                    // No-op
                } else {
                    val directoryToEnter = line.substringAfter("$ cd ")

                    currentDirectory = when (directoryToEnter) {
                        "/" -> top
                        ".." -> currentDirectory.parent!! // `cd ..` from `top` will crash
                        else -> currentDirectory.children.first { it.name == directoryToEnter }
                    }
                }
            } else {
                if (line.startsWith("dir ")) {
                    val directoryName = line.substringAfter("dir ")
                    if (currentDirectory.children.none { it.name == directoryName }) {
                        currentDirectory.children.add(
                            FileStructureObject(directoryName, 0L, currentDirectory, mutableListOf())
                        )
                    }
                } else {
                    val (size, fileName) = line.split(" ", limit = 2)
                    if (currentDirectory.children.none { it.name == fileName }) {
                        currentDirectory.children.add(
                            FileStructureObject(fileName, size.toLong(), currentDirectory, mutableListOf())
                        )
                    }
                }
            }
        }
    }

    val smallDirectories = mutableMapOf<String, Long>()

    fun getRecursiveSize(directory: FileStructureObject, limit: Long): Long {
        val thisDirectorySize = directory.children.filter { it.size != 0L }.sumOf { it.size }

        val childDirectorySizes = directory.children.filter { it.size == 0L }.sumOf { getRecursiveSize(it, limit) }

        if (thisDirectorySize + childDirectorySizes <= limit) {
            smallDirectories["${directory.parent?.name} ${directory.name}"] = thisDirectorySize + childDirectorySizes
        }

        return thisDirectorySize + childDirectorySizes
    }

    fun part1(input: List<String>): Long {
        buildDirectoryStructure(input)
        getRecursiveSize(top, 100000)

        val result = smallDirectories.values.sum()

        smallDirectories.clear()
        top.children.clear()

        return result
    }

    fun part2(input: List<String>): Long {
        buildDirectoryStructure(input)
        val totalDiskSize = 70000000L
        val totalSpaceUsed = getRecursiveSize(top, 70000000L)
        val totalSpaceNeeded = 30000000L

        val toDelete = totalSpaceNeeded - (totalDiskSize - totalSpaceUsed)

        return smallDirectories.values.sorted().first { it > toDelete }
    }

    val testInput = readInput("Day07-Example")
    val part1TestResult = part1(testInput)
    val part2TestResult = part2(testInput)
    val part1Expected = 95437L
    val part2Expected = 24933642L

    check(part1TestResult == part1Expected) { "Part 1: Expected $part1Expected but got $part1TestResult" }
    check(part2TestResult == part2Expected) { "Part 2: Expected $part2Expected but got $part2TestResult" }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

data class FileStructureObject(
    val name: String,
    val size: Long = 0,
    val parent: FileStructureObject?,
    val children: MutableList<FileStructureObject>,
) {
    override fun toString(): String {
        return "Name: $name, Size: $size"
    }
}
