package com.example.mediconnect.feature.screens
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mediconnect.R
import com.example.mediconnect.domain.entities.AuthResult
import com.example.mediconnect.feature.AuthViewModel
import com.example.mediconnect.feature.navigation.Screen
import com.example.mediconnect.util.Resource
import kotlin.math.log

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var showPassword by remember {
        mutableStateOf(false)
    }
    val emailState by authViewModel.emailState.collectAsState()
    val passwordState by authViewModel.passwordState.collectAsState()
    val loading by authViewModel.loading.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Image(
            painter = painterResource(id = R.drawable.backgroundimage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)

        )
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.Center)
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    //Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Sign In",
                        style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF275FCC)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 40.dp, top = 10.dp)
                            ,
                        textAlign = TextAlign.Center,

                    )

                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF275FCC),
                            cursorColor = Color(0xFF275FCC),
                            focusedLabelColor =  Color(0xFF275FCC),
                            focusedLeadingIconColor = Color(0xFF275FCC)),
                        value = emailState.text,
                        onValueChange = {authViewModel.setEmail(it)
                            emailError = if (it.isBlank() || !authViewModel.isValidEmail(it)) {
                                "Invalid Email Format"
                            } else {
                                null
                            }},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        leadingIcon = {
                            Icons.Default.Email?.let {
                                Icon(it, contentDescription = null)
                            }
                        },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,

                        )

                    )
                    emailError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                        )
                    }


                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF275FCC),
                            cursorColor = Color(0xFF275FCC),
                            focusedLabelColor = Color(0xFF275FCC),
                            focusedLeadingIconColor = Color(0xFF275FCC)
                        ),
                        value = passwordState.text,
                        onValueChange = {
                            authViewModel.setPassword(it)
                            passwordError = if (it.isBlank() || !authViewModel.isValidPassword(it)) {
                                "Invalid Password Format"
                            } else {
                                null
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        leadingIcon = {
                            Icons.Filled.Lock?.let {
                                Icon(it, contentDescription = null)
                            }
                        },
                        trailingIcon = {
                            val visibilityIcon = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = visibilityIcon,
                                    contentDescription = if (showPassword) "Hide Password" else "Show Password"
                                )
                            }
                        },
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Go,
                        )
                    )



                    passwordError?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth(),
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = {
                                val result = authViewModel.authenticate(
                                    email = authViewModel.emailState.value.text,
                                    password = authViewModel.passwordState.value.text
                                )

                            Log.d("aminee", "//////${result}")



                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF275FCC)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (loading) {

                            CircularProgressIndicator(color = Color.White)
                        } else {
                            Text("Sign In")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),

                        contentAlignment = Alignment.Center
                    ) {
                        ClickableText(
                            text = AnnotatedString("If you don't have an account click here"),
                            modifier = Modifier,
                            onClick = {
                                navController.navigate(route = Screen.SignUpScreen.route)

                            },
                            style = TextStyle(color = Color.Blue,
                            textDecoration = TextDecoration.Underline)


                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Or",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                        ,
                        textAlign = TextAlign.Center,
                        )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.google_icons_09_512),
                            contentDescription = "Google Icon",
                            modifier = Modifier.size(40.dp)  // Adjust size as needed
                        )


                        Image(
                            painter = painterResource(id = R.drawable._24010),
                            contentDescription = "Facebook Icon",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))

                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        ClickableText(
                            text = AnnotatedString("Forget Password ?"),
                            modifier = Modifier,
                            onClick = {

                            },
                            style = TextStyle(color = Color.Blue,
                                textDecoration = TextDecoration.Underline)

                        )
                    }

                }

            }
        }
    }


