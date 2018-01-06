package adfgvx

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Before
import org.junit.Test

class SquareBuilderTest {

    private lateinit var squareBuilder: SquareBuilder

    @Before
    fun setUp() {
        squareBuilder = SquareBuilder()
    }

    @Test
    fun `it should build a square from NACHTBOMMENWERPER`() {
        val firstKey = "NACHTBOMMENWERPER"
        val polybiusSquare: Map<SquareCoordinate, Char> = squareBuilder.build(firstKey)
        assertThat(polybiusSquare.getValue(SquareCoordinate('A', 'G')), equalTo('C'))
    }
}