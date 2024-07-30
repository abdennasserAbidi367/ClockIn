package com.example.mediconnect.feature.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mediconnect.R

@Composable
fun TestScreen(navController: NavController) {
    Text(text = "fzgggg", color = colorResource(id = R.color.purple_200), modifier = Modifier.height(150.dp).width(150.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontFamily = FontFamily(
                Font(
                    R.font.rubikbold,
                    weight = FontWeight.Bold
                )
            )
        )
    )
}