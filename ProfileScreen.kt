package com.example.splashmaniaapp.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.ui.screen.home.HomeMenuItem
import com.example.splashmaniaapp.ui.screen.home.HomeViewModel
import com.example.splashmaniaapp.ui.screen.mytrips.BottomNavBarWithIcons
import kotlinx.coroutines.launch

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val selectedTextColor = Color(0xFF0038D0)
val userAvatarColor = Color(0xFF70A9EA)

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    username: String,
    userEmail: String,
    onMyVouchersClicked: () -> Unit = {},
    onPaymentDetailsClicked: () -> Unit = {},
    onLogoutClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    val profileUiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.loadUserFromAuth()
    }

    if(showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirmLogout = {
                showLogoutDialog = false
                onLogoutClicked()
            }
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                ProfileDrawerMenuContent(
                    onHomeClicked = {
                        scope.launch { drawerState.close() }
                        onHomeClicked()
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
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomNavBarWithIcons(
                    onHomeClicked = onHomeClicked,
                    onBookingsClicked = onBookingsClicked,
                    onVouchersClicked = onVouchersClicked,
                    onMyTripsClicked = onMyTripsClicked,
                    onProfileClicked = onProfileClicked,
                    currentScreen = "PROFILE"
                )
            }
        ) { innerPadding ->
            when(profileUiState.currentStep) {
                ProfileState.MAIN_PROFILE -> MainProfileScreen(
                    modifier = Modifier.padding(innerPadding),
                    username = homeUiState.username,
                    userEmail = homeUiState.email,
                    onMyVouchersClicked = { viewModel.navigateTo(ProfileState.MY_VOUCHERS) },
                    onPaymentDetailsClicked = { viewModel.navigateTo(ProfileState.PAYMENT_DETAILS) },
                    onLogoutClicked = { showLogoutDialog = true }
                )

                ProfileState.MY_VOUCHERS -> MyVouchersScreen(
                    vouchers = profileUiState.vouchers,
                    onBackClicked = { viewModel.navigateBack() }
                )

                ProfileState.PAYMENT_DETAILS -> PaymentDetailsScreen(
                    paymentMethods = profileUiState.paymentMethods,
                    onBackClicked = { viewModel.navigateBack() },
                    onChangePhoneNumber = { viewModel.navigateTo(ProfileState.CHANGE_PHONE_NUMBER) },
                    onChangeAccountNumber = { viewModel.navigateTo(ProfileState.CHANGE_ACCOUNT_NUMBER) }
                )

                ProfileState.CHANGE_PHONE_NUMBER -> ChangePhoneNumberScreen(
                    viewModel = viewModel,
                    currentPhone = "+601111480378",
                    onBackClicked = { viewModel.navigateBack() },
                    onUpdateClicked = { newPhone ->
                        viewModel.updatePhone(newPhone)
                        viewModel.navigateTo(ProfileState.PAYMENT_DETAILS)
                    },
                    onCancelClicked = { viewModel.navigateTo(ProfileState.PAYMENT_DETAILS) }
                )

                ProfileState.CHANGE_ACCOUNT_NUMBER -> ChangeAccountNumberScreen(
                    viewModel = viewModel,
                    currentAccount = "******9408",
                    onBackClicked = { viewModel.navigateBack() },
                    onUpdateClicked = { newAccount ->
                        viewModel.updateAccount(newAccount)
                        viewModel.navigateTo(ProfileState.PAYMENT_DETAILS)
                    },
                    onCancelClicked = { viewModel.navigateTo(ProfileState.PAYMENT_DETAILS) }
                )
            }
        }
    }
}

@Composable
fun ProfileDrawerMenuContent(
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    onCloseClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(com.example.splashmaniaapp.ui.screen.bookings.backgroundColor)
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
            title = "Home",
            icon = Icons.Filled.Home,
            onClick = onHomeClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "Bookings",
            icon = Icons.Filled.ShoppingCart,
            onClick = onBookingsClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "Vouchers",
            icon = Icons.Filled.FavoriteBorder,
            onClick = onVouchersClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "My Trips",
            icon = Icons.Filled.Place,
            onClick = onMyTripsClicked,
            iconSize = 36.dp
        )

        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))

        HomeMenuItem(
            title = "Profile",
            icon = Icons.Filled.Person,
            onClick = onProfileClicked,
            iconSize = 36.dp
        )
    }
}

@Composable
fun MainProfileScreen(
    modifier: Modifier = Modifier,
    username: String,
    userEmail: String,
    onMyVouchersClicked: () -> Unit,
    onPaymentDetailsClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(R.drawable.splashmania_pic),
            contentDescription = "SplashMania Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 10.dp, bottom = 10.dp)
        )
        // User Profile Section
        Box(
            // User Avatar
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(userAvatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getInitials(username),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Username
        Text(
            text = username,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )

        // User email
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Divider(color = backgroundColor, thickness = 1.dp)

        // Menu Items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // My Vouchers
            ProfileMenuItem(
                title = "My Vouchers",
                onClick = onMyVouchersClicked,
                showArrow = true
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Payment Details
            ProfileMenuItem(
                title = "Payment Details",
                onClick = onPaymentDetailsClicked,
                showArrow = true
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            // Log Out
            ProfileMenuItem(
                title = "Log Out",
                onClick = onLogoutClicked,
                textColor = Color.Red,
                showArrow = false
            )
        }
    }
}

fun getInitials(name: String): String {
    val parts = name.trim().split(" ")
    return when {
        parts.size >= 2 -> {
            "${parts[0].first()}${parts[1].first()}".uppercase()
        }
        parts.size == 1 && parts[0].isNotEmpty() -> {
            parts[0].take(2).uppercase()
        }
        else -> ""
    }
}

@Composable
fun ProfileMenuItem(
    title: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
    showArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        if(showArrow) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Navigate",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun MyVouchersScreen(
    vouchers: List<Voucher>,
    onBackClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onBackClicked)
                    .padding(horizontal = 8.dp),
                tint = Color.Black
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "My Vouchers",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(color = backgroundColor, thickness = 1.dp)

        // Voucher List
        if(vouchers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Vouchers Available",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(vouchers) { voucher ->
                    VoucherItem(voucher = voucher)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "PROFILE"
        )
    }
}

