package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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
import com.example.mediconnect.common.PopupRename
import com.example.mediconnect.domain.entities.Rate
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    val authViewModel: HistoryViewModel = hiltViewModel()
    val store = DataStoreManager(LocalContext.current)
    val userGson = store.getUser.collectAsState(initial = "")
    val user = Gson().fromJson(userGson.value, User::class.java)

    val listTopicsById by authViewModel.listTopicsById.collectAsStateWithLifecycle()
    val listNameTopics by authViewModel.listNameTopics.collectAsStateWithLifecycle()
    val allRate by authViewModel.allRate.collectAsStateWithLifecycle()
    val comments by authViewModel.comments.collectAsStateWithLifecycle()
    val rateRoom by authViewModel.listRateById.collectAsStateWithLifecycle()

    authViewModel.getListTopics("2019-A-0044")

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val modalBottomSheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var isCommentViewed by remember { mutableStateOf(false) }
    var keepIndex by remember { mutableStateOf(0) }
    var rating by remember { mutableStateOf(Rate()) }
    var rates by remember { mutableStateOf(Rate()) }

    val expandStates = remember { mutableStateListOf(false) }
    expandStates.clear()
    listNameTopics.map {
        expandStates.add(false)
    }

    if (showSheet) {
        PopupRename(idTopic = 0,
            onAction = { rate ->
                rating = rate
                Log.i("rateComment", "HistoryScreen: ${rate.comment?.toList()}")
                authViewModel.addRate(rating)
                showSheet = false
            }) {
            showSheet = false
        }
    }

    /*BackHandler {
        navController.popBackStack()
    }*/

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
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(Screen.RateScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
    }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, bottom = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.purple_200))
                    .fillMaxWidth(0.4f)
                    .clickable {
                        //popup
                        showSheet = true
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    text = "Comment",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.purple_200))
                    .clickable {
                        navController.navigate(Screen.ChooseSubjectScreen.route)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    text = "Logout",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }) { padding ->
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
                Text(
                    text = "You worked on these subjects :",
                    modifier = Modifier.padding(top = 10.dp),
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

                val searchHistory by authViewModel.searchHistory.collectAsStateWithLifecycle()
                val forToday by authViewModel.forToday.collectAsStateWithLifecycle()

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    value = searchHistory,
                    onValueChange = { authViewModel.changeSearchHistory(it) },
                    label = { Text("Search") },
                )

                val text = if (forToday) "All Subject" else "Subject for today"
                Text(text = text, modifier = Modifier.clickable {
                    authViewModel.changeForToday(!forToday)
                })

                authViewModel.getTopicsDetail(listTopicsById, searchHistory)

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, top = 30.dp, bottom = 50.dp)
                ) {
                    itemsIndexed(
                        items = listNameTopics,
                        key = { i, dr ->
                            ViewCompat.generateViewId()
                        }
                    ) { index, item ->

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

                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                                    .padding(vertical = 10.dp),
                                text = item,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.rubik_medium,
                                            weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }


                        /*Row(modifier = Modifier.fillMaxWidth()) {

                            Text(
                                text = item,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.rubik_medium,
                                            weight = FontWeight.Medium
                                        )
                                    )
                                )
                            )
                        }*/
                    }
                }
            }
        }
    }
}
