package com.example.mediconnect.common

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
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
fun PopupComment(
    rates: Rate,
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
                            text = "Add Comments",
                            modifier = Modifier.padding(top = 10.dp),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubikbold,
                                        weight = FontWeight.Bold
                                    )
                                )
                            )
                        )

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
                                Log.i("rateComment", "old: $rates")
                                if (comment.isNotEmpty()) {
                                    comments.add(comment)
                                    rates.comment?.addAll(comments)
                                    Log.i("rateComment", "comments: ${comments.toList()}")
                                }

                                onAction(rates)
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