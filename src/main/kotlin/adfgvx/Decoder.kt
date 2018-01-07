package adfgvx

import solutions.Solutions.Task31.codedMessage
import solutions.Solutions.Task31.firstKey
import solutions.Solutions.Task31.secondKey

val ADFGVX = "ADFGVX".toList()

class Decoder(private val squareBuilder: SquareBuilder = SquareBuilder()) {

    fun deTransposeMessage(codedMessage: String, key: String): List<List<Char>> {
        val remainder = codedMessage.length % key.length
        val columnSize = codedMessage.length / key.length
        var remainingCodedMessage = codedMessage

        return key.mapIndexed { index, char -> index to char }
                .sortedBy { it.second }
                .map { indexToChar ->
                    var tempColumnSize = columnSize
                    if (indexToChar.first < remainder) {
                        tempColumnSize++
                    }
                    val (column, remainingCode) = remainingCodedMessage.splitIntoTwo(tempColumnSize)
                    remainingCodedMessage = remainingCode
                    indexToChar.first to column.toList()
                }.sortedBy { it.first }
                .map { it.second }
    }

    fun decodeTransposedMessage(transposedMessage: String, parsedSquare: Map<SquareCoordinate, Char>): String {
        val messagePair = transposedMessage.splitIntoPairs()
        return messagePair.map { parsedSquare.getValue(SquareCoordinate(it[0], it[1])) }.joinToString("")
    }

    fun decodeMessage(encrypted: String, parsedSquare: Map<SquareCoordinate, Char>, key: String): String {
        val columns = deTransposeMessage(encrypted, key)
        printColumns(key, columns)
        val deTransposedMessage = stringifyColumns(columns)
        return decodeTransposedMessage(deTransposedMessage, parsedSquare)
    }

    fun decodeMessage(encrypted: String, firstKey: String, secondKey: String): String {
        val columns = deTransposeMessage(encrypted, firstKey)
        printColumns(firstKey, columns)
        val deTransposedMessage = stringifyColumns(columns)
        val parsedSquare = squareBuilder.build(secondKey)
        return decodeTransposedMessage(deTransposedMessage, parsedSquare)
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


}

private fun String.splitIntoPairs(): List<String> {

    return 0.until(this.length).step(2).map { index ->
        this.substring(index, index + 2)
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
