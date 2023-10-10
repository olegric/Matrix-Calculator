package com.example.matrixcalc


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.matrixcalc.ui.theme.MatrixCalcTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Встановлення початкового набору кольорів
            val lightColorScheme = lightColors(
                primary = Color(0xFF358BE0),
                secondary = Color(0xFFFF0000),
                background = Color(0xFFFFFFFF),
                onPrimary = Color(0xFF000000)
            )

            val darkColorScheme = darkColors(
                primary = Color(0xFF001785),
                secondary = Color(0xFF00FF00),
                background = Color(0xFF000000),
                onPrimary = Color(0xFFFFFFFF)

            )

            // Створення стану для вибору набору кольорів
            val selectedColorScheme = remember { mutableStateOf(lightColorScheme) }
            setContent {
                MatrixCalcTheme {
                    MaterialTheme(
                        colors = selectedColorScheme.value


                    ) {


                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {

                            MatrixCalculator(onColorSchemeChange = {
                                selectedColorScheme.value =
                                    if (selectedColorScheme.value == lightColorScheme) {
                                        darkColorScheme
                                    } else {
                                        lightColorScheme
                                    }
                            }
                            )
                        }
                    }
                }

            }

        }


    }


}




