package opgave41

import com.github.shmvanhouten.adventofcode2017.util.rawinstructionconverter.RawInstructionConverter
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class CharRemoverTest {

    private val converter = RawInstructionConverter()
    private val grid = converter.convertIndexedRawInputIntoInstructions("/opgave41.txt", this::convertToCharList)
            .flatMap { it }
            .toMap()

    @Test
    fun `it should see if there are any solo letters of type in the grid`() {
        val messageFinder = CharRemover()

        assertThat(messageFinder.isCharEverAlone('F', grid), equalTo(false))
    }

    @Test
    fun `it should tell there are solo letters of type in the grid`() {
        val messageFinder = CharRemover()

        assertThat(messageFinder.isCharEverAlone('O', grid), equalTo(true))
        assertThat(messageFinder.isCharEverAlone('!', grid), equalTo(true))
    }

    @Test
    fun `it should remove all the F's and and move the rest down a row`() {
        val messageFinder = CharRemover()

        val gridWithoutChar = messageFinder.removeChar(Coordinate(0, 7), grid)
        drawGrid(gridWithoutChar)
        assertThat(gridWithoutChar.containsKey(Coordinate(0, 0)), equalTo(false))
    }

    @Test
    fun `Martijn's input`() {
        val messageFinder = CharRemover()


        var gridWithoutChar = grid


        coordinates.forEach { coordinate ->
            println("removing ${gridWithoutChar.getValue(coordinate)} at coordinate: $coordinate")
            gridWithoutChar = messageFinder.removeChar(coordinate, gridWithoutChar)
            drawGrid(gridWithoutChar)
        }
        assertThat(gridWithoutChar.values.any { it == 'k' }, equalTo(false))
    }


    private fun convertToCharList(readline: String, y: Int): List<Pair<Coordinate, Char>> {
        return readline.trim().split(" \t")
                .map { it.toCharArray()[0] }
                .mapIndexed { x, char -> Coordinate(x, y) to char }
    }
}

val coordinates = listOf(Coordinate(x = 0, y = 7),
        Coordinate(x = 7, y = 15),
        Coordinate(x = 2, y = 10),
        Coordinate(x = 2, y = 9),
        Coordinate(x = 15, y = 2),
        Coordinate(x = 15, y = 6),
        Coordinate(x = 20, y = 16),
        Coordinate(x = 11, y = 2),
        Coordinate(x = 7, y = 15),
        Coordinate(x = 7, y = 15),
        Coordinate(x = 8, y = 18),
        Coordinate(x = 11, y = 17),
        Coordinate(x = 1, y = 10),
        Coordinate(x = 5, y = 9),
        Coordinate(x = 2, y = 9),
        Coordinate(x = 7, y = 11),
        Coordinate(x = 7, y = 17),
        Coordinate(x = 18, y = 15),
        Coordinate(x = 8, y = 12),
        Coordinate(x = 11, y = 11),
        Coordinate(x = 14, y = 15),
        Coordinate(x = 7, y = 19),
        Coordinate(x = 0, y = 17),
        Coordinate(x = 11, y = 13),
        Coordinate(x = 6, y = 19),
        Coordinate(x = 2, y = 16),
        Coordinate(x = 0, y = 16),
        Coordinate(x = 3, y = 17),
        Coordinate(x = 0, y = 14)
)