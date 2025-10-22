package com.example.splashmaniaapp.ui.screen.vouchers

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.ui.screen.home.HomeMenuItem
import com.example.splashmaniaapp.ui.screen.mytrips.BottomNavBarWithIcons
import kotlinx.coroutines.launch

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val redeemedColor = Color(0xFF70A9EA)

@Composable
fun VouchersScreen(
    viewModel: VouchersViewModel = viewModel(),
    onRedeemClicked: (Int) -> Unit = {},
    onVoucherClicked: (Int) -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                VouchersDrawerMenuContent(
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
                    currentScreen = "VOUCHERS"
                )
            }
        ) { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Vouchers",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.splashmania_pic),
                        contentDescription = "SplashMania Logo",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(top = 10.dp, bottom = 10.dp)
                    )
                }

                if(uiState.vouchers.isEmpty()) {
                    Text(
                        text = "No vouchers available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(40.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.vouchers) { voucher ->
                            VoucherCard(
                                voucher = voucher,
                                onRedeemClicked = { viewModel.redeemVoucher(voucher.id) },
                                onClick = { onVoucherClicked(voucher.id) }
                            )
                        }
                    }
                }

                // Error messages
                uiState.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VoucherCard(
    voucher: Voucher,
    onRedeemClicked: () -> Unit,
    onClick: () -> Unit = {}
) {
    val isRedeemed = voucher.isRedeemed

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.voucher),
                contentDescription = "Voucher",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                alignment = Alignment.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = voucher.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    if (voucher.description.isNotEmpty()) {
                        Text(
                            text = voucher.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (voucher.minSpend.isNotEmpty() && voucher.minSpend != "0") {
                        Text(
                            text = "with min spend RM${voucher.minSpend}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    if (voucher.terms.isNotEmpty()) {
                        Text(
                            text = "*${voucher.terms}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    Button(
                        onClick = onRedeemClicked,
                        enabled = !voucher.isRedeemed,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isRedeemed) redeemedColor else buttonColor
                        )
                    ) {
                        Text(
                            text = if (isRedeemed) "REDEEMED" else "REDEEM",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VoucherDetailsScreen(
    viewModel: VouchersViewModel,
    voucherId: Int,
    onBackClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val voucher = uiState.vouchers.find { it.id == voucherId }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(voucherId) {
        val v = viewModel.getVoucherById(voucherId)
        if (v?.isRedeemed == true) {
            viewModel.markAsRedeemed(voucherId)
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Back",
                    )
                }
                Text(
                    text = "Voucher Details",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        voucher?.let {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.voucher),
                    contentDescription = "Voucher",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp)
                )

                Text(
                    text = it.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = it.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                if (it.minSpend.isNotEmpty() && it.minSpend != "0") {
                    Text(
                        text = "Minimum spend: RM${it.minSpend}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

                // Redeem button
                Button(
                    onClick = {
                        viewModel.redeemVoucher(it.id)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Voucher redeemed successfully!")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (it.isRedeemed) redeemedColor else buttonColor
                    ),
                    enabled = !it.isRedeemed,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (it.isRedeemed) "REDEEMED" else "REDEEM",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Text(
                    text = "Terms & Conditions:\n${it.terms}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        } ?: run {
            Text(
                text = "Voucher not found",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun VouchersDrawerMenuContent(
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