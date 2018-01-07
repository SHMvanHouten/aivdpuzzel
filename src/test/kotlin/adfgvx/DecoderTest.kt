package adfgvx

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Before
import org.junit.Test


class DecoderTest {

    private lateinit var decoder: Decoder
    private lateinit var squareBuilder: SquareBuilder

    @Before
    fun setUp() {
        decoder = Decoder()
        squareBuilder = SquareBuilder()
    }

    @Test
    fun `it should sort the keyword letters in alphabetical order, match them to the code columns, and rearrange them to original`() {

        val codedMessage = "VAXDGF"
        val key = "BAD"

        val expected = "XVGDAF"


        assertThat(decoder.deTransposeMessage(codedMessage, key), equalTo(expected))
    }

    @Test
    fun `it should do the same but with an uneven amount of chars`() {

        val codedMessage = "VAXDGFA"
        val key = "BAD"


        val expected = "XVFDAAG"


        assertThat(decoder.deTransposeMessage(codedMessage, key), equalTo(expected))
    }

    @Test
    fun `it should extract the message from the code and square`() {
        val transposedMessage = "FXGXDDGXXAXXFAFAFGGGFAAXFAGXDX"

        val parsedSquare = squareBuilder.build("NACHTBOMMENWERPER")
        printSquare(parsedSquare)

        val decodedMessage = decoder.decodeTransposedMessage(transposedMessage, parsedSquare)
        assertThat(decodedMessage, equalTo("ditiszeergeheim".toUpperCase()))
    }

    @Test
    fun `it should decode the message`() {

        val encrypted = "DFGGX XAAXG AFXGA FXXXG FFXFA DDXGA".removeAllChars(' ')
        val firstKey = "PILOTEN"
        val secondKey = "NACHTBOMMENWERPER"

        val decoded = decoder.decodeMessage(encrypted, firstKey, secondKey).toLowerCase()
        assertThat(decoded, equalTo("ditiszeergeheim"))
    }

    private fun printSquare(parsedSquare: Map<SquareCoordinate, Char>) {
        ADFGVX.forEach { y ->
            println(StringBuilder().apply {
                ADFGVX.forEach { x ->
                    append(parsedSquare.getValue(SquareCoordinate(x, y)))
                }
            })
        }
    }


}



private fun String.removeAllChars(char: Char): String {
    return this.replace("$char".toRegex(), "")
}
