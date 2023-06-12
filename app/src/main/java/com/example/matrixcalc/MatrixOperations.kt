package com.example.matrixcalc

import kotlin.math.roundToInt


fun addMatrices(matrix1: Array<Array<Int>>, matrix2: Array<Array<Int>>): Array<Array<Int>> {
    if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
        throw IllegalArgumentException("Matrix dimensions do not match for addition")
    }

    val result = Array(matrix1.size) { Array(matrix1[0].size) { 0 } }
    for (i in matrix1.indices) {
        for (j in matrix1[i].indices) {
            result[i][j] = matrix1[i][j] + matrix2[i][j]
        }
    }
    return result
}

fun subtractMatrices(
    matrix1: Array<Array<Int>>,
    matrix2: Array<Array<Int>>
): Array<Array<Int>> {
    if (matrix1.size != matrix2.size || matrix1[0].size != matrix2[0].size) {
        throw IllegalArgumentException("Matrix dimensions do not match for subtraction")
    }

    val result = Array(matrix1.size) { Array(matrix1[0].size) { 0 } }
    for (i in matrix1.indices) {
        for (j in matrix1[i].indices) {
            result[i][j] = matrix1[i][j] - matrix2[i][j]
        }
    }
    return result
}

fun multiplyMatrices(
    matrix1: Array<Array<Int>>,
    matrix2: Array<Array<Int>>
): Array<Array<Int>> {
    if (matrix1[0].size != matrix2.size) {
        throw IllegalArgumentException("Matrix dimensions do not match for multiplication")
    }

    val result = Array(matrix1.size) { Array(matrix2[0].size) { 0 } }
    for (i in matrix1.indices) {
        for (j in matrix2[0].indices) {
            for (k in matrix2.indices) {
                result[i][j] += matrix1[i][k] * matrix2[k][j]
            }
        }
    }
    return result
}

fun findDeterminant(matrix: Array<Array<Int>>): Float {
    val size = matrix.size
    if( matrix.size != matrix[0].size){
        throw IllegalArgumentException("Matrix cannot be empty and must be square")}


    if (size == 1) {
        return matrix[0][0].toFloat()
    }

    var determinant = 0f

    for (column in 0 until size) {
        val subMatrix = Array(size - 1) { Array(size - 1) { 0 } }

        for (i in 1 until size) {
            var subMatrixColumn = 0
            for (j in 0 until size) {
                if (j != column) {
                    subMatrix[i - 1][subMatrixColumn] = matrix[i][j]
                    subMatrixColumn++
                }
            }
        }

        val cofactor = if (column % 2 == 0) 1 else -1
        determinant += cofactor * matrix[0][column] * findDeterminant(subMatrix)
    }

    return determinant
}

fun reverseMatrix(matrix: Array<Array<Int>>): Array<Array<Float>> {
    val size = matrix.size

    if (size != matrix[0].size) {
        throw IllegalArgumentException("Matrix cannot be empty and must be square")
    }

    val determinant = findDeterminant(matrix)
    if (determinant == 0f) {
        throw IllegalArgumentException("Matrix must be invertible")
    }

    val adjugateMatrix = Array(size) { Array(size) { 0f } }
    for (row in 0 until size) {
        for (col in 0 until size) {
            val cofactor = if ((row + col) % 2 == 0) 1 else -1
            val minor = findDeterminant(createMinor(matrix, row, col))
            adjugateMatrix[col][row] = (cofactor * minor).toFloat()
        }
    }

    val scalar = 1 / determinant.toDouble()
    val reversedMatrix = Array(size) { Array(size) { 0f } }
    for (row in 0 until size) {
        for (col in 0 until size) {
            reversedMatrix[row][col] = (scalar * adjugateMatrix[row][col]).toFloat()
        }
    }

    return reversedMatrix
}

fun createMinor(
    matrix: Array<Array<Int>>,
    rowToRemove: Int,
    colToRemove: Int
): Array<Array<Int>> {
    val size = matrix.size
    require(size > 1) { "Matrix must have a size greater than 1" }
    require(rowToRemove in 0 until size) { "Invalid row index" }
    require(colToRemove in 0 until size) { "Invalid column index" }

    val minor = Array(size - 1) { Array(size - 1) { 0 } }
    var minorRow = 0

    for (row in 0 until size) {
        if (row == rowToRemove) continue

        var minorCol = 0
        for (col in 0 until size) {
            if (col == colToRemove) continue

            minor[minorRow][minorCol] = matrix[row][col]
            minorCol++
        }

        minorRow++
    }

    return minor
}



fun matrixRank(matrix: Array<Array<Int>>): Int {
    val rows = matrix.size
    val cols = matrix[0].size

    var rank = 0
    val rowEchelonForm = matrix.copyOf()
    var lead = 0

    for (row in 0 until rows) {
        if (lead >= cols) {
            break
        }

        var i = row
        while (rowEchelonForm[i][lead] == 0) {
            i++
            if (i == rows) {
                i = row
                lead++
                if (lead == cols) {
                    break
                }
            }
        }

        if (lead < cols) {
            val temp = rowEchelonForm[i]
            rowEchelonForm[i] = rowEchelonForm[row]
            rowEchelonForm[row] = temp

            val divisor = rowEchelonForm[row][lead]
            for (j in 0 until cols) {
                rowEchelonForm[row][j] /= divisor
            }

            for (k in 0 until rows) {
                if (k != row) {
                    val multiple = rowEchelonForm[k][lead]
                    for (j in 0 until cols) {
                        rowEchelonForm[k][j] -= rowEchelonForm[row][j] * multiple
                    }
                }
            }

            lead++
            rank++
        }
    }

    return rank
}


fun transposeMatrix(matrix: Array<Array<Int>>): Array<Array<Int>> {
    val rows = matrix.size
    val cols = matrix[0].size

    val transposedMatrix = Array(cols) { Array(rows) { 0 } }
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            transposedMatrix[col][row] = matrix[row][col]
        }
    }

    return transposedMatrix
}
fun joinToString(matrix: Array<Array<Int>>): String {
    var str = ""
    val rows = matrix.size
    val cols = matrix[0].size
    for (row in 0 until rows) {
        for (col in 0 until cols) {

            var num = ""+(matrix[row][col])

            str = str.plus("--".repeat(6-num.length-2)  + num) // Assign the concatenated string back to str
        }
        str = str.plus("\n") // Assign the concatenated string back to str
    }
    return str
}
fun joinToString(matrix: Array<Array<Float>>): String {
    var str = ""
    val rows = matrix.size
    val cols = matrix[0].size
    for (row in 0 until rows) {
        for (col in 0 until cols) {
            val num = ""+((matrix[row][col] * 100.0).roundToInt() / 100.0)
                 str = str.plus(" ".repeat(6-num.length)  + num)
        }
        str = str.plus("\n") // Assign the concatenated string back to str
    }
    return str
}

