package com.example.matrixcalc


import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun OperationButtons(
    onAddClick: () -> Unit,
    onSubtractClick: () -> Unit,
    onMultiplyClick: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = onAddClick) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onSubtractClick) {
            Text("Subtract")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onMultiplyClick) {
            Text("Multiply")
        }
    }
}

@Composable
fun OperationWithMatrix(
    onDetClick: () -> Unit,
    onTransClick: () -> Unit,
    onRankClick: () -> Unit,
    onReverseClick: () -> Unit,
) {
    Column(Modifier.width(275.dp)) {
        Row {
            Button(
                onClick = onDetClick,
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Determinant")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = onReverseClick,
                Modifier.fillMaxWidth()
            ) {

                Text("Inverse")
            }
        }
        Row {
            Button(
                onClick = onTransClick,
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Transpose")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = onRankClick, Modifier.fillMaxWidth()) {
                Text("Rank")
            }
        }

    }


}

@Composable
fun ErrorDialog(errorMessage: String, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onClose() },
        title = { Text("Error") },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = { onClose() }) {
                Text("OK")
            }
        }
    )
}


@Composable
fun MatrixSizeInput(onSizeChange: (Int, Int) -> Unit) {
    var rows by remember { mutableStateOf(3) }
    var cols by remember { mutableStateOf(3) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Rows: $rows")
            Row {
                Button(onClick = {
                    if (rows > 1) rows--
                    onSizeChange(rows, cols)
                }) {
                    Text(text = "-")

                }
                Button(onClick = {
                    if (rows < 6) rows++
                    onSizeChange(rows, cols)
                }) {
                    Text(text = "+")
                }
            }
        }

        Spacer(modifier = Modifier.width(18.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Cols: $cols")
            Row {
                Button(onClick = {
                    if (cols > 1) cols--
                    onSizeChange(rows, cols)


                }) {
                    Text(text = "-")

                }
                Button(onClick = {
                    if (cols < 6) cols++
                    onSizeChange(rows, cols)
                }) {
                    Text(text = "+")
                }

            }
        }
    }
}

@Composable
fun ResultsList(results: List<String>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        results.forEach { result ->
            Text(result)
        }
    }
}

@Composable
fun InputMatrix(matrix: Array<Array<Float>>): Array<Array<Float>> {
    Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {


        for (i in matrix.indices) {
            Row {
                for (j in matrix[i].indices) {
                    var element by remember { mutableStateOf(matrix[i][j].toString()) }
                    var previousText by remember { mutableStateOf("") }
                    TextField(
                        value = element,
                        onValueChange = { newValue ->
                            element = newValue
                            matrix[i][j] = newValue.toFloatOrNull() ?: 0f
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .padding(5.dp)
                            .onFocusChanged { focusState ->
                                if (!focusState.isFocused && element.isEmpty()) {
                                    element = previousText
                                } else if (focusState.isFocused) {
                                    previousText = element
                                    element = ""
                                }
                            }  // Specify the width of TextField to avoid overflow
                    )
                }
            }
        }
    }
    return matrix
}

@Composable
fun RandomMatrixButton(onRandomMatrix: (Array<Array<Float>>) -> Unit) {
    Button(onClick = { onRandomMatrix(generateRandomMatrix(3, 3, 0..10)) }) {
        Text("Random Matrix")
    }
}

fun generateRandomMatrix(rows: Int, cols: Int, rage: IntRange): Array<Array<Float>> {
    return Array(rows) {
        Array(cols) {
            Random.nextInt(rage).toFloat()
        }
    }
}
