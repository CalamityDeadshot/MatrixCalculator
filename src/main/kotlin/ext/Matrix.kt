package ext

import matrix.Matrix

fun Array<Array<Number>>.toMatrix() = Matrix(this)

fun List<Array<Number>>.toMatrix() = Matrix(this.toTypedArray())

fun emptyMatrix() = Matrix()

fun identityMatrixOfSize(size: Int) = Matrix(
    Array(size) { row ->
        Array(size) { column ->
            if (row == column) 1 else 0
        }
    }
)