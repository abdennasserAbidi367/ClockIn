package com.example.mediconnect.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mediconnect.R

@Composable
fun CustomDialogWithContent(setShowDialog: (Boolean, String) -> Unit) {

    val radioOptions = listOf("In", "Out")
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    Dialog(onDismissRequest = {  }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "You want to clock:",
                        Modifier.padding(top = 15.dp),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Row(modifier = Modifier.padding(top = 15.dp)) {
                        radioOptions.forEach { clock ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = (clock == selectedOption),
                                    onClick = { selectedOption = clock }
                                )

                                Text(text = clock)
                            }
                        }

                    }

                    Box(modifier = Modifier.padding(40.dp, 10.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                setShowDialog(false, selectedOption)
                            },
                            shape = RoundedCornerShape(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_200)),
                            modifier = Modifier
                                .fillMaxWidth(1.5f)
                                .height(50.dp)
                        ) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
    }
}