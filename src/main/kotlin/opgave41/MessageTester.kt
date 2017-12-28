package opgave41

class MessageTester(private val charRemover: CharRemover = CharRemover()) {
    fun test(solution: String, grid: Map<Coordinate, Char>): Boolean {

        val index = 0
        val solutionGrids = playAwayGridWithSolution(index, solution, grid)
        return solutionGrids.any { it.isEmpty() }
    }

    private fun playAwayGridWithSolution(index: Int, solution: String, grid: Map<Coordinate, Char>): List<Map<Coordinate, Char>> {
        if (index > 29) {
            return listOf(grid)
        }
        val currentChar = solution[index]

        if (index == 6 && grid.values.contains('k')) {
            return listOf(grid)
        }
        if(index == 9 && grid.values.contains('S')){
            return listOf(grid)
        }
        if(index == 10 && grid.values.contains('t')){
            return listOf(grid)
        }
        if(index == 17 && grid.values.contains('g')){
            return listOf(grid)
        }
        if(index == 18 && grid.values.contains('O')){
            return listOf(grid)
        }
        if(index == 20 && grid.values.contains('D')){
            return listOf(grid)
        }
        if(index == 21 && grid.values.contains('n')){
            return listOf(grid)
        }
        if(index == 22 && grid.values.contains('I')){
            return listOf(grid)
        }
        if(index == 23 && grid.values.contains('e')){
            return listOf(grid)
        }
        if(index == 24 && grid.values.contains('U')){
            return listOf(grid)
        }









        return getValidCoordinates(currentChar, grid).flatMap {
            val gridWithoutChar = charRemover.removeChar(it, grid)
            playAwayGridWithSolution(index + 1, solution, gridWithoutChar)
        }

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