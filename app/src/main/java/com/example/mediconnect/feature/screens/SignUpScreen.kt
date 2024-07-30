package com.example.mediconnect.feature.screens

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.mediconnect.R
import com.example.mediconnect.domain.entities.Role
import com.example.mediconnect.feature.AuthViewModel
import com.example.mediconnect.feature.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val firstnameState by authViewModel.firstnameState.collectAsState()
    val lastnameState by authViewModel.lastnameState.collectAsState()
    val emailState by authViewModel.emailState.collectAsState()
    val passwordState by authViewModel.passwordState.collectAsState()
    val confirmPasswordState by authViewModel.confirmPasswordState.collectAsState()
    var selectedRole by remember { mutableStateOf<Role?>(null) }
    var showPassword by remember {
        mutableStateOf(false)
    }
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
                .fillMaxWidth()
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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color(0xFF275FCC)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,

                    )
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF275FCC),
                        cursorColor = Color(0xFF275FCC),
                        focusedLabelColor = Color(0xFF275FCC),
                        focusedLeadingIconColor = Color(0xFF275FCC)
                    ),
                    value = firstnameState.text,
                    onValueChange = { authViewModel.setFirstname(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    leadingIcon = {
                        Icons.Default.Person?.let {
                            Icon(it, contentDescription = null)
                        }
                    },
                    label = { Text("FirstName") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF275FCC),
                        cursorColor = Color(0xFF275FCC),
                        focusedLabelColor = Color(0xFF275FCC),
                        focusedLeadingIconColor = Color(0xFF275FCC)
                    ),
                    value = lastnameState.text,
                    onValueChange = { authViewModel.setlastname(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),

                    leadingIcon = {
                        Icons.Default.Person?.let {
                            Icon(it, contentDescription = null)
                        }
                    },
                    label = { Text("LastName") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))


                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF275FCC),
                        cursorColor = Color(0xFF275FCC),
                        focusedLabelColor = Color(0xFF275FCC),
                        focusedLeadingIconColor = Color(0xFF275FCC)
                    ),
                    value = emailState.text,
                    onValueChange = {authViewModel.setEmail(it)},
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
                        keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))


                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF275FCC),
                        cursorColor = Color(0xFF275FCC),
                        focusedLabelColor = Color(0xFF275FCC),
                        focusedLeadingIconColor = Color(0xFF275FCC)
                    ),
                    value = passwordState.text,
                    onValueChange = { authViewModel.setPassword(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    leadingIcon = {
                        Icons.Default.Lock?.let {
                            Icon(it, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Image(
                                painter = painterResource(id = if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                                contentDescription = if (showPassword) "Show Password" else "Hide Password"
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF275FCC),
                        cursorColor = Color(0xFF275FCC),
                        focusedLabelColor = Color(0xFF275FCC),
                        focusedLeadingIconColor = Color(0xFF275FCC)
                    ),
                    value = confirmPasswordState.text,
                    onValueChange = { authViewModel.setConfirmPassword(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    leadingIcon = {
                        Icons.Default.Lock?.let {
                            Icon(it, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Image(
                                painter = painterResource(id = if (showPassword) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                                contentDescription = if (showPassword) "Show Password" else "Hide Password"
                            )
                        }
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    label = { Text("Confirm Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF275FCC)),
                        selected = selectedRole == Role.Patient,
                        onClick = { selectedRole = Role.Patient }
                    )
                    Text("Patient",  modifier = Modifier.padding(start = 4.dp).align(Alignment.CenterVertically))

                    RadioButton(
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF275FCC)),
                        selected = selectedRole == Role.Doctor,
                        onClick = { selectedRole = Role.Doctor }
                    )
                    Text("Doctor", modifier = Modifier.padding(start = 4.dp).align(Alignment.CenterVertically))
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        selectedRole?.let {
                            authViewModel.register(
                                firstname=authViewModel.firstnameState.value.text,
                                lastname=authViewModel.lastnameState.value.text,
                                email=authViewModel.emailState.value.text,
                                password = authViewModel.passwordState.value.text,
                                role = it.name

                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF275FCC)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Sign Up")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    ClickableText(
                        text = AnnotatedString("If you have an account click here"),
                        modifier = Modifier,
                        onClick = {
                            navController.navigate(route = Screen.SignInScreen.route)
                        }

                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Or",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Google Icon
                    Image(
                        painter = painterResource(id = R.drawable.google_icons_09_512),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )

                    // Facebook Icon
                    Image(
                        painter = painterResource(id = R.drawable._24010),
                        contentDescription = "Facebook Icon",
                        modifier = Modifier.size(40.dp)  // Adjust size as needed
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

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

                        }

                    )
                }


            }

        }
    }}



