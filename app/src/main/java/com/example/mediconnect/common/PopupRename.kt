package com.example.mediconnect.common

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.ViewCompat
import com.example.mediconnect.R
import com.example.mediconnect.domain.entities.Rate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupRename(
    idTopic: Int,
    onAction: (name: Rate) -> Unit,
    onDismiss: () -> Unit
) {

    var isLike by remember { mutableStateOf(false) }
    var isDisLike by remember { mutableStateOf(false) }
    var addComment by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }
    var comments = remember { mutableStateListOf<String>() }
    var like by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        content = {
            Surface(shape = MaterialTheme.shapes.medium) {
                Column {
                    Column(Modifier.padding(24.dp)) {

                        Text(
                            text = "How is your work on this Task",
                            modifier = Modifier.padding(top = 10.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubikbold,
                                        weight = FontWeight.Bold
                                    )
                                )
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 30.dp, bottom = 10.dp)
                        ) {

                            val color =
                                if (isLike) colorResource(id = R.color.purple_200) else colorResource(
                                    id = android.R.color.transparent
                                )
                            val colorDis =
                                if (isDisLike) colorResource(id = R.color.purple_200) else colorResource(
                                    id = android.R.color.transparent
                                )

                            Button(
                                onClick = {
                                    isLike = true
                                    isDisLike = false
                                    like = 1
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = color),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize()
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.purple_200),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                val c =
                                    if (isLike) colorResource(id = R.color.white) else colorResource(
                                        id = R.color.purple_200
                                    )
                                Text(text = "Great", color = c)
                            }

                            Button(
                                onClick = {
                                    isLike = false
                                    isDisLike = true
                                    like = 2
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = colorDis),
                                modifier = Modifier
                                    .padding(start = 20.dp)
                                    .wrapContentSize()
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.purple_200),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                            ) {
                                val c =
                                    if (isDisLike) colorResource(id = R.color.white) else colorResource(
                                        id = R.color.purple_200
                                    )

                                Text(text = "Bad", color = c)
                            }
                        }

                        Spacer(Modifier.size(16.dp))
                        TextArea(addComment) {
                            comment = it
                            addComment = false
                        }
                    }


                    Spacer(Modifier.size(4.dp))
                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        Arrangement.spacedBy(8.dp, Alignment.End),
                    ) {
                        TextButton(
                            onClick = {
                                onDismiss()
                            },
                            content = { Text("CANCEL") },
                        )
                        TextButton(
                            onClick = {
                                Log.i("rateComment", "comments: $comments")
                                if (comment.isNotEmpty()) {
                                    comments.add(comment)
                                    Log.i("rateComment", "comments: $comment")
                                    Log.i("rateComment", "comments: ${comments.toList()}")
                                }

                                val r = Rate(ViewCompat.generateViewId(), comments, like = like, "2019-A-0044")
                                onAction(r)
                            },
                            content = { Text("OK") }
                        )

                        TextButton(
                            onClick = {
                                addComment = true
                                comments.add(comment)

                            },
                            content = { Text("ADD COMMENT") },
                        )
                    }
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}