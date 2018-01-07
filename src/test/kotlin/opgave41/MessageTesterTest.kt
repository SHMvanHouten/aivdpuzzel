package opgave41

import com.github.shmvanhouten.adventofcode2017.util.rawinstructionconverter.RawInstructionConverter
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import solutions.Solutions.Task41.solution

class MessageTesterTest {

    private val converter = RawInstructionConverter()
    private val grid = converter.convertIndexedRawInputIntoInstructions("/opgave41.txt", this::convertToCharList)
            .flatMap { it }
            .toMap()
    @Test
    fun `it should work through the possible solution`() {


        val messageTester = MessageTester()
        assertThat(messageTester.test(solution, grid), equalTo(true))
    }








    private fun convertToCharList(readline: String, y: Int): List<Pair<Coordinate, Char>> {
        return readline.trim().split(" \t")
                .map { it.toCharArray()[0] }
                .mapIndexed { x, char -> Coordinate(x, y) to char }
    }
}