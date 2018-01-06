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

        val A = listOf('V', 'A')
        val B = listOf('X', 'D')
        val D = listOf('G', 'F')

        val expectedList = listOf(B, A, D)


        assertThat(decoder.deTransposeMessage(codedMessage, key), equalTo(expectedList))
    }

    @Test
    fun `it should do the same but with an uneven amount of chars`() {

        val codedMessage = "VAXDGFA"
        val key = "BAD"

        val A = listOf('V', 'A')
        val B = listOf('X', 'D', 'G')
        val D = listOf('F', 'A')

        val expectedList = listOf(B, A, D)

        assertThat(decoder.deTransposeMessage(codedMessage, key), equalTo(expectedList))
    }

    @Test
    fun `it should extract the message from the code and square`() {
        val transposedMessage = "FXGXDDGXXAXXFAFAFGGGFAAXFAGXDX"
        val square = """N A 1 C 3 H
8 T B 2 O M
E 5 W R P D
4 F 6 G 7 I
9 J 0 K L Q
S U V X Y Z"""
        val parsedSquare = squareBuilder.parseSquare(square)
        printSquare(parsedSquare)

        val decodedMessage = decoder.decodeTransposedMessage(transposedMessage, parsedSquare)
        assertThat(decodedMessage, equalTo("ditiszeergeheim".toUpperCase()))
    }

    @Test
    fun `it should decode the message`() {
        val square = """N A 1 C 3 H
8 T B 2 O M
E 5 W R P D
4 F 6 G 7 I
9 J 0 K L Q
S U V X Y Z"""
        val parsedSquare = squareBuilder.parseSquare(square)
        val encrypted = "DFGGX XAAXG AFXGA FXXXG FFXFA DDXGA".removeAllChars(' ')
        val key = "PILOTEN"

        val decoded = decoder.decodeMessage(encrypted, parsedSquare, key).toLowerCase()
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
