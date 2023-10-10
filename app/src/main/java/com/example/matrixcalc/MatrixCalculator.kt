package com.example.matrixcalc

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun MatrixCalculator(onColorSchemeChange: () -> Unit) {
    var matrix1Rows by remember { mutableStateOf(3) }
    var matrix1Cols by remember { mutableStateOf(3) }
    var matrix1 by remember(
        matrix1Rows,
        matrix1Cols
    ) { mutableStateOf(Array(matrix1Rows) { Array(matrix1Cols) { 0f } }) }

    var matrix2Rows by remember { mutableStateOf(3) }
    var matrix2Cols by remember { mutableStateOf(3) }
    var matrix2 by remember(
        matrix2Rows,
        matrix2Cols
    ) { mutableStateOf(Array(matrix2Rows) { Array(matrix2Cols) { 0f } }) }

    var resultMatrix by remember { mutableStateOf<Array<Array<Float>>?>(null) }

    var errorDialogState by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val results = remember { mutableStateListOf<String>() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Matrix Calculator") },
                actions = {
                    IconButton(onClick = {
                        onColorSchemeChange()
                    }) {

                        Icon(
                            painter = painterResource(R.drawable.dark_mode),
                            contentDescription = "Clear"
                        )
                    }
                }
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Add UI for inputting two matrices
                    Text("Matrix A")
                    MatrixSizeInput { rows, cols ->
                        matrix1Rows = rows
                        matrix1Cols = cols
                        matrix1 = Array(matrix1Rows) { Array(matrix1Cols) { 0f } }
                    }
                    matrix1 = InputMatrix(matrix1)
                    OperationWithMatrix(onDetClick = {

                        try {

                            results.add(
                                0,
                                "Determinant: " + findDeterminant(matrix1).toString()
                            )
                        } catch (e: IllegalArgumentException) {
                            errorDialogState = true
                            errorMessage = e.message ?: ""
                        }
                    },
                        onTransClick = {


                            resultMatrix = transposeMatrix(matrix1)
                            results.add(
                                0, "Transpose :\n" +
                                        "${joinToString(resultMatrix!!)}"
                            )

                        },
                        onRankClick = {


                            results.add(0, "Rank: " + " ${matrixRank(matrix1)}")

                        },
                        onReverseClick = {
                            try {

                                results.add(
                                    0,
                                    "Inverse Matrix:\n${joinToString(reverseMatrix(matrix1))}"
                                )
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Matrix B")
                    MatrixSizeInput { rows, cols ->
                        matrix2Rows = rows
                        matrix2Cols = cols
                        matrix2 = Array(matrix2Rows) { Array(matrix2Cols) { 0f } }
                    }
                    matrix2 = InputMatrix(matrix2)
                    OperationWithMatrix(onDetClick = {
                        try {

                            results.add(
                                0,
                                "Determinant: " + findDeterminant(matrix2).toString()
                            )
                        } catch (e: IllegalArgumentException) {
                            errorDialogState = true
                            errorMessage = e.message ?: ""
                        }
                    },
                        onTransClick = {
                            resultMatrix = transposeMatrix(matrix2)
                            results.add(
                                0, "Transpose :\n" +
                                        "${joinToString(resultMatrix!!)}"
                            )

                        },
                        onRankClick = {
                            results.add(0, "Rank: " + " ${matrixRank(matrix2)}")
                        },
                        onReverseClick = {
                            try {

                                results.add(
                                    0,
                                    "Inverse Matrix:\n${joinToString(reverseMatrix(matrix2))}"
                                )
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add buttons for performing operations
                    OperationButtons(
                        onAddClick = {
                            try {
                                resultMatrix = addMatrices(matrix1, matrix2)

                                results.add(0, "Addition Result:\n${joinToString(resultMatrix!!)}")
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        },
                        onSubtractClick = {
                            try {
                                resultMatrix = subtractMatrices(matrix1, matrix2)
                                results.add(
                                    0, "Subtraction Result\n" +
                                            "${joinToString(resultMatrix!!)}"
                                )
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        },
                        onMultiplyClick = {
                            try {
                                resultMatrix = multiplyMatrices(matrix1, matrix2)
                                results.add(
                                    0, "Multiplication Result\n" +
                                            "${joinToString(resultMatrix!!)}"
                                )
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }
                    )


                    // Display result matrix

                    if (results.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Results")
                        ResultsList(results)
                    }

                }
            }
        }
    )

    if (errorDialogState) {
        ErrorDialog(errorMessage = errorMessage) {
            errorDialogState = false
        }
    }
}


