package opgave41

class MessageTester(private val charRemover: CharRemover = CharRemover()) {
    fun test(solution: String, grid: Map<Coordinate, Char>): Boolean {

        val index = 0
        val solutionGrids = playAwayGridWithSolution(index, solution, grid)
        return solutionGrids != null
    }

    private fun playAwayGridWithSolution(index: Int, solution: String, grid: Map<Coordinate, Char>): Map<Coordinate, Char>? {
        if(index > solution.length ){
            println(grid.size)
        }
        if (index == solution.length && grid.isEmpty()) {
            println("found a solution")
            return grid
        }
        val currentChar = solution[index]

        if(currentCharIsTheLastInTheSolution(index, solution) && !isCharInSingleCluster(currentChar, grid)){
//            println("$currentChar at $index is the last but will not be eliminated")
//            drawGrid(grid)
            return null
        }

        if(index > 16){
            println("we got past the dreaded 16!")
        }

        val gridsThatWork = getValidCoordinates(currentChar, grid).mapNotNull {
            val gridWithoutChar = charRemover.removeChar(it, grid)
            playAwayGridWithSolution(index + 1, solution, gridWithoutChar)
        }

        return if(gridsThatWork.isNotEmpty()){
            gridsThatWork.first()
        } else {
            null
        }
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
        var groupLeaders = setOf<Coordinate>()
        var groupMembers = setOf<Coordinate>()

        groupedCharacters.forEach { coordinateOfCharacter ->
            if (coordinateOfCharacter.getNeighbours().none { groupMembers.contains(it) }) {
                groupLeaders += coordinateOfCharacter
            }

            groupMembers += coordinateOfCharacter
        }

        return groupLeaders
    }

    private fun isItAlone(coordinate: Coordinate, currentCharactersInGrid: Set<Coordinate>): Boolean {
        return coordinate.getNeighbours().none { currentCharactersInGrid.contains(it) }
    }

}