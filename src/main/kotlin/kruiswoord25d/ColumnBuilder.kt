package kruiswoord25d

class ColumnBuilder {

    fun build(): Map<Char, List<Char>> {
        return ('a'..'z').associateBy ({it}, {
            (it..'z').plus('a'.until(it)).toList()
        })
    }

}

fun main(args: Array<String>) {
    val columnBuilder = ColumnBuilder()
    val columns = columnBuilder.build()
    println("   ${columns.getValue('a').joinToString(" ")}")
    println()
    columns.forEach{
        println("${it.key}  ${it.value.joinToString(" ")}")
    }
}