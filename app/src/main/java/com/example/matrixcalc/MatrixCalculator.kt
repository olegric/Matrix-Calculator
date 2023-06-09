package com.example.matrixcalc

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MatrixCalculator() {
    var matrix1Rows by remember { mutableStateOf(3) }
    var matrix1Cols by remember { mutableStateOf(3) }
    var matrix1 by remember(
        matrix1Rows,
        matrix1Cols
    ) { mutableStateOf(Array(matrix1Rows) { Array(matrix1Cols) { 0 } }) }

    var matrix2Rows by remember { mutableStateOf(3) }
    var matrix2Cols by remember { mutableStateOf(3) }
    var matrix2 by remember(
        matrix2Rows,
        matrix2Cols
    ) { mutableStateOf(Array(matrix2Rows) { Array(matrix2Cols) { 0 } }) }

    var resultMatrix by remember { mutableStateOf<Array<Array<Int>>?>(null) }

    var errorDialogState by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val results = remember { mutableStateListOf<String>() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Matrix Calculator") },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Clear")
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
                        matrix1 = Array(matrix1Rows) { Array(matrix1Cols) { 0 } }
                    }
                    matrix1 = InputMatrix(matrix1)
                    OperationWithMatrix(matrix = matrix1)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Matrix B")
                    MatrixSizeInput { rows, cols ->
                        matrix2Rows = rows
                        matrix2Cols = cols
                        matrix2 = Array(matrix2Rows) { Array(matrix2Cols) { 0 } }
                    }
                    matrix2 = InputMatrix(matrix2)
                    OperationWithMatrix(matrix = matrix2)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Add buttons for performing operations
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(onClick = {
                            try {
                                resultMatrix = addMatrices(matrix1, matrix2)

                                results.add(0, "Addition Result: ${resultMatrix!!.joinToString()}")
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }) {
                            Text("Add")
                        }

                        Button(onClick = {
                            try {
                                resultMatrix = subtractMatrices(matrix1, matrix2)
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }) {
                            Text("Subtract")
                        }

                        Button(onClick = {
                            try {
                                resultMatrix = multiplyMatrices(matrix1, matrix2)
                            } catch (e: IllegalArgumentException) {
                                errorDialogState = true
                                errorMessage = e.message ?: ""
                            }
                        }) {
                            Text("Multiply")
                        }
                    }


                    // Display result matrix
                    resultMatrix?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Result")
                        DisplayMatrix(it)
                    }
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