package com.example.mediconnect.feature.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import android.view.Gravity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.mediconnect.R
import com.example.mediconnect.common.CustomDialog
import com.example.mediconnect.common.CustomDialogWithContent
import com.example.mediconnect.common.QrCodeAnalyzer
import com.example.mediconnect.common.SharedPreference
import com.example.mediconnect.domain.entities.User
import com.example.mediconnect.feature.AuthViewModel
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.local.datastore.DataStoreManager
import com.google.gson.Gson
import com.thecode.aestheticdialogs.AestheticDialog
import com.thecode.aestheticdialogs.DialogAnimation
import com.thecode.aestheticdialogs.DialogStyle
import com.thecode.aestheticdialogs.DialogType
import com.thecode.aestheticdialogs.OnDialogClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseSubjectScreen(navController: NavController) {
    val authViewModel: ScanViewModel = hiltViewModel()

    val store = DataStoreManager(LocalContext.current)

    val listId = getListId(LocalContext.current)

    val detail by authViewModel.state.collectAsStateWithLifecycle()
    val userId by authViewModel.userId.collectAsStateWithLifecycle()
    val user by authViewModel.users.collectAsStateWithLifecycle()

    authViewModel.getAllUser()

    Log.i("detailCode", "ChooseSubjectScreen: $detail")

    var toScann by remember { mutableStateOf(false) }

    var code by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    BackHandler {
        //context.getActivity()?.finish()
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
        authViewModel.getUserById(userId)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val showDialog = remember { mutableStateOf(false) }
            if (showDialog.value) {
                CustomDialog(false, "This user id is incorrect") {
                    showDialog.value = it
                }
            }

            val showContentDialog = remember { mutableStateOf(false) }
            if (showContentDialog.value) {
                CustomDialogWithContent { show, clock ->
                    showContentDialog.value = show
                    // save in shared the in out clock
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveClock(clock)
                    }
                    navController.navigate(route = Screen.HomeScreen.route) {
                        popUpTo(route = Screen.ChooseSubjectScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }

            if (!toScann) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "Put your ID number to enter",
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    value = userId,
                    onValueChange = { authViewModel.changeUserId(it) },
                    label = { Text("ID") },
                )

                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .clip(RectangleShape)
                        .background(colorResource(id = R.color.purple_200))
                        .fillMaxWidth(0.8f)
                        .clickable {

                            if (user.isNotEmpty()) {
                                val u = Gson().toJson(user[0])
                                runBlocking {
                                    store.saveUser(u)
                                }
                                if (user[0].role == "admin") {
                                    navController.navigate(route = Screen.AdminScreen.route) {
                                        popUpTo(route = Screen.ChooseSubjectScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                } else showContentDialog.value = true
                            } else showDialog.value = true
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        text = "Submit",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(Color.Gray)
                    ) {}

                    Text(
                        text = "OR",
                        modifier = Modifier.weight(0.5f),
                        color = Color.Gray,
                        style = TextStyle(
                            textAlign = TextAlign.Center
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .weight(1f)
                            .background(Color.Gray)
                    ) {}
                }

                Box(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .clip(RectangleShape)
                        .background(colorResource(id = R.color.purple_200))
                        .fillMaxWidth(0.8f)
                        .clickable {
                            //toScann = true
                            authViewModel.startScanning()
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp),
                        text = "Scann with Qr code",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                /*Button(modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(top = 15.dp)
                    .clip(RectangleShape)
                    .background(color = Color.Blue),
                    onClick = {
                        Log.i("testID", "ChooseSubjectScreen: ${userId in listId}")
                    }) {
                    Text(text = "Submit")
                }*/
            } else {
                if (hasCameraPermission) {
                    AndroidView(
                        factory = { context ->
                            val previewView = PreviewView(context)
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(Size(previewView.width, previewView.height))
                                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                                .build()

                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                QrCodeAnalyzer { result ->
                                    Log.i("resultds", "ChooseSubjectScreen: $result")
                                    code = result
                                }
                            )
                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        },
                        modifier = Modifier.weight(1f)
                    )

                    Log.i("hahicode", "ChooseSubjectScreen: $code")

                    Text(
                        text = code,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    )
                }
            }
        }

    }
}

fun getUserId(listId: List<User>): List<String> {
    return listId.map {
        it.id
    }
}

fun getListId(current: Context): List<User> {

    val userAdmin = User(id = "AAAAAA", username = "Ala", role = "admin")

    val listId = mutableListOf<User>()

    val s = loadRHJSONFromAsset(current)
    val workbook = WorkbookFactory.create(s)
    val workSheet = workbook.getSheetAt(0)
    for (myRow in workSheet) {

        val username = myRow.getCell(0).stringCellValue
        val id = myRow.getCell(1).stringCellValue
        if (id.isNotEmpty()) {
            val user = User(
                username = username,
                role = "simple",
                id = id
            )
            listId.add(user)
        }
    }
    listId.add(userAdmin)
    listId.removeFirst()
    return listId
}

fun getList(current: Context): List<String> {
    val list = mutableListOf<User>()
    val listId = mutableListOf<String>()

    val s = loadJSONFromAsset(current)
    val workbook = WorkbookFactory.create(s)
    val workSheet = workbook.getSheetAt(0)
    val q = workSheet.getRow(0).getCell(1).stringCellValue
    Log.i("", "ChooseSubjectScreen: $q")
    for (myRow in workSheet) {
        for (myCell in myRow) {
            //set foreground color here
            Log.i("myCell", "ChooseSubjectScreen: $myCell")
        }
        val username = myRow.getCell(0).stringCellValue
        val email = myRow.getCell(1).stringCellValue
        val url = myRow.getCell(2).stringCellValue
        val user = User(username = username, id = email)
        list.add(user)
        listId.add(username)
    }
    return listId
}

fun loadJSONFromAsset(current: Context): InputStream? {
    return try {
        val inputStream: InputStream = current.assets.open("story.xls")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        //inputStream.read(buffer)
        inputStream
        /*inputStream.close()
        String(buffer, Charset.forName("UTF-8"))*/
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
}

fun loadRHJSONFromAsset(current: Context): InputStream? {
    return try {
        val inputStream: InputStream = current.assets.open("rh.xls")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        //inputStream.read(buffer)
        inputStream
        /*inputStream.close()
        String(buffer, Charset.forName("UTF-8"))*/
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
}

fun loadBookJSONFromAsset(current: Context): InputStream? {
    return try {
        val inputStream: InputStream = current.assets.open("Book1.xls")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        //inputStream.read(buffer)
        inputStream
        /*inputStream.close()
        String(buffer, Charset.forName("UTF-8"))*/
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
}