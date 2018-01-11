package kruiswoord25d

import org.apache.commons.lang3.StringUtils

class VigenereDecipher(private val builder: ColumnBuilder = ColumnBuilder()) {

    fun decipher(encryptedMessage: String, key: String): String {

        val columns = builder.build()

        val alphabetMap = ('a'..'z').mapIndexed { index, c -> index to c }.toMap()

        val repeatedKey = StringBuilder().apply {
            0.until(encryptedMessage.length).step(key.length).forEach {
                append(key)
            }
        }.toString().substring(0, encryptedMessage.length)

        return repeatedKey.mapIndexed { index, char ->
            val encryptedChar = encryptedMessage[index]
            if (!encryptedChar.isLetter()) {
                encryptedChar
            } else {
                val columnToUse = columns.getValue(char)
                val resultCharIndex = columnToUse.indexOf(encryptedChar)
                alphabetMap.getValue(resultCharIndex)
            }
        }.joinToString("")
    }

    fun decipherList(encryptedMessage: String, keys: List<String>): List<String> {
        return keys.map { decipher(encryptedMessage, it) }
    }
}

fun dutchWords(): List<String> {
    val woorden = loadLines("/OpenTaal-210G-basis-gekeurd.txt").toMutableList()
    woorden.addAll(loadLines("/OpenTaal-210G-basis-ongekeurd.txt"))
    return woorden
}

fun normalDutchWords(): List<String> =
        dutchWords()
                .filter { !it.contains('\'') }
                .filter { StringUtils.isAlpha(it) }

fun normalDutchWordsWithSize(length: Int): List<String>{
    return normalDutchWords().filter { it.length == length }
}

fun normalDutchWordsInUpperCase() =
        normalDutchWords().map { it.toUpperCase() }

class FileUtils

    internal fun loadLines(fileOnClasspath: String) = loadLines(fileOnClasspath) { it }

    internal fun <T> loadLines(fileOnClasspath: String, converter: (String) -> T): List<T> {
        val lineList = mutableListOf<T>()
        FileUtils::class.java.getResourceAsStream(fileOnClasspath)
                .bufferedReader()
                .useLines { lines ->
                    lines.forEach {
                        lineList.add(converter(it))
                    }
                }
        return lineList
    }


fun main(args: Array<String>) {
    val decipherer = VigenereDecipher()
    val horizontaal24 = "Rpne vzgsus tpiw dlzvcrg olb Yafwdwvzug qz / Ylwtsfgflb zy qwl Oscxsu dl geathwu".toLowerCase()
    normalDutchWordsWithSize(4).forEach { println(decipherer.decipher(horizontaal24, it)) }
}

