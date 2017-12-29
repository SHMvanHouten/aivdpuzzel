package opgave41

class MessageTester(private val charRemover: CharRemover = CharRemover()) {

    fun test(solution: String, grid: Map<Coordinate, Char>): Boolean {

        var unfinishedPaths = listOf(Node(grid, emptyList()))

        solution.forEachIndexed { index, currentChar ->
            if (currentCharIsTheLastInTheSolution(index, solution)) {
                unfinishedPaths = unfinishedPaths.filter { isCharInSingleCluster(currentChar, it.grid) }
            }
            unfinishedPaths = unfinishedPaths.flatMap { node ->
                val validCoordinates = getValidCoordinates(currentChar, node.grid)
                validCoordinates.map { Node(charRemover.removeChar(it, node.grid), node.path.plus(it)) }
            }.distinctBy { it.grid }
            println("removing a $currentChar gave ${unfinishedPaths.size} possible grid layouts")
        }

        val first = unfinishedPaths.first()
        first.path.forEach { println(it) }
        return unfinishedPaths.any { it.grid.isEmpty() }
    }

    private fun isCharInSingleCluster(currentChar: Char, grid: Map<Coordinate, Char>): Boolean {
        val justTheChars = grid.filter { it.value == currentChar }
        val attachedChars = charRemover.getAttachedChars(justTheChars.keys.first(), justTheChars)
        return justTheChars.size == attachedChars.size
    }

    private fun currentCharIsTheLastInTheSolution(index: Int, solution: String): Boolean {
        val currentChar = solution[index]
        return !solution.substring(index + 1).contains(currentChar)
    }

    private fun getValidCoordinates(currentChar: Char, grid: Map<Coordinate, Char>): Set<Coordinate> {
        val currentCharactersInGrid = grid.filter { it.value == currentChar }.keys
        val groupedCharacters = currentCharactersInGrid.filter { !isItAlone(it, currentCharactersInGrid) }
        return getSingleCoordinatesFromGroups(groupedCharacters)
    }

    private fun getSingleCoordinatesFromGroups(groupedCharacters: List<Coordinate>): Set<Coordinate> {
        val groupLeaders = mutableMapOf<Coordinate, Set<Coordinate>>()

        groupedCharacters.forEach { coordinateOfCharacter ->
            val groupsThatFit = groupLeaders.filter { group ->
                group.value.any { coordinate ->
                    coordinateOfCharacter.getNeighbours().any { it == coordinate }
                }
            }
            when {
                groupsThatFit.isEmpty() -> groupLeaders.put(coordinateOfCharacter, setOf(coordinateOfCharacter))

                groupsThatFit.size == 1 -> groupLeaders.put(groupsThatFit.keys.first(), groupsThatFit.values.first() + coordinateOfCharacter)

                else -> {
                    groupsThatFit.keys.forEach { groupLeaders.remove(it) }
                    groupLeaders.put(groupsThatFit.keys.first(), groupsThatFit.values.flatMap { it }.toSet())
                }
            }

        }

        return groupLeaders.keys
    }

    private fun isItAlone(coordinate: Coordinate, currentCharactersInGrid: Set<Coordinate>): Boolean {
        return coordinate.getNeighbours().none { currentCharactersInGrid.contains(it) }
    }

}