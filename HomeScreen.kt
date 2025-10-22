package com.example.splashmaniaapp.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.ui.screen.mytrips.BottomNavBarWithIcons
import kotlinx.coroutines.launch

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val backgroundAColor = Color(0xFFFFFFFF)

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onCloseClicked: () -> Unit = {},
    onBookingClick: (Int) -> Unit,
    onVoucherClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onAboutClicked: () -> Unit = {},
    onContactUsClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit,
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (uiState.isLoggedIn) {
                        Text(
                            text = "Welcome, ${uiState.username}!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = uiState.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }

                HomeDrawerMenuContent(
                    onAboutClicked = {
                        scope.launch { drawerState.close() }
                        onAboutClicked()
                    },
                    onContactUsClicked = {
                        scope.launch { drawerState.close() }
                        onContactUsClicked()
                    },
                    onSettingsClicked = {
                        scope.launch { drawerState.close() }
                        onSettingsClicked()
                    },
                    onCloseClicked = {
                        scope.launch { drawerState.close() }
                        onCloseClicked()
                    },
                    onBookingsClicked = {
                        scope.launch { drawerState.close() }
                        onBookingsClicked()
                    },
                    onVouchersClicked = {
                        scope.launch { drawerState.close() }
                        onVouchersClicked()
                    },
                    onMyTripsClicked = {
                        scope.launch { drawerState.close() }
                        onMyTripsClicked()
                    },
                    onProfileClicked = {
                        scope.launch { drawerState.close() }
                        onProfileClicked()
                    }
                )
            }
        },
    ) {
        Scaffold(
            bottomBar = {
                BottomNavBarWithIcons(
                    onHomeClicked = onHomeClicked,
                    onBookingsClicked = onBookingsClicked,
                    onVouchersClicked = onVouchersClicked,
                    onMyTripsClicked = onMyTripsClicked,
                    onProfileClicked = onProfileClicked,
                    currentScreen = "HOME"
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with menu icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                scope.launch { drawerState.open() }
                            }
                    )
                }

                // Main content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Home",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Welcome, ${uiState.username}!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.splashmania_pic),
                        contentDescription = "SplashMania Logo",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(vertical = 20.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.splashmania_home_pic),
                        contentDescription = "SplashMania Attractions",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = 20.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        Text(
                            text = "Welcome to SplashMania!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun HomeDrawerMenuContent(
    onAboutClicked: () -> Unit = {},
    onContactUsClicked: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    onCloseClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(backgroundAColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onCloseClicked) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        HomeMenuItem(
            title = "About SplashMania",
            icon = Icons.Filled.Info,
            onClick = onAboutClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "Contact Us",
            icon = Icons.Filled.Phone,
            onClick = onContactUsClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "Settings",
            icon = Icons.Filled.Settings,
            onClick = onSettingsClicked,
            iconSize = 36.dp
        )
    }
}

@Composable
fun HomeMenuItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    iconSize: Dp = 36.dp // IconSize default value
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier
                .size(iconSize)
                .padding(end = 20.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Navigate",
            tint = Color.Gray,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun AboutSplashManiaScreen(
    onBackClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info",
                tint = Color.Black,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = "About SplashMania",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "SplashMania is an 18-acre outdoor water park located in Gamuda Cove, near Kuala Lumpur. It features 39 thrilling water slides and attractions, including the first-of-its-kind virtual reality waterslide, Atlantis VR, in Malaysia. The park is designed for families, groups of friends, and anyone seeking a fun, wet experience.\n" +
                        "\n" + "Key Features:\n" + "\n" + "Waterslides:\n" + "SplashMania boasts 24 unique and exciting water slides, including the \"Shaka Waka\" and the \"Wild Rush\". \n" + "\n" +
                        "Unique Attractions:\n" + "It includes the Atlantis VR, Malaysia's first virtual reality waterslide, and other innovative water-based experiences. \n" + "\n" + "Family-Friendly:\n" + "The park caters to all ages, with attractions and areas designed for families, including a wave pool and lazy river. \n" + "\n" +
                        "Convenient Location:\n" + "Situated in Gamuda Cove, it's easily accessible from Kuala Lumpur and near KLIA, making it a good day trip option. \n" + "\n" + "Food and Amenities:\n" + "SplashMania offers food and beverage outlets, picnic areas, and other amenities to enhance the visitor experience. \n" +
                        "\n" + "Overall, SplashMania offers a wide range of water-based activities and attractions for a fun and memorable day out, with a focus on both thrilling rides and family-friendly fun.",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ContactUsScreen(
    onBackClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Contact Us",
                tint = Color.Black,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .background(backgroundAColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SplashMania Sdn Bhd\n" + "\nReg. No: 202201014580 (1460058-M)\n" +
                        "\nHead Office: \n" + "SplashMania Waterpark,\n" + "Jalan Cove Sentral 4,\n" +
                        "\nBandar Gamuda Cove,\n" + "42700 Banting, Selangor.\n" + "\nEmail Address:\n" + "customercare@splashmania.com.my",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel = viewModel(),
    username: String,
    email: String,
    onBackClicked: () -> Unit = {},
    onChangeUsernameClicked: () -> Unit = {},
    onChangeEmailClicked: () -> Unit = {},
    onAccountSecurityClicked: () -> Unit = {},
    onDeleteAccountClicked: () -> Unit = {}
) {
    val uiState by homeViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(36.dp)
            )
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
        }

        Divider(color = Color.White, thickness = 1.dp)

        // Username
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onChangeUsernameClicked)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Username",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = uiState.username,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Edit Username",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        // Email
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onChangeEmailClicked)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Email",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = uiState.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Edit Email",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        // Account Security
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onAccountSecurityClicked)
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Account Security",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Account Security",
                modifier = Modifier.size(28.dp)
            )
        }

        Divider(color = Color.LightGray, thickness = 1.dp)

        // Delete Account
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onDeleteAccountClicked)
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Delete Account",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
    }
}

@Composable
fun ChangeUsernameScreen(
    onBackClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onChangeUsernameClicked: (String) -> Unit = {},
) {
    var newUsername by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Profile Update",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Change Username",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = newUsername,
            onValueChange = { newUsername = it },
            placeholder = { Text("New Username") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCancelClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray
            ),
        ) {
            Text(
                text = "CANCEL",
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onChangeUsernameClicked(newUsername) },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "UPDATE",
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ChangeEmailScreen(
    onBackClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onChangeEmailClicked: (String) -> Unit = {}
) {
    var newEmail by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Profile Update",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Change Email",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = newEmail,
            onValueChange = { newEmail = it },
            placeholder = { Text("New Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCancelClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray
            ),
        ) {
            Text(
                text = "CANCEL",
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onChangeEmailClicked(newEmail) },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "UPDATE",
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun AccountSecurityScreen(
    onBackClicked: () -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onUpdateClicked: (String, String) -> Unit = { _, _ -> }
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundAColor)
            .padding(16.dp)
    ) {
        // Header with time and back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable(onClick = onBackClicked)
                    .size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Account Security",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Change Password",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            placeholder = { Text("New Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onCancelClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "CANCEL",
                color = Color.Black,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = Color.White, thickness = 5.dp)

        Button(
            onClick = { onUpdateClicked(newPassword, confirmPassword) },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = "UPDATE",
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}


@Composable
fun DeleteAccountDialog(
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Delete Your Account?") },
        confirmButton = {
            Button(
                onClick = onConfirmDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Yes, Delete My Account", color = buttonColor)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("No", color = Color.White)
            }
        }
    )
}