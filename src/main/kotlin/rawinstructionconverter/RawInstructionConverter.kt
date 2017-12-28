package com.github.shmvanhouten.adventofcode2017.util.rawinstructionconverter

class RawInstructionConverter {

    fun <T> convertRawInputIntoInstructions(relativePath: String, convertLine: (String) -> T): List<T> {
        val listOfInstructions = mutableListOf<T>()

        val inputStream = this::class.java.getResourceAsStream(relativePath)
        inputStream.bufferedReader()
                .useLines { lines ->
                    lines.forEach {
                        listOfInstructions.add(convertLine(it))
                    }
                }

        return listOfInstructions
    }

    fun <T> convertIndexedRawInputIntoInstructions(relativePath: String, convertLine: (String, Int) -> T): List<T> {
        val listOfInstructions = mutableListOf<T>()

        val inputStream = this::class.java.getResourceAsStream(relativePath)
        inputStream.bufferedReader()
                .useLines { lines ->
                    lines.forEachIndexed { index , line ->
                        listOfInstructions.add(convertLine(line, index))
                    }
                }

        return listOfInstructions
    }

}

object FileReader {

    fun readFile(relativePath: String): String{
        val stringBuilder = StringBuilder()

        val inputStream = this::class.java.getResourceAsStream(relativePath)
        inputStream.bufferedReader()
                .useLines { lines ->
                    lines.forEach {
                        stringBuilder.append(it)
                    }
                }

        return stringBuilder.toString()
    }
}

