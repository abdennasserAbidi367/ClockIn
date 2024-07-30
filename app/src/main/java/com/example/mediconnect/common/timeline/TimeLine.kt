package com.example.mediconnect.common.timeline

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.mediconnect.R

@Composable
fun TimeLine(text: String, isLast: Boolean, isChecked: Boolean) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val s = (screenWidth - 120.dp)/2
    Log.i("sizeLine", "TimeLine: $s")

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.purple_200),
                shape = CircleShape
            ),
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "contentDescription",
                modifier = Modifier
                    .size(24.dp)
                    .background(colorResource(id = R.color.purple_200), CircleShape)
                    .padding(2.dp),
                tint = Color.White
            )
        } else {
            Text(text = text, color = colorResource(id = R.color.purple_200))
        }
    }

    val colorState = if (isChecked) colorResource(id = R.color.purple_200) else colorResource(id = R.color.marron)
    if (!isLast) {
        Box(
            modifier = Modifier
                .height(5.dp)
                .width(s)
                .background(colorResource(id = R.color.purple_200))
        ) {}
    }
}