package opgave41

class CharRemover {
    fun isCharEverAlone(char: Char, grid: Map<Coordinate, Char>): Boolean {

        val justTheChars = grid.filter { it.value == char }
        return justTheChars.any {
            val neighbours = it.key.getNeighbours()
            neighbours.none { justTheChars.contains(it) }
        }
    }

    fun removeChar(coordinateToRemove: Coordinate, grid: Map<Coordinate, Char>): Map<Coordinate, Char> {
        val removedCoordinates = getAttachedChars(coordinateToRemove, grid)
        var gridWithout = grid - removedCoordinates

        removedCoordinates.sortedBy { it.y }.forEach { removedCoordinate ->
            gridWithout = gridWithout.entries.associateBy({
                if (it.key.x == removedCoordinate.x && it.key.y < removedCoordinate.y) {
                    Coordinate(it.key.x, it.key.y + 1)
                } else {
                    it.key
                }
            }, { it.value })
        }

        val emptyColumns = 0.until(21).filter { x ->
            gridWithout.keys.none { it.x == x }
        }
        emptyColumns.reversed().forEach { x ->
            gridWithout = gridWithout.entries.associateBy({
                if (it.key.x > x) {
                    Coordinate(it.key.x - 1, it.key.y)
                } else {
                    it.key
                }
            }, { it.value })
        }

        return gridWithout
    }

    fun getAttachedChars(charLocation: Coordinate, grid: Map<Coordinate, Char>): Set<Coordinate> {
        val charAtLocation = grid.getValue(charLocation)
//        println("removing $charAtLocation")

        var unVisitedChars = setOf(charLocation)
        var visitedChars = setOf<Coordinate>()

        while (unVisitedChars.isNotEmpty()) {
            val currentChar = unVisitedChars.first()
            unVisitedChars -= currentChar
            visitedChars += currentChar

            unVisitedChars += currentChar.getNeighbours()
                    .filter { !visitedChars.contains(it) }
                    .filter { !unVisitedChars.contains(it) }
                    .filter { grid.contains(it) }
                    .filter { grid.getValue(it) == charAtLocation }
        }

        return visitedChars
    }
}

fun drawGrid(grid: Map<Coordinate, Char>) {
    0.until(21).forEach { y ->
        val row = StringBuilder()
        0.until(21).forEach { x ->
            if (!grid.containsKey(Coordinate(x, y))) {
                row.append('.')
            } else {
                row.append(grid.getValue(Coordinate(x, y)))
            }
        }
        println(row)
    }
}
