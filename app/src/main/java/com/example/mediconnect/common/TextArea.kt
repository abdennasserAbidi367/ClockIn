package com.example.mediconnect.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.mediconnect.R

@Composable
fun TextArea(addComment: Boolean, onValue: (comment: String) -> Unit) {
    val text = rememberSaveable { mutableStateOf("") }
    TextField(
        label = { Text(text = "Comment") },
        value = if (addComment) "" else text.value,
        onValueChange = {
            onValue(it)
            text.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .border(
                width = 1.dp, color = colorResource(id = R.color.purple_200),
                shape = RoundedCornerShape(8.dp)
            )
    )
}