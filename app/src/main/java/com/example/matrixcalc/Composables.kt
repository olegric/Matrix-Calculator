package com.example.matrixcalc



import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.unit.dp

@Composable
fun OperationWithMatrix(matrix: Array<Array<Int>>) {
    Column(Modifier.width(275.dp)) {
        Row {
            Button(
                onClick = { findDeterminant(matrix) },
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Determinant")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = { reverseMatrix(matrix) },
                Modifier.fillMaxWidth()
            ) {

                Text("Inverse")
            }
        }
        Row {
            Button(
                onClick = { transposeMatrix(matrix) },
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Transpose")
            }
            Spacer(modifier = Modifier.width(5.dp))
            Button(onClick = { matrixRank(matrix) }, Modifier.fillMaxWidth()) {
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
fun DisplayMatrix(matrix: Array<Array<Int>>) {
    Column {
        for (i in matrix.indices) {
            Row(modifier = Modifier.padding(8.dp)) {
                for (j in matrix[i].indices) {
                    Text(
                        text = matrix[i][j].toString(),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
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
                    rows--
                    onSizeChange(if (rows > 0) rows else 3, if (cols > 0) cols else 3)
                }) {
                    Text(text = "-")

                }
                Button(onClick = {
                    rows++
                    onSizeChange(if (rows > 0) rows else 3, if (cols > 0) cols else 3)
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
                    cols--
                    onSizeChange(if (rows > 0) rows else 3, if (cols > 0) cols else 3)
                }) {
                    Text(text = "-")

                }
                Button(onClick = {
                    cols++
                    onSizeChange(if (rows > 0) rows else 3, if (cols > 0) cols else 3)
                }) {
                    Text(text = "+")
                }

            }
        }
    }
}
@Composable
fun ResultsList(results: List<String>) {
    Column {
        results.forEach { result ->
            Text(result)
        }
    }
}
@Composable
fun InputMatrix(matrix: Array<Array<Int>>): Array<Array<Int>> {
    for (i in matrix.indices) {
        Row {
            for (j in matrix[i].indices) {
                var element by remember { mutableStateOf(matrix[i][j].toString()) }
                TextField(
                    value = element,
                    onValueChange = { newValue ->
                        element = newValue
                        matrix[i][j] = newValue.toIntOrNull() ?: 0
                    },
                    modifier = Modifier
                        .width(60.dp)
                        .padding(5.dp)  // Specify the width of TextField to avoid overflow
                )
            }
        }
    }
    return matrix
}
