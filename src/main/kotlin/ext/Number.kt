package ext

import kotlin.math.absoluteValue

operator fun Number.minus(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() - other.toLong()
        is Int    -> this.toInt()  - other.toInt()
        is Short  -> this.toShort() - other.toShort()
        is Byte   -> this.toByte() - other.toByte()
        is Double -> this.toDouble() - other.toDouble()
        is Float  -> this.toFloat() - other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.plus(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() + other.toLong()
        is Int    -> this.toInt()  + other.toInt()
        is Short  -> this.toShort() + other.toShort()
        is Byte   -> this.toByte() + other.toByte()
        is Double -> this.toDouble() + other.toDouble()
        is Float  -> this.toFloat() + other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.times(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() * other.toLong()
        is Int    -> this.toInt()  * other.toInt()
        is Short  -> this.toShort() * other.toShort()
        is Byte   -> this.toByte() * other.toByte()
        is Double -> this.toDouble() * other.toDouble()
        is Float  -> this.toFloat() * other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

operator fun Number.div(other: Number): Number {
    return when (this) {
        is Long   -> this.toLong() / other.toLong()
        is Int    -> this.toInt()  / other.toInt()
        is Short  -> this.toShort() / other.toShort()
        is Byte   -> this.toByte() / other.toByte()
        is Double -> this.toDouble() / other.toDouble()
        is Float  -> this.toFloat() / other.toFloat()
        else      -> throw RuntimeException("Unknown numeric type")
    }
}

fun Double.impreciselyEquals(other: Double, precision: Double = 0.00000000000001) {
    (this - other).absoluteValue < precision
}