package matrix

import ext.*

class Matrix {
    val value: Array<Array<Number>>
    val rowsCount: Int
    val columnsCount: Int

    constructor(vararg value: List<Number>) {
        val maxRow = value.maxOf {
            it.size
        }
        this.value = value.map {
            val delta = maxRow - it.size
            if (delta == 0) it.toTypedArray()
            else (it + List(delta) {0}).toTypedArray()
        }.toTypedArray()

        rowsCount = this.value.size
        columnsCount = maxRow
    }

    constructor(values: Array<Array<Number>>) {
        val maxRow = values.maxOf {
            it.size
        }
        this.value = values.map {
            val delta = maxRow - it.size
            if (delta == 0) it
            else (it + List(delta) {0})
        }.toTypedArray()

        rowsCount = this.value.size
        columnsCount = maxRow
    }

    operator fun plus(other: Matrix): Matrix {
        ensureSameDimensions(this, other)
        return Matrix(
            value.mapIndexed { row, numbers ->
                numbers.mapIndexed { column, number ->
                    number + other.value[row][column]
                }.toTypedArray()
            }.toTypedArray()
        )
    }

    operator fun minus(other: Matrix): Matrix {
        ensureSameDimensions(this, other)
        return Matrix(
            value.mapIndexed { row, numbers ->
                numbers.mapIndexed { column, number ->
                    number - other.value[row][column]
                }.toTypedArray()
            }.toTypedArray()
        )
    }

    operator fun div(other: Matrix) = this * other.inverted()

    operator fun times(other: Matrix): Matrix {
        if (columnsCount != other.rowsCount)
            throw IllegalArgumentException("Matrices are not multipliable")

        val product = Array(rowsCount) { Array<Number>(other.columnsCount) { 0 } }
        for (i in product.indices) {
            for (j in product[i].indices) {
                for (k in other.value.indices) {
                    product[i][j] += value[i][k] * other.value[k][j]
                }
            }
        }
        return Matrix(product)
    }

    operator fun get(rowIndex: Int, colIndex: Int): Number {
        if (rowIndex < 0 || colIndex < 0 || rowIndex >= rowsCount || colIndex >= columnsCount)
            throw IllegalArgumentException("Matrix.get: Index out of bounds")
        return value[rowIndex][colIndex]
    }

    fun minorMatrix(rowSlice: Int, colSlice: Int): Matrix {

        val slice = value.filterIndexed { index, _ ->
            index != rowSlice
        }.toTypedArray()

        slice.forEachIndexed { index, numbers ->
            slice[index] = numbers.filterIndexed { column, number ->
                column != colSlice
            }.toTypedArray()
        }

        return slice.toMatrix()
    }

    fun cofactor(row: Int, column: Int): Double {
        val sign = if ((column + row) % 2 == 0) 1 else -1
        return minorMatrix(row, column).determinant() * sign
    }

    fun determinant(): Double {
        if (rowsCount != columnsCount) throw IllegalArgumentException("Matrix.determinant: Only available for square matrices")
        return when (rowsCount) {
            1 -> this[0, 0].toDouble()
            2 -> (this[0, 0] * this[1, 1] - this[0, 1] * this[1, 0]).toDouble()
            else -> {
                var sum = 0.0
                for (column in value.indices) {
                    val sign = if (column % 2 == 0) 1 else -1
                    sum += (this[0, column] * cofactor(0, column)).toDouble()
                }

                sum
            }
        }
    }

    private operator fun times(number: Number) =
        this.value.map {
            it.map {
                it * number
            }.toTypedArray()
        }.toMatrix()

    fun switchRow(rowIndex1: Int, rowIndex2: Int): Matrix {
        return if (rowIndex1 < 0 || rowIndex2 < 0 || rowIndex1 >= rowsCount || rowIndex2 >= rowsCount) {
            throw IllegalArgumentException("lib.real.Matrix.switchRow: Index out of bound")
        } else if (rowIndex1 == rowIndex2) {
            this
        } else {
            val newData = value.copyOf()
            val t = newData[rowIndex1]
            newData[rowIndex1] = newData[rowIndex2]
            newData[rowIndex2] = t

            newData.toMatrix()
        }
    }

    fun getSubmatrix(rowIndexStart: Int, rowIndexEnd: Int, colIndexStart: Int, colIndexEnd: Int): Matrix {
        return if (rowIndexStart < 0 || colIndexStart < 0 || rowIndexStart >= rowIndexEnd || colIndexStart >= colIndexEnd
            || rowIndexEnd > rowsCount || colIndexEnd > columnsCount) {
            throw IllegalArgumentException("Matrix.Submatrix: Index out of bounds")
        } else {
            val slice = value.filterIndexed { index, _ ->
                index in rowIndexStart until rowIndexEnd
            }.toTypedArray()

            slice.forEachIndexed { index, numbers ->
                slice[index] = numbers.filterIndexed { column, number ->
                    column in colIndexStart until colIndexEnd
                }.toTypedArray()
            }

            slice.toMatrix()
        }
    }

    fun inverted(): Matrix {
        if (this.determinant() == 0.0) {
            throw IllegalCallerException("Matrix.inverted: Matrix determinant is zero, so inverted matrix does not exist")
        }

        return adjugate() * (1 / this.determinant())
    }

    fun transposed() =
        value.mapIndexed { row, rows ->
            rows.mapIndexed { column, _ ->
                value[column][row]
            }.toTypedArray()
        }.toMatrix()

    fun adjugate() =
        value.mapIndexed { column, it ->
            it.mapIndexed { row, _ ->
                cofactor(column, row) as Number
            }.toTypedArray()
        }.toMatrix().transposed()

    override fun equals(other: Any?): Boolean {
        if (other !is Matrix) return false
        if (rowsCount != other.rowsCount) return false
        if (columnsCount != other.columnsCount) return false
        return !value.mapIndexed { row, numbers ->
            if (other.value[row][0] is Double) {
                return@mapIndexed numbers.mapIndexed { column, number ->
                    (other.value[row][column].toDouble()).impreciselyEquals(number.toDouble())
                }
            }
            numbers.contentEquals(other.value[row])
        }.contains(false)
    }

    override fun hashCode(): Int {
        var result = value.contentDeepHashCode()
        result = 31 * result + rowsCount
        result = 31 * result + columnsCount
        return result
    }

    override fun toString(): String {
        return value.joinToString(
            separator = "\n"
        ) {
            it.joinToString(
                prefix = "[",
                postfix = "]"
            )
        }
    }
}

fun ensureSameDimensions(m1: Matrix, m2: Matrix) {
    if (m1.rowsCount != m2.rowsCount || m1.columnsCount != m2.columnsCount)
        throw IllegalArgumentException("Matrices are of different size")

}