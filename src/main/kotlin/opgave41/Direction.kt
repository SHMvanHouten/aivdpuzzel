package opgave41

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;


    fun turnLeft(): Direction {
        return if (this == NORTH) {
            WEST
        } else {
            Direction.values()[this.ordinal - 1]
        }
    }

    fun turnRight(): Direction {
        return if (this == WEST) {
            NORTH
        } else {
            Direction.values()[this.ordinal + 1]
        }
    }

    fun turnBack(): Direction{
        return when(this){
            Direction.NORTH -> SOUTH
            Direction.EAST -> WEST
            Direction.SOUTH -> NORTH
            Direction.WEST -> EAST
        }
    }
}

enum class RelativePosition(val coordinate: Coordinate) {
    TOP(Coordinate(0, -1)),
    TOP_RIGHT(Coordinate(1, -1)),
    RIGHT(Coordinate(1, 0)),
    BOTTOM_RIGHT(Coordinate(1, 1)),
    BOTTOM(Coordinate(0, 1)),
    BOTTOM_LEFT(Coordinate(-1, 1)),
    LEFT(Coordinate(-1, 0)),
    TOP_LEFT(Coordinate(-1, -1))
}
