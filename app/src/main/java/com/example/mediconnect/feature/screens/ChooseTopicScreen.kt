package com.example.mediconnect.feature.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.view.ViewCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediconnect.R
import com.example.mediconnect.common.CustomDialog
import com.example.mediconnect.common.CustomDropdownMenu
import com.example.mediconnect.common.timeline.TimeLine
import com.example.mediconnect.domain.entities.Topics
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager
import com.google.gson.Gson
import kotlinx.coroutines.flow.update
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseTopicScreen(navController: NavController) {
    val authViewModel: TopicsViewModel = hiltViewModel()
    val store = DataStoreManager(LocalContext.current)
    val userGson = store.getUser.collectAsState(initial = "")
    val user = Gson().fromJson(userGson.value, User::class.java)

    val listT = getListT(LocalContext.current)
    authViewModel.changeTopics(listT)

    val articles by authViewModel.sq.collectAsStateWithLifecycle()
    val timeOfTheDay by authViewModel.timeOfTheDay.collectAsStateWithLifecycle()
    val listTopicsTitle by authViewModel.listTopicsTitle.collectAsStateWithLifecycle()
    val hourStartState by authViewModel.hourStartState.collectAsStateWithLifecycle()
    val hourEndState by authViewModel.hourEndState.collectAsStateWithLifecycle()
    val durations by authViewModel.durations.collectAsStateWithLifecycle()
    val startState by authViewModel.startState.collectAsStateWithLifecycle()
    val endState by authViewModel.endState.collectAsStateWithLifecycle()
    val listTopicsById by authViewModel.listTopicsById.collectAsStateWithLifecycle()

    val isCheckedStep1 by authViewModel.isCheckedStep1.collectAsStateWithLifecycle()
    val isCheckedStep2 by authViewModel.isCheckedStep2.collectAsStateWithLifecycle()
    val isCheckedStep3 by authViewModel.isCheckedStep3.collectAsStateWithLifecycle()

    val step1 by authViewModel.step1.collectAsStateWithLifecycle()
    val step2 by authViewModel.step2.collectAsStateWithLifecycle()
    val step3 by authViewModel.step3.collectAsStateWithLifecycle()

    val dropDownState = remember { mutableStateOf("Select subject") }
    val searchHint = remember { mutableStateOf("") }
    val isLoader = remember { mutableStateOf(false) }
    val isPopupShowed = remember { mutableStateOf(false) }
    val listTopics = authViewModel.listTopics.collectAsStateWithLifecycle()


    /*val timeLineState = authViewModel.uiState.collectAsStateWithLifecycle()

    val step1 = timeLineState.value.step1 ?: false
    val step2 = timeLineState.value.step2 ?: false
    val step3 = timeLineState.value.step3 ?: false*/

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
                    if (step3) {
                        authViewModel.updateStep1(true)
                    } else if (step2) {
                        authViewModel.resetSteps()
                    } else {
                        navController.popBackStack()
                    }
                    Log.i("fzzzzz", "step3: $step3")
                    Log.i("fzzzzz", "step2: $step2")
                    Log.i("fzzzzz", "step1: $step1")
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
    },
        bottomBar = {
            if (step2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 10.dp)
                ) {

                    Box(
                        modifier = Modifier.fillMaxWidth(0.6f)
                            .padding(start = 10.dp)
                            .clip(RectangleShape)
                            .background(colorResource(id = R.color.purple_200))
                            .clickable {
                                authViewModel.clearDataBase()
                                authViewModel.resetSteps()
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            text = "Update the informations",
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
                                val listTop = mutableListOf<Topics>()
                                listTopics.value.mapIndexed { index, topics ->
                                    val sdf = SimpleDateFormat("dd/M/yyyy")
                                    val currentDate = sdf.format(Date())
                                    val topic = Topics(
                                        id = topics.id,
                                        title = topics.title,
                                        hourStart = hourStartState[index],
                                        hourEnd = hourEndState[index],
                                        nbHour = durations[index].toInt(),
                                        dateWork = currentDate,
                                        idUser = user.id
                                    )
                                    listTop.add(topic)
                                    authViewModel.saveTopics(topic)
                                    authViewModel.saveListTopics(topic)
                                }
                                authViewModel.updateStep2()
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            text = "Confirm",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }) { padding ->

        if (isPopupShowed.value) {
            CustomDialog(true, "you have successfully chosen the topics") {
                isPopupShowed.value = it
                navController.navigate(route = Screen.ChooseSubjectScreen.route)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Column(modifier = Modifier.background(Color.White)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimeLine("1", false, isCheckedStep1)
                TimeLine("2", false, isCheckedStep2)
                TimeLine("3", true, isCheckedStep3)
            }

            if (step1) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Choose your tasks",
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

                    OutlinedTextField(
                        value = searchHint.value,
                        onValueChange = {
                            searchHint.value = it
                            authViewModel.search(it)
                        },
                        //label = { Text(text = "Chercher votre sujet") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp)
                            .border(
                                1.dp,
                                SolidColor(colorResource(id = R.color.marron)),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .background(
                                colorResource(id = R.color.marron_light),
                                shape = RoundedCornerShape(5.dp)
                            )
                    )

                    val checkedStates = remember { mutableStateListOf<Boolean>() }
                    checkedStates.clear()
                    checkedStates.addAll(articles.map { it.isChecked ?: false })

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.5f)
                            .padding(top = 10.dp)
                            .background(Color.White, shape = RoundedCornerShape(5.dp))
                            .border(
                                1.dp,
                                SolidColor(colorResource(id = R.color.marron)),
                                shape = RoundedCornerShape(5.dp)
                            ),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        itemsIndexed(
                            items = articles,
                            key = { i, dr ->
                                articles[i].id ?: ViewCompat.generateViewId()
                            }
                        ) { index, item ->
                            val topic = articles[index]
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Checkbox(
                                    modifier = Modifier.size(24.dp),
                                    checked = checkedStates[index],
                                    onCheckedChange = {
                                        checkedStates[index] = it
                                        //topic.isChecked = it
                                        //authViewModel.saveTopicChoosed(articles[index])
                                        authViewModel.checkTopic(index)
                                    },
                                    enabled = true,
                                    colors = CheckboxDefaults.colors(colorResource(id = R.color.purple_200))
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = item.title ?: ""
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .clip(RectangleShape)
                            .background(colorResource(id = R.color.purple_200))
                            .fillMaxWidth(0.8f)
                            .clickable {
                                Log.i("listTopics", "ChooseTopicScreen: $listTopics")
                                authViewModel.updateStep1()
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            text = "Next",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (step2) {
                authViewModel.getTopicsTitle()

                val expandStates = remember { mutableStateListOf(false) }
                expandStates.clear()
                listTopicsTitle.map {
                    expandStates.add(false)
                }

                val clickHourStart = remember { mutableStateOf("0h") }
                val clickHourEnd = remember { mutableStateOf("0h") }
                val clickDuration = remember { mutableStateOf("0") }

                val toUpdate = remember { mutableStateOf(false) }
                val keepIndex = remember { mutableStateOf(0) }
                val showIndex = remember { mutableStateOf(0) }

                if (toUpdate.value) {
                    Dialog(onDismissRequest = { }) {
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
                                        text = "You worked on the topic ${listTopicsTitle[keepIndex.value]}",
                                        Modifier.padding(top = 15.dp),
                                        style = TextStyle(
                                            fontSize = 24.sp,
                                            fontFamily = FontFamily.Default,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )

                                    Row(
                                        modifier = Modifier.padding(top = 15.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Duration : ")

                                        TextField(modifier = Modifier
                                            .padding(8.dp)
                                            .border(
                                                border = BorderStroke(
                                                    1.dp,
                                                    colorResource(id = R.color.purple_200)
                                                ),
                                                shape = RoundedCornerShape(4.dp)
                                            ), value = clickDuration.value, onValueChange = {
                                            clickDuration.value = it
                                        })

                                        /*CustomDropdownMenu(
                                            list = timeOfTheDay,
                                            defaultSelected = clickHourStart.value,
                                            color = colorResource(id = R.color.purple_200),
                                            modifier = Modifier.padding(start = 10.dp),
                                            onSelected = {
                                                clickHourStart.value = timeOfTheDay[it]
                                                authViewModel.changeHourStart(timeOfTheDay[it], keepIndex.value)
                                            }
                                        )*/

                                        Text(
                                            text = "Hours",
                                            modifier = Modifier.padding(start = 20.dp)
                                        )
                                        /*CustomDropdownMenu(
                                            list = timeOfTheDay,
                                            defaultSelected = clickHourEnd.value,
                                            color = colorResource(id = R.color.purple_200),
                                            modifier = Modifier.padding(start = 10.dp),
                                            onSelected = {
                                                clickHourEnd.value = timeOfTheDay[it]
                                                authViewModel.changeHourEnd(timeOfTheDay[it], keepIndex.value)
                                            }
                                        )*/

                                    }

                                    Box(modifier = Modifier.padding(40.dp, 10.dp, 40.dp, 0.dp)) {
                                        Button(
                                            onClick = {
                                                authViewModel.changeDuration(
                                                    clickDuration.value,
                                                    keepIndex.value
                                                )
                                                toUpdate.value = false
                                                Log.i(
                                                    "hourss",
                                                    "ChooseTopicScreen: ${keepIndex.value}"
                                                )
                                            },
                                            shape = RoundedCornerShape(50.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = colorResource(
                                                    id = R.color.purple_200
                                                )
                                            ),
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 50.dp)
                ) {
                    itemsIndexed(
                        items = listTopicsTitle,
                        key = { i, dr ->
                            ViewCompat.generateViewId()
                        }
                    ) { index, item ->
                        val topic = listTopicsTitle[index]
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showIndex.value = index

                                        expandStates[index] = !expandStates[index]
                                    }
                            ) {
                                val sd =
                                    if (expandStates[index]) Icons.Filled.Check else Icons.Default.Downloading
                                val arrow =
                                    if (expandStates[index]) painterResource(id = R.drawable.baseline_keyboard_arrow_up_24) else painterResource(
                                        id = R.drawable.baseline_keyboard_arrow_down_24
                                    )

                                /*Icon(
                                    modifier = Modifier.weight(0.1f),
                                    imageVector = sd,
                                    contentDescription = ""
                                )*/

                                Text(
                                    text = item,
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .padding(start = 10.dp),
                                    color = colorResource(id = R.color.purple_200),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Icon(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .padding(start = 10.dp),
                                    painter = arrow,
                                    contentDescription = ""
                                )
                            }

                            Log.i("countDuration", "ChooseTopicScreen: $index")

                            if (expandStates[index]) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            border = BorderStroke(
                                                1.dp,
                                                colorResource(id = R.color.purple_200)
                                            ),
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                ) {
                                    Text(
                                        modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                                        text = "You worked in this subject :"
                                    )

                                    Row(
                                        modifier = Modifier.padding(top = 20.dp, start = 20.dp)
                                    ) {

                                        val countStart =
                                            if (index == showIndex.value) hourStartState[showIndex.value]
                                            else hourStartState[index]

                                        val countEnd =
                                            if (index == showIndex.value) hourEndState[showIndex.value]
                                            else hourEndState[index]

                                        val countDuration =
                                            if (index == showIndex.value) durations[showIndex.value]
                                            else durations[index]

                                        Text(text = "Duration : $countDuration heurs")
                                        /*Text(
                                            text = "To :  $countEnd",
                                            modifier = Modifier.padding(start = 20.dp)
                                        )*/
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_settings_24),
                                            contentDescription = "",
                                            modifier = Modifier
                                                .padding(start = 20.dp)
                                                .clickable {
                                                    toUpdate.value = true
                                                    keepIndex.value = index
                                                    authViewModel.keepIndexed.update {
                                                        index
                                                    }
                                                })
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))
                                }
                            }
                        }
                    }
                }
            } else {
                authViewModel.getListTopics(user.id)
                Log.i("listTopicsById", "${user.id}  ChooseTopicScreen: $listTopicsById")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Confirm your choices",
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
                        text = "You will work on these subjects: ",
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

                    /*if (isLoader.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(size = 100.dp),
                            color = Color.Blue,
                        )
                        //delay(2000)
                        LaunchedEffect(isLoader.value) {
                            delay(2000)
                            isLoader.value = false
                        }
                    }*/
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                )
                {
                    val allSubject = authViewModel.getTopicsDetail(listTopicsById)
                    Log.i("allSubject", "ChooseTopicScreen: $allSubject")
                    allSubject.map {
                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            Text(
                                text = it,
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
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .clip(RectangleShape)
                            .background(colorResource(id = R.color.purple_200))
                            .fillMaxWidth()
                            .clickable {
                                authViewModel.updateStep3()
                                isLoader.value = true
                                //put the informations into shared or datastore
                                isPopupShowed.value = true
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            text = "Confirm",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .clip(RectangleShape)
                            .background(colorResource(id = R.color.purple_200))
                            .fillMaxWidth()
                            .clickable {
                                authViewModel.clearDataBase()
                                authViewModel.resetSteps()
                            }
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 15.dp),
                            text = "Update the informations",
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

fun getListT(current: Context): List<Topics> {
    val listId = mutableListOf<Topics>()

    val s = loadBookJSONFromAsset(current)
    val workbook = WorkbookFactory.create(s)
    val workSheet = workbook.getSheetAt(0)
    for (myRow in workSheet) {
        val topics = myRow.getCell(0).stringCellValue
        if (topics.isNotEmpty()) {
            val user = Topics(
                title = topics,
                id = ViewCompat.generateViewId()
            )
            listId.add(user)
        }
    }
    listId.removeFirst()
    return listId
}