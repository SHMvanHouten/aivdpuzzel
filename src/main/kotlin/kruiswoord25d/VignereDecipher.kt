package kruiswoord25d

class VigenereDecipher(private val builder: ColumnBuilder = ColumnBuilder()) {

    fun decipher(encryptedMessage: String, key: String): String {

        val columns = builder.build()

        val alphabetMap = ('a'..'z').mapIndexed{index, c -> index to c }.toMap()

        val repeatedKey = StringBuilder().apply {
            0.until(encryptedMessage.length).step(key.length).forEach {
                append(key)
            }
        }.toString().substring(0, encryptedMessage.length)
        return repeatedKey.mapIndexed { index, char ->
            val columnToUse = columns.getValue(char)
            val resultCharIndex = columnToUse.indexOf(encryptedMessage[index])
            alphabetMap.getValue(resultCharIndex)
        }.joinToString("")
    }
}

fun main(args: Array<String>) {

}

