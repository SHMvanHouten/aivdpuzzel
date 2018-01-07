package opg25

class WordPuzzle {
}

fun main(args: Array<String>) {
    val words = "aikido, arbeidsdag, basilisk, bibliotheekpas, bypass, cinemazaal, deuk, eucalyptusboom, fietsersbrug, folkrock, geschubd, grit, gymp, je, luchtmachtstaf, megasucces, mono, multimiljonair, pekelbad, rucola, soms, toegangsnummer, vals, ?, zwam"
    words.split(", ")
            .forEach { println("$it, ${it.length}") }
}