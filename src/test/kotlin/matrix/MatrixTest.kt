package matrix

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class MatrixTest {

    private val matrix1 = Matrix(
        listOf(1, 2, 3),
        listOf(4),
        listOf(0, 5),
        listOf(6, 7, 8)
    )

    private val matrix2 = Matrix(
        listOf(9, 8, 7),
        listOf(6, 10, 10),
        listOf(10, 5, 10),
        listOf(4, 3, 2)
    )

    private val matrix3 = Matrix(
        listOf(10, 10, 10),
        listOf(10, 10, 10),
        listOf(10, 10, 10),
        listOf(10, 10, 10),
    )

    private val matrix4 = Matrix(
        listOf(1, 2, 3, 4),
        listOf(1, 2, 3, 4),
        listOf(1, 2, 3, 4)
    )

    private val matrix5 = Matrix(
        listOf(30, 60, 90, 120),
        listOf(30, 60, 90, 120),
        listOf(30, 60, 90, 120),
        listOf(30, 60, 90, 120)
    )

    private val matrix6 = Matrix(
        listOf(100, 100, 100),
        listOf(100, 100, 100),
        listOf(100, 100, 100)
    )

    private val subMatrix6 = Matrix(
        listOf(100, 100),
        listOf(100, 100)
    )

    private val matrix7 = Matrix(
        listOf(1, 4, 54, 4),
        listOf(3, 5, 54, 2),
        listOf(6, 78, 34, 54),
        listOf(6, 7, 45, 34),
    )

    private val matrix8 = Matrix(
        listOf(4, -3, 5),
        listOf(1, 0, 3),
        listOf(-1, 5, 2),
    )

    private val matrix9 = Matrix(
        listOf(4, -3, 5),
        listOf(-1, 5, 2),
        listOf(1, 0, 3),
    )

    private val matrix10 = Matrix(
        listOf(0, 3),
        listOf(5, 2)
    )

    @Test
    fun `rows count getter returns correct value`() {
        assertThat(matrix1.rowsCount).isEqualTo(4)
    }

    @Test
    fun `columns count getter returns correct value`() {
        assertThat(matrix1.columnsCount).isEqualTo(3)
    }

    @Test
    fun `matrix1 + matrix2 returns matrix3`() {
        assertThat(matrix1 + matrix2).isEqualTo(matrix3)
    }

    @Test
    fun `matrix3 - matrix2 returns matrix1`() {
        assertThat(matrix3 - matrix2).isEqualTo(matrix1)
    }

    @Test
    fun `correct submatrix calculation`() {
        assertThat(matrix6.minorMatrix(1, 1)).isEqualTo(subMatrix6)
    }

    @Test
    fun `determinant of matrix5 is zero`() {
        assertThat(matrix5.determinant()).isEqualTo(0)
    }

    @Test
    fun `determinant of matrix7 is 275070`() {
        assertThat(matrix7.determinant()).isEqualTo(275070)
    }


    // 4 * |-15| - (-3) * |5| + 5 * |5|
    // -60 + 15 + 25 = -20
    @Test
    fun `determinant of matrix8 is -20 `() {
        assertThat(matrix8.determinant()).isEqualTo(-20)
    }

    @Test
    fun `switching row 1 and 2 of matrix8 produces matrix9`() {
        assertThat(matrix8.switchRow(1, 2)).isEqualTo(matrix9)
    }

    @Test
    fun `slcing matrix8 produces matrix10`() {
        assertThat(matrix8.getSubmatrix(1, matrix8.rowsCount, 1, matrix8.columnsCount))
            .isEqualTo(matrix10)
    }

    @Test
    fun div() {
        assert(false)
    }

    @Test
    fun `matrix3 * matrix4 returns matrix5`() {
        assertThat(matrix3 * matrix4).isEqualTo(matrix5)
    }

    @Test
    fun `matrix4 * matrix3 returns matrix6`() {
        assertThat(matrix4 * matrix3).isEqualTo(matrix6)
    }
}