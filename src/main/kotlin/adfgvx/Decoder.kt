package adfgvx

val ADFGVX = "ADFGVX".toList()

class Decoder {

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
        val deTransposedMessage = stringifyColumns(key, columns)
        return decodeTransposedMessage(deTransposedMessage, parsedSquare)
    }

    private fun stringifyColumns(key: String, columns: List<List<Char>>): String {
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

fun parseSquare(square: String): Map<SquareCoordinate, Char> {

    return square.split('\n')
            .mapIndexed { y, row ->
                row.trim().split(' ')
                        .mapIndexed { x, string ->
                            SquareCoordinate(ADFGVX[y], ADFGVX[x]) to string.toChar()
                        }
            }
            .flatMap { it }
            .toMap()
}

private fun String.toChar(): Char {
    return this[0]
}

    data class SquareCoordinate(val x: Char, val y: Char)


    fun main(args: Array<String>) {
        val codedMessage = "VAADGFAGAAXXDAAAFFXAAAVDXAGGXAAAADGAXVGAFFAVXVAVADVADVAAVA"
        val key = "GEORGESPAINVIN"
        val square = """N E 5 D 4 I
9 T S O P L
G 7 A 1 B 2
C 3 F 6 H 8
J 0 K M Q R
U V W X Y Z"""
        val parsedSquare = parseSquare(square)
        val decoder = Decoder()
        val columns = decoder.deTransposeMessage(codedMessage, key)
//        columns.zip(key.toList(), { column: List<Char>, keyChar: Char -> listOf(keyChar) + column })
        println(decoder.decodeMessage(codedMessage, parsedSquare, key))
        val secondCodedMessage = "Fddgfxdfddaagdddxxadddvxadffaddddgfdavfdxxdvavdvdgvdgfddvd".toUpperCase()
        println(decoder.decodeMessage(secondCodedMessage, parsedSquare, key))
    }
