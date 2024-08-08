package com.example.mediconnect.feature.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.mediconnect.common.horizontalcalendar.CalendarApp
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavController) {
    val authViewModel: AdminViewModel = hiltViewModel()
    val store = DataStoreManager(LocalContext.current)
    val userGson = store.getUser.collectAsState(initial = "")
    val user = Gson().fromJson(userGson.value, User::class.java)

    val listTopicsByDate by authViewModel.listTopicsByDate.collectAsStateWithLifecycle()
    val listTopicsRapport by authViewModel.listTopicsRapport.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
//TODO("change word AAAAAA with admin")
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
                /*IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }*/
            },
            actions = {
                /*IconButton(onClick = {
                    navController.navigate(Screen.RateScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }*/
            },
            scrollBehavior = scrollBehavior,
        )
    }, bottomBar = {
        //TODO("2 buttons same width")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, bottom = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(0.5f)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.purple_200))
                    .fillMaxWidth(0.4f)
                    .clickable {
                        //popup
                        //showSheet = true
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    text = "Print",
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(0.5f)
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
    }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CalendarApp(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(120.dp)
            ) {
                authViewModel.getListTopics(it)
            }

            if (listTopicsRapport.isEmpty()) {
                Text(
                    text = "There are no reports",
                    modifier = Modifier.fillMaxSize(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubikbold,
                                weight = FontWeight.Bold
                            )
                        )
                    ),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, top = 30.dp, bottom = 50.dp)
                ) {
                    itemsIndexed(
                        items = listTopicsRapport,
                        key = { _, _ ->
                            ViewCompat.generateViewId()
                        }
                    ) { _, item ->

                        Column(
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

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .padding(horizontal = 10.dp)
                                        .padding(vertical = 10.dp),
                                    text = item.title ?: "",
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

                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 10.dp),
                                    text = item.idUsers?.size.toString(),
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.rubik_regular,
                                                weight = FontWeight.Normal
                                            )
                                        )
                                    )
                                )

                                Icon(
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .padding(start = 5.dp),
                                    imageVector = Icons.Filled.SupervisedUserCircle,
                                    contentDescription = ""
                                )
                            }

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 10.dp),
                                text = item.workedUser ?: "",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.rubik_regular,
                                            weight = FontWeight.Normal
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