@Composable
fun VoucherItem(voucher: Voucher) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = voucher.code,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = buttonColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "with min.spend RM${voucher.minSpend}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (voucher.availableForAll) "Available for all users" else "Limited availability",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun PaymentDetailsScreen(
    paymentMethods: List<PaymentMethod>,
    onBackClicked: () -> Unit = {},
    onChangePhoneNumber: () -> Unit = {},
    onChangeAccountNumber: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onBackClicked)
                    .padding(horizontal = 8.dp),
                tint = Color.Black
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.size(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Payment Details",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(color = backgroundColor, thickness = 1.dp)

        // Payment Methods List
        if (paymentMethods.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Payment Methods Found",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(paymentMethods) { method ->
                    PaymentMethodItem(
                        method = method,
                        onEditClicked = {
                            if (method.type == "Touch n Go") {
                                onChangePhoneNumber()
                            } else if(method.type == "FPX Online Banking") {
                                onChangeAccountNumber()
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "PROFILE"
        )
    }
}

@Composable
fun PaymentMethodItem(
    method: PaymentMethod,
    onEditClicked: () -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = method.type,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = method.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = onEditClicked,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = buttonColor
                )
            }
        }
    }
}

@Composable
fun ChangePhoneNumberScreen(
    viewModel: ProfileViewModel = viewModel(),
    currentPhone: String = "+601111480378",
    onBackClicked: () -> Unit = {},
    onUpdateClicked: (String) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    var newPhone by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onBackClicked)
                    .padding(horizontal = 8.dp),
                tint = Color.Black
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Change Phone Number",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(color = backgroundColor, thickness = 1.dp)

        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Payment method header
            Text(
                text = "Touch n Go",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = currentPhone,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = newPhone,
                onValueChange = { input ->
                    val formattedInput = if (input.startsWith("+60")) {
                        input
                    } else if (input.startsWith("60")) {
                        "+$input"
                    } else if (input.isNotEmpty() && !input.startsWith("+")) {
                        "+60$input"
                    } else {
                        input
                    }

                    if (formattedInput.length <= 13) { // +60 + 10 digits = 13 chars
                        newPhone = formattedInput

                        errorMessage = viewModel.validatePhoneNumber(formattedInput)
                    }
                },
                label = { Text("New Phone Number") },
                placeholder = { Text("+60XXXXXXXXX")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = errorMessage != null,
                supportingText = {
                    if(errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red
                        )
                    } else {
                        Text("Format: +60XXXXXXXXXX (10 digits)")
                    }
                }
            )

            // Buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Cancel Button
                Button(
                    onClick = onCancelClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "CANCEL",
                        color = selectedTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Update Button
                Button(
                    onClick = {
                        val validationError = viewModel.validatePhoneNumber(newPhone)
                        if (validationError == null) {
                            onUpdateClicked(newPhone)
                        } else {
                            errorMessage = validationError
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    enabled = newPhone.isNotBlank() && newPhone != currentPhone && errorMessage == null
                ) {
                    Text(
                        text = "UPDATE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "PROFILE"
        )
    }
}

@Composable
fun ChangeAccountNumberScreen(
    viewModel: ProfileViewModel = viewModel(),
    currentAccount: String = "******9408",
    onBackClicked: () -> Unit = {},
    onUpdateClicked: (String) -> Unit = {},
    onCancelClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    var newAccount by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back",
                modifier = Modifier
                    .size(40.dp)
                    .clickable(onClick = onBackClicked)
                    .padding(horizontal = 8.dp),
                tint = Color.Black
            )

            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.width(48.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Change Account Number",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Divider(color = backgroundColor, thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "FPX Online Banking",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = currentAccount,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = newAccount,
                onValueChange = { input ->
                    val digitsOnly = input.filter { it.isDigit() }
                    if (digitsOnly.length <= 10) {
                        newAccount = digitsOnly
                        errorMessage = viewModel.validateAccountNumber(digitsOnly)
                    }
                },
                label = { Text("New Account Number") },
                placeholder = { Text("Enter 10-digit account number") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errorMessage != null,
                supportingText = {
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage!!,
                            color = Color.Red
                        )
                    } else {
                        Text("Enter exactly 10 digits")
                    }
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Cancel Button
                Button(
                    onClick = onCancelClicked,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "CANCEL",
                        color = selectedTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        val validationError = viewModel.validateAccountNumber(newAccount)
                        if (validationError == null) {
                            // 格式化显示为 ****** + 最后4位数字
                            val formattedAccount = "******${newAccount.takeLast(4)}"
                            onUpdateClicked(formattedAccount)
                        } else {
                            errorMessage = validationError
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    enabled = newAccount.isNotBlank() && errorMessage == null
                ) {
                    Text(
                        text = "UPDATE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "PROFILE"
        )
    }
}

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirmLogout: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Log Out?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Are you sure you want to log out?",
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmLogout,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Yes, Log Out", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("Cancel", color = Color.Black)
            }
        }
    )
}