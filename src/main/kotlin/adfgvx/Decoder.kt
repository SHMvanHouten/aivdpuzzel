package adfgvx

import solutions.Solutions.Task31.codedMessage
import solutions.Solutions.Task31.firstKey
import solutions.Solutions.Task31.secondKey

const val ADFGVX = "ADFGVX"

class Decoder(private val squareBuilder: SquareBuilder = SquareBuilder()) {

    fun decodeMessage(encrypted: String, firstKey: String, secondKey: String): String {
        val deTransposedMessage = deTransposeMessage(encrypted, firstKey)
        val parsedSquare = squareBuilder.build(secondKey)
        return decodeDeTransposedMessage(deTransposedMessage, parsedSquare)
    }

    fun deTransposeMessage(codedMessage: String, key: String): String {
        val remainder = codedMessage.length % key.length
        val columnSize = codedMessage.length / key.length
        var remainingCodedMessage = codedMessage

        val columns = key.mapIndexed { index, char -> PositionedChar(char, index) }
                .sortedBy { it.char }
                .map { positionedChar ->
                    val index = positionedChar.index
                    val tempColumnSize = assessColumnSize(columnSize, index, remainder)
                    val (column, remainingCode) = remainingCodedMessage.splitIntoTwo(tempColumnSize)
                    remainingCodedMessage = remainingCode
                    PositionedColumn(column.toList(), index)
                }.sortedBy { it.index }
                .map { it.column }
        printColumns(key, columns)
        return stringifyColumns(columns)
    }

    fun decodeDeTransposedMessage(transposedMessage: String, parsedSquare: Map<SquareCoordinate, Char>): String {
        val messagePairs = transposedMessage.splitIntoPairs()
        return messagePairs
                .map { parsedSquare.getValue(SquareCoordinate(it.first, it.second)) }
                .joinToString("")
    }

    private fun stringifyColumns(columns: List<List<Char>>): String {
        return StringBuilder().apply {
            0.until(columns.first().size).forEach { index ->
                columns.forEach {
                    if (index < it.size) {
                        append(it[index])
                    }
                }
            }
        }.toString()
    }

    private fun assessColumnSize(columnSize: Int, index: Int, remainder: Int): Int {
        var tempColumnSize = columnSize
        if (index < remainder) {
            tempColumnSize++
        }
        return tempColumnSize
    }
}

data class PositionedColumn(val column: List<Char>, val index: Int)

data class PositionedChar(val char: Char, val index: Int)

private fun String.splitIntoPairs(): List<Pair<Char, Char>> {

    return 0.until(this.length).step(2).map { index ->
        Pair(this[index], this[index + 1])
    }
}

private fun String.splitIntoTwo(columnSize: Int): Pair<String, String> {
    val first = this.substring(0, columnSize)
    val second = this.substring(columnSize)

    return Pair(first, second)
}


private fun printColumns(key: String, columns: List<List<Char>>) {
    println(key.toCharArray().joinToString(" "))
    println(StringBuilder().apply { 1.until(key.length * 2).forEach { append('-') } })

    0.until(columns.first().size).forEach { index ->
        println(StringBuilder().apply {
            columns.forEach {
                if (index < it.size) {
                    append(it[index]).append(' ')
                }
            }
        })
    }
}


data class SquareCoordinate(val x: Char, val y: Char)


fun main(args: Array<String>) {


    val decoder = Decoder()

    println(decoder.decodeMessage(codedMessage, firstKey, secondKey))
}
