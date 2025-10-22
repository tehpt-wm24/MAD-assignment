package com.example.splashmaniaapp.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.data.AuthManager
import com.example.splashmaniaapp.ui.screen.home.HomeViewModel

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val linkColor = Color(0xFF0038D0)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    onLoginSuccess: () -> Unit = {},
    onForgotPasswordClicked: () -> Unit = {},
    onSignUpClicked: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    // Validation states
    val isEmailValid = remember { mutableStateOf(true) }
    val isFormValid = remember { mutableStateOf(true) }

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            homeViewModel.loadUserProfile()
            onLoginSuccess()
        }
    }

    Scaffold(
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )

            Text(
                text = "Log In",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            if (!isFormValid.value) {
                Text(
                    text = "Please fill in all fields correctly",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (uiState.isError) {
                Text(
                    text = uiState.errorMessage ?: "Login failed",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            OutlinedTextField(
                value = uiState.email,
                onValueChange = {
                    viewModel.updateEmail(it)
                    isEmailValid.value = viewModel.isValidEmail(it)
                    isFormValid.value = true
                },
                label = { Text("Email") },
                isError = !isEmailValid.value,
                supportingText = {
                    if (!isEmailValid.value) {
                        Text("Must be a valid email address (e.g., example@gmail.com)", color = Color.Red)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isEmailValid.value) Color.Gray else Color.Red,
                    unfocusedBorderColor = if (isEmailValid.value) Color.LightGray else Color.Red,
                    errorBorderColor = Color.Red
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 10.dp)
            )

            TextButton(
                onClick = onForgotPasswordClicked,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Forgot Password?",
                    textDecoration = TextDecoration.Underline
                )
            }

            Button(
                onClick = {
                    val emailValid = viewModel.isValidEmail(uiState.email)
                    isEmailValid.value = emailValid

                    if (emailValid && uiState.email.isNotEmpty() && uiState.password.isNotEmpty()) {
                        viewModel.login()
                    } else {
                        isFormValid.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 5.dp)
            ) {
                Text(
                    text = "LOGIN",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Black
                )
                TextButton(
                    onClick = onSignUpClicked,
                    modifier = Modifier.padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        color = linkColor,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: LoginViewModel,
    homeViewModel: HomeViewModel = viewModel(),
    onSignUpButtonClicked: (email: String, password: String, username: String) -> Unit = {_, _, _ ->},
    onSignInClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    // Validation states
    val isEmailValid = remember { mutableStateOf(true) }
    val isPasswordMatch = remember { mutableStateOf(true) }
    val isFormValid = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                    Text("Back", color = Color.Black)
                }
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            if(!isFormValid.value) {
                Text(
                    text = "Please fill in all fields correctly",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    isEmailValid.value = viewModel.isValidEmail(it)
                    isFormValid.value = true
                },
                label = { Text("Email") },
                isError = !isEmailValid.value,
                supportingText = {
                    if(!isEmailValid.value) {
                        Text(text = "Must be a valid email address (e.g., example@gmail.com)", color = Color.Red)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isEmailValid.value) Color.Gray else Color.Red,
                    unfocusedBorderColor = if (isEmailValid.value) Color.LightGray else Color.Red,
                    errorBorderColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    password.value = it
                    isPasswordMatch.value = it == confirmPassword.value
                    isFormValid.value = true
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 12.dp)
            )

            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = {
                    confirmPassword.value = it
                    isPasswordMatch.value = it == password.value
                    isFormValid.value = true
                 },
                label = { Text("Confirm Password") },
                isError = !isPasswordMatch.value,
                supportingText = {
                    if(!isPasswordMatch.value) {
                        Text("Passwords do not match.")
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isPasswordMatch.value) Color.Gray else Color.Red,
                    unfocusedBorderColor = if (isPasswordMatch.value) Color.LightGray else Color.Red,
                    errorBorderColor = Color.Red
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 12.dp)
            )

            Button(
                onClick = {
                    val emailValid = viewModel.isValidEmail(email.value)
                    val passwordsMatch = password.value == confirmPassword.value

                    isEmailValid.value = emailValid
                    isPasswordMatch.value = passwordsMatch

                    if(emailValid && passwordsMatch &&
                       email.value.isNotEmpty() &&
                       username.value.isNotEmpty() &&
                       password.value.isNotEmpty()) {
                        AuthManager.signUp(email.value, password.value, username.value)
                        homeViewModel.loadUserProfile()
                        onSignUpButtonClicked(email.value, password.value, username.value)
                    } else {
                        isFormValid.value = false
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 5.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 40.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color.Black
                )
                TextButton(
                    onClick = onSignInClicked,
                    modifier = Modifier.padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)
                ) {
                    Text(
                        text = "Sign In",
                        color = linkColor,
                        modifier = Modifier.padding(0.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    viewModel: LoginViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    onCancelClicked: () -> Unit = {},
    onUpdateClicked: (newPassword: String) -> Unit = {},
    onBackClicked: () -> Unit = {}
) {
    val email = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val confirmNewPassword = remember { mutableStateOf("") }

    // Validation states
    val isEmailValid = remember { mutableStateOf(true) }
    val isPasswordMatch = remember { mutableStateOf(true) }
    val isFormValid = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                TextButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                    Text("Back", color = Color.Black)
                }
            }
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )

            Text(
                text = "Forgot Password",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 30.dp)
            )

            if (!isFormValid.value) {
                Text(
                    text = "Please fill in all fields correctly",
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                    isEmailValid.value = viewModel.isValidEmail(it)
                    isFormValid.value = true
                },
                label = { Text("Email") },
                isError = !isEmailValid.value,
                supportingText = {
                    if (!isEmailValid.value) {
                        Text("Must be a valid email address (e.g., example@gmail.com)")
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isEmailValid.value) Color.Gray else Color.Red,
                    unfocusedBorderColor = if (isEmailValid.value) Color.LightGray else Color.Red,
                    errorBorderColor = Color.Red
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = newPassword.value,
                onValueChange = {
                    newPassword.value = it
                    isPasswordMatch.value = it == confirmNewPassword.value
                    isFormValid.value = true
                },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 20.dp)
            )

            OutlinedTextField(
                value = confirmNewPassword.value,
                onValueChange = {
                    confirmNewPassword.value = it
                    isPasswordMatch.value = it == newPassword.value
                    isFormValid.value = true
                },
                label = { Text("Confirm New Password") },
                isError = !isPasswordMatch.value,
                supportingText = {
                    if (!isPasswordMatch.value) {
                        Text("Passwords do not match")
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isPasswordMatch.value) Color.Gray else Color.Red,
                    unfocusedBorderColor = if (isPasswordMatch.value) Color.LightGray else Color.Red,
                    errorBorderColor = Color.Red
                ),
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 40.dp)
            )

            // Buttons row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = onCancelClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                ) {
                    Text(
                        text = "CANCEL",
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = {
                        val emailValid = viewModel.isValidEmail(email.value)
                        val passwordsMatch = newPassword.value == confirmNewPassword.value

                        isEmailValid.value = emailValid
                        isPasswordMatch.value = passwordsMatch

                        if (emailValid && passwordsMatch &&
                            email.value.isNotEmpty() &&
                            newPassword.value.isNotEmpty()
                        ) {
                            val success = AuthManager.resetPassword(email.value)
                            if (success) {
                                homeViewModel.loadUserProfile()
                                onUpdateClicked(newPassword.value)
                            } else {
                                isFormValid.value = false
                            }
                        } else {
                            isFormValid.value = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = "UPDATE",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}