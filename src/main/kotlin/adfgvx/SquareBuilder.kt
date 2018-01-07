package adfgvx

private const val BASIC_SQUARE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

class SquareBuilder {
    fun build(firstKey: String): Map<SquareCoordinate, Char> {
        val key = firstKey.toUpperCase()
        var charsNotUsed = BASIC_SQUARE

        val rearrangedChars = StringBuilder().apply {
            key.forEach { char ->
                if (charsNotUsed.contains(char)) {
                    appendCharAndCorrespondingIndex(char)
                    charsNotUsed = charsNotUsed.removeAllChars(char)
                }
            }
            charsNotUsed.forEach { char ->
                appendCharAndCorrespondingIndex(char)
            }
        }.toString()
        println(rearrangedChars)

        return parseSquare(splitIntoSix(rearrangedChars))
    }

    private fun splitIntoSix(rearrangedChars: String): List<String> {
        return 0.until(36).step(6).map { index ->
            rearrangedChars.substring(index, index + 6)
        }
    }

    private fun parseSquare(rows: List<String>): Map<SquareCoordinate, Char> {
        return rows
                .mapIndexed { y, row ->
                    row.removeAllChars(' ')
                            .mapIndexed { x, char ->
                                SquareCoordinate(ADFGVX[y], ADFGVX[x]) to char
                            }
                }
                .flatMap { it }
                .toMap()
    }

    private fun StringBuilder.appendCharAndCorrespondingIndex(char: Char) {
        append(char)
        if (char in 'A'..'J') {
            val numberInAlphabet = alphabetIndex(char)
            append(numberInAlphabet)
        }
    }

    private fun alphabetIndex(char: Char) = (char + 1 - 'A') % 10
}

private fun String.removeAllChars(char: Char): String {
    return this.replace("$char".toRegex(), "")
}