package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediconnect.R
import com.example.mediconnect.common.PopupComment
import com.example.mediconnect.common.PopupRename
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateScreen(navController: NavController) {
    val authViewModel: RateViewModel = hiltViewModel()
    val store = DataStoreManager(LocalContext.current)
    val userGson = store.getUser.collectAsState(initial = "")
    val user = Gson().fromJson(userGson.value, User::class.java)

    LaunchedEffect(user) {
        if (user != null) {
            authViewModel.getRate(user.id)
        }
    }

    /* if (user!=null) {
         authViewModel.getRate(user.id)
     }*/

    val rateRoom by authViewModel.listRateById.collectAsStateWithLifecycle()
    val comments by authViewModel.comments.collectAsStateWithLifecycle()
    val rates by authViewModel.rates.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val modalBottomSheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var showAddComment by remember { mutableStateOf(false) }
    var isCommentViewed by remember { mutableStateOf(false) }
    var rating by remember { mutableStateOf(Rate()) }

    if (showAddComment) {
        PopupComment(
            rates,
            onAction = { rate ->
                rating = rate
                Log.i("rateComment", "HistoryScreen: $rate")
                authViewModel.addComments(rate)
                //rates.comment?.clear()
                showAddComment = false
            }) {
            showAddComment = false
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
            ),
            title = {
                Text(
                    stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    if (isCommentViewed) isCommentViewed = false
                    else navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                /*IconButton(onClick = { *//* do something *//* }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }*/
            },
            scrollBehavior = scrollBehavior,
        )
    }) { padding ->
        if (isCommentViewed) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val txt = if (rates.like == 1) "Great" else "Bad"
                    Text(
                        text = "Your work today is doing $txt",
                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
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

                if (comments.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "There is no comment, please add one ",
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

                        Box(
                            contentAlignment= Alignment.Center,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(top = 10.dp)
                                .border(
                                    width = 2.dp,
                                    color = colorResource(id = R.color.purple_200),
                                    shape = CircleShape
                                ).clickable {
                                    showAddComment = true
                                },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = ""
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, top = 20.dp)
                    )
                    {
                        Text(
                            text = "Comments :",
                            modifier = Modifier.weight(0.8f),
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

                        Icon(
                            imageVector = Icons.Filled.Add,
                            modifier = Modifier
                                .weight(0.2f)
                                .clickable {
                                    showAddComment = true
                                    Log.i("ratesrfeafaea", "RateScreen: $rates")
                                },
                            contentDescription = ""
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .padding(vertical = 10.dp)
                    ) {
                        itemsIndexed(
                            items = comments,
                            key = { i, dr ->
                                ViewCompat.generateViewId()
                            }
                        ) { index, comment ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(id = R.color.purple_200),
                                        shape = RectangleShape
                                    )
                            ) {
                                Text(
                                    text = comment.comment ?: "",
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .padding(10.dp)
                                )

                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    modifier = Modifier
                                        .weight(0.2f)
                                        .padding(10.dp)
                                        .clickable {
                                            authViewModel.updateRate(comment)
                                        },
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (rateRoom.isNotEmpty()) {
                        LazyColumn {
                            itemsIndexed(
                                items = rateRoom,
                                key = { i, dr ->
                                    ViewCompat.generateViewId()
                                }
                            ) { count, rate ->

                                val viewCommentState = remember { mutableStateListOf(false) }
                                viewCommentState.clear()
                                rate.comment?.map {
                                    viewCommentState.add(false)
                                }

                                Log.i("viewCommentState", "HistoryScreen: $viewCommentState")

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .padding(top = 10.dp)
                                        .border(
                                            1.dp,
                                            SolidColor(colorResource(id = R.color.purple_200)),
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            colorResource(id = android.R.color.transparent),
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                ) {
                                    val txt = if (rate.like == 1) "Great" else "Bad"
                                    Text(
                                        text = "Your work today is doing $txt",
                                        modifier = Modifier
                                            .padding(20.dp)
                                    )

                                    //authViewModel.addComments(rate.comment?.toList())

                                    Column(modifier = Modifier.fillMaxHeight()) {
                                        Box(modifier = Modifier
                                            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
                                            .height(20.dp)
                                            .clickable {
                                                authViewModel.deleteRate(rate)
                                            }
                                            .border(
                                                width = 1.dp,
                                                color = colorResource(id = R.color.purple_200),
                                                shape = RoundedCornerShape(8.dp)
                                            )) {

                                            Text(
                                                text = "Delete",
                                                modifier = Modifier.padding(horizontal = 10.dp)
                                            )
                                        }

                                        Box(modifier = Modifier
                                            .padding(start = 20.dp, top = 5.dp, bottom = 20.dp)
                                            .height(20.dp)
                                            .clickable {
                                                authViewModel.changeRate(rate)
                                                isCommentViewed = !isCommentViewed
                                            }
                                            .border(
                                                width = 1.dp,
                                                color = colorResource(id = R.color.purple_200),
                                                shape = RoundedCornerShape(8.dp)
                                            )) {

                                            Text(
                                                text = "View Comments",
                                                modifier = Modifier.padding(horizontal = 10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "There is no rates",
                                modifier = Modifier.padding(top = 10.dp, start = 10.dp),
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
                    }
                }
            }
        }
    }
}
