package com.example.mediconnect.feature.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mediconnect.R
import com.example.mediconnect.feature.HomeViewModel
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val authViewModel: HomeViewModel = hiltViewModel()

    val store = DataStoreManager(LocalContext.current)
    val clock = store.getClock.collectAsState(initial = "")

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Log.i("clock", "HomeScreen: ${authViewModel.getClock()}")

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
                /*IconButton(onClick = { *//* do something *//* }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }*/
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Welcome",
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

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 15.dp),
                text = "You clocked ${clock.value} at this time ${authViewModel.getDate()}",
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

            Box(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.purple_200))
                    .fillMaxWidth(0.8f)
                    .clickable {
                        if (clock.value == "Out") {
                            navController.navigate(Screen.HistoryScreen.route)
                        } else {
                            authViewModel.clearDataBase()
                            navController.navigate(Screen.ChooseTopicScreen.route)
                        }
                    }
            ) {
                val info = if (clock.value == "Out") "View Work" else "Select topic"
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    text = info,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 25.dp)
                    .clip(RectangleShape)
                    .background(colorResource(id = R.color.purple_200))
                    .fillMaxWidth(0.8f)
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
}