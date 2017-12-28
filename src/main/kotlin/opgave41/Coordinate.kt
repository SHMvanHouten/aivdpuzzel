package opgave41

import opgave41.RelativePosition.*


data class Coordinate(val x: Int, val y: Int) {
    fun getNeighbours(): Set<Coordinate> {
        return setOf(
                this + TOP.coordinate,
                this + RIGHT.coordinate,
                this + BOTTOM.coordinate,
                this + LEFT.coordinate
        )
    }

    fun getNeighbour(directionPointed: Direction): Coordinate {
        return when (directionPointed) {
            Direction.NORTH -> this + TOP.coordinate
            Direction.EAST -> this + RIGHT.coordinate
            Direction.SOUTH -> this + BOTTOM.coordinate
            Direction.WEST -> this + LEFT.coordinate
        }
    }

    operator fun plus(otherCoordinate: Coordinate): Coordinate {
        val x = this.x + otherCoordinate.x
        val y = this.y + otherCoordinate.y
        return Coordinate(x, y)
    }

    fun move(direction: Direction): Coordinate {
        return when (direction) {
            Direction.NORTH -> this + TOP.coordinate
            Direction.EAST -> this + RIGHT.coordinate
            Direction.SOUTH -> this + BOTTOM.coordinate
            Direction.WEST -> this + LEFT.coordinate
        }
    }
}