package matrix

class Matrix(
    vararg value: List<Number>
) {


    val value: Array<Array<Number>>
    val rowsCount: Int
    val columnsCount: Int

    init {
        val maxRow = value.maxOf {
            it.size
        }
        this.value = value.map {
            val delta = maxRow - it.size
            if (delta == 0) it.toTypedArray()
            else (it + List(delta) {0}).toTypedArray()
        }.toTypedArray()

        rowsCount = maxRow
        columnsCount = this.value.size
    }

    operator fun plus(other: Matrix): Matrix {
        return emptyMatrix()
    }

    operator fun minus(other: Matrix) = emptyMatrix()

    operator fun div(other: Matrix) = emptyMatrix()

    operator fun times(other: Matrix) = emptyMatrix()
}

fun emptyMatrix() = Matrix()