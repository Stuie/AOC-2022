import java.io.File
import kotlin.io.path.Path

val inputFileLocation = Path("src", "main", "kotlin")

/**
 * Reads lines from the given input txt file.
 */
//fun readInput(name: String) = File("src\\main\\kotlin", "$name.txt").readLines().dropLastWhile { it.isBlank() }

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File(inputFileLocation.toString(), "$name.txt").readLines().dropLastWhile { it.isBlank() }
