package ext

import matrix.Matrix

fun Array<Array<Number>>.toMatrix() = Matrix(this)

fun List<Array<Number>>.toMatrix() = Matrix(this.toTypedArray())