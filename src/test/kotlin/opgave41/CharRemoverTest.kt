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

        var gridWithoutChar = messageFinder.removeChar(Coordinate(0, 7), grid)
        drawGrid(gridWithoutChar)
        gridWithoutChar = messageFinder.removeChar(Coordinate(7, 20), gridWithoutChar)
        drawGrid(gridWithoutChar)

        gridWithoutChar = messageFinder.removeChar(Coordinate(0, 13), gridWithoutChar)
        drawGrid(gridWithoutChar)

        gridWithoutChar = messageFinder.removeChar(Coordinate(0, 12), gridWithoutChar)
        drawGrid(gridWithoutChar)

        gridWithoutChar = messageFinder.removeChar(Coordinate(19, 2), gridWithoutChar)
        drawGrid(gridWithoutChar)

        gridWithoutChar = messageFinder.removeChar(Coordinate(20, 6), gridWithoutChar)
        drawGrid(gridWithoutChar)

        assertThat(gridWithoutChar.values.any { it == 'k' }, equalTo(false))
    }


    private fun convertToCharList(readline: String, y: Int): List<Pair<Coordinate, Char>> {
        return readline.trim().split(" \t")
                .map { it.toCharArray()[0] }
                .mapIndexed { x, char -> Coordinate(x, y) to char }
    }
}

