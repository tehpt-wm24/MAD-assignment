package com.example.splashmaniaapp.ui.screen.mytrips

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.di.AppViewModelProvider
import com.example.splashmaniaapp.ui.screen.home.HomeMenuItem
import kotlinx.coroutines.launch

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val selectedTextColor = Color(0xFF0038D0)

@Composable
fun MyTripsScreen(
    viewModel: MyTripsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onUpcomingClicked: () -> Unit,
    onCompletedClicked: () -> Unit,
    onCancelledClicked: () -> Unit,
    onPlanButtonClicked: () -> Unit = {},
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
                MyTripsDrawerMenuContent(
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
                    },
                    onCloseClicked = {
                        scope.launch { drawerState.close() }
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
                    currentScreen = "MY TRIPS"
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
                Text(
                    text = "My Trips",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                )

                Divider(color = backgroundColor, thickness = 1.dp)

                Image(
                    painter = painterResource(R.drawable.splashmania_pic),
                    contentDescription = "SplashMania Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Upcoming
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.changeTripStatus(TripStatus.UPCOMING) }
                    ) {
                        Text(
                            text = "Upcoming",
                            color = if(uiState.selectedStatus == TripStatus.UPCOMING) selectedTextColor else Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.upcomingCount}",
                            color = if(uiState.selectedStatus == TripStatus.UPCOMING) selectedTextColor else Color.Black,
                            fontSize = 16.sp
                        )
                    }

                    // Completed
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.changeTripStatus(TripStatus.COMPLETED) }
                    ) {
                        Text(
                            text = "Completed",
                            color = if(uiState.selectedStatus == TripStatus.COMPLETED) selectedTextColor else Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.completedCount}",
                            color = if(uiState.selectedStatus == TripStatus.COMPLETED) selectedTextColor else Color.Black,
                            fontSize = 16.sp
                        )
                    }

                    // Cancelled
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { viewModel.changeTripStatus(TripStatus.CANCELLED) }
                    ) {
                        Text(
                            text = "Cancelled",
                            color = if(uiState.selectedStatus == TripStatus.CANCELLED) selectedTextColor else Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.cancelledCount}",
                            color = if(uiState.selectedStatus == TripStatus.CANCELLED) selectedTextColor else Color.Black,
                            fontSize = 16.sp
                        )
                    }
                }

                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(bottom = 10.dp))

                val filteredTrips = when (uiState.selectedStatus) {
                    TripStatus.UPCOMING -> uiState.trips.filter { it.status == TripStatus.UPCOMING }
                    TripStatus.COMPLETED -> uiState.trips.filter { it.status == TripStatus.COMPLETED }
                    TripStatus.CANCELLED -> uiState.trips.filter { it.status == TripStatus.CANCELLED }
                }

                if (filteredTrips.isEmpty()) {
                    when (uiState.selectedStatus) {
                        TripStatus.UPCOMING -> EmptyTripsState(onPlanButtonClicked)
                        TripStatus.COMPLETED -> EmptyStateForStatus("No Completed Trips", "You haven't completed any trips yet")
                        TripStatus.CANCELLED -> EmptyStateForStatus("No Cancelled Trips", "You haven't cancelled any trips yet")
                    }
                } else {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(filteredTrips.take(2)) { trip ->
                            TripPreviewItem(trip = trip)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    Button(
                        onClick = {
                            when (uiState.selectedStatus) {
                                TripStatus.UPCOMING -> onUpcomingClicked()
                                TripStatus.COMPLETED -> onCompletedClicked()
                                TripStatus.CANCELLED -> onCancelledClicked()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = when (uiState.selectedStatus) {
                                TripStatus.UPCOMING -> "View All Upcoming Trips"
                                TripStatus.COMPLETED -> "View All Completed Trips"
                                TripStatus.CANCELLED -> "View All Cancelled Trips"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TripPreviewItem(trip: Trip) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = trip.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Date: ${trip.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MyTripsDrawerMenuContent(
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
            .background(backgroundColor)
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
fun UpcomingScreen(
    viewModel: MyTripsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClicked: () -> Unit,
    onPlanButtonClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val upcomingTrips = uiState.trips.filter { it.status == TripStatus.UPCOMING }

    TripsListScreen(
        title = "Upcoming Trips",
        trips = upcomingTrips,
        status = TripStatus.UPCOMING,
        isLoading = false,
        errorMessage = uiState.errorMessage,
        onBackClicked = onBackClicked,
        onPlanButtonClicked = onPlanButtonClicked,
        onCancelTrip = { tripId -> viewModel.cancelTrip(tripId) },
        showCancelButton = true,
        onHomeClicked = onHomeClicked,
        onBookingsClicked = onBookingsClicked,
        onVouchersClicked = onVouchersClicked,
        onMyTripsClicked = onMyTripsClicked,
        onProfileClicked = onProfileClicked
    )
}

@Composable
fun CompletedScreen(
    viewModel: MyTripsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClicked: () -> Unit,
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val completedTrips = uiState.trips.filter { it.status == TripStatus.COMPLETED }

    TripsListScreen(
        title = "Completed Trips",
        trips = completedTrips,
        status = TripStatus.COMPLETED,
        isLoading = false,
        errorMessage = uiState.errorMessage,
        onBackClicked = onBackClicked,
        onPlanButtonClicked = {},
        onCancelTrip = {},
        showCancelButton = false,
        onHomeClicked = onHomeClicked,
        onBookingsClicked = onBookingsClicked,
        onVouchersClicked = onVouchersClicked,
        onMyTripsClicked = onMyTripsClicked,
        onProfileClicked = onProfileClicked
    )
}

@Composable
fun CancelledScreen(
    viewModel: MyTripsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onBackClicked: () -> Unit,
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val cancelledTrips = uiState.trips.filter { it.status == TripStatus.CANCELLED }

    TripsListScreen(
        title = "Cancelled Trips",
        trips = cancelledTrips,
        status = TripStatus.CANCELLED,
        isLoading = false,
        errorMessage = uiState.errorMessage,
        onBackClicked = onBackClicked,
        onPlanButtonClicked = {},
        onCancelTrip = {},
        showCancelButton = false,
        onHomeClicked = onHomeClicked,
        onBookingsClicked = onBookingsClicked,
        onVouchersClicked = onVouchersClicked,
        onMyTripsClicked = onMyTripsClicked,
        onProfileClicked = onProfileClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripsListScreen(
    title: String,
    trips: List<Trip>,
    status: TripStatus,
    isLoading: Boolean,
    errorMessage: String?,
    onBackClicked: () -> Unit,
    onPlanButtonClicked: () -> Unit,
    onCancelTrip: (Int) -> Unit,
    showCancelButton: Boolean,
    onHomeClicked: () -> Unit,
    onBookingsClicked: () -> Unit,
    onVouchersClicked: () -> Unit,
    onMyTripsClicked: () -> Unit,
    onProfileClicked: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                MyTripsDrawerMenuContent(
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
                    },
                    onCloseClicked = {
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Back",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = backgroundColor,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black
                    )
                )
            },
            bottomBar = {
                BottomNavBarWithIcons(
                    onHomeClicked = onHomeClicked,
                    onBookingsClicked = onBookingsClicked,
                    onVouchersClicked = onVouchersClicked,
                    onMyTripsClicked = onMyTripsClicked,
                    onProfileClicked = onProfileClicked,
                    currentScreen = "MY TRIPS"
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backgroundColor)
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = buttonColor)
                    }
                } else if (errorMessage != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error loading trips",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                        Button(
                            onClick = { /* 重试逻辑 */ },
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                        ) {
                            Text(text = "Retry", color = Color.White)
                        }
                    }
                } else if (trips.isEmpty()) {
                    when(status) {
                        TripStatus.UPCOMING -> EmptyTripsState(onPlanButtonClicked)
                        TripStatus.COMPLETED -> EmptyStateForStatus("No Completed Trips", "You haven't completed any trips yet")
                        TripStatus.CANCELLED -> EmptyStateForStatus("No Cancelled Trips", "You haven't cancelled any trips yet")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 8.dp)
                    ) {
                        items(trips) { trip ->
                            when (status) {
                                TripStatus.UPCOMING -> UpcomingTripItem(
                                    trip = trip,
                                    onCancelTrip = onCancelTrip
                                )
                                TripStatus.COMPLETED -> CompletedTripItem(trip = trip)
                                TripStatus.CANCELLED -> CancelledTripItem(trip = trip)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyStateForStatus(title: String, message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

@Composable
fun UpcomingTripItem(
    trip: Trip,
    onCancelTrip: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = trip.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = selectedTextColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Date: ${trip.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            Column(
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                if (trip.adults > 0) {
                    Text(
                        text = "Adult × ${trip.adults}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                if (trip.children > 0) {
                    Text(
                        text = "Child × ${trip.children}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
                if (trip.seniors > 0) {
                    Text(
                        text = "Senior × ${trip.seniors}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(1f))

                // Upcoming
                Image(
                    painter = painterResource(R.drawable.upcoming),
                    contentDescription = "Upcoming Status",
                    modifier = Modifier.size(80.dp)
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint = selectedTextColor
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Are you sure you want to cancel this trip?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = { expanded = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray
                            ),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "KEEP", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                onCancelTrip(trip.id)
                                expanded = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {
                            Text(text = "CANCEL", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CompletedTripItem(trip: Trip, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = trip.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0038D0),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Date: ${trip.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Ticket details
            if (trip.adults > 0 || trip.children > 0 || trip.seniors > 0) {
                Divider(color = Color.LightGray, thickness = 1.dp)
                Column(
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    if (trip.adults > 0) {
                        Text(
                            text = "Adult × ${trip.adults}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    if (trip.children > 0) {
                        Text(
                            text = "Child × ${trip.children}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                    if (trip.seniors > 0) {
                        Text(
                            text = "Senior × ${trip.seniors}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }

            // Completed
            Image(
                painter = painterResource(R.drawable.completed),
                contentDescription = "Completed Status",
                modifier = Modifier
                    .align(Alignment.End)
                    .size(80.dp)
            )
        }
    }
}

@Composable
fun CancelledTripItem(trip: Trip, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = trip.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Date: ${trip.date}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Cancelled
            Image(
                painter = painterResource(R.drawable.cancelled),
                contentDescription = "Cancelled Status",
                modifier = Modifier
                    .align(Alignment.End)
                    .size(80.dp)
            )
        }
    }
}

@Composable
fun EmptyTripsState(onPlanButtonClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Upcoming Trips",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Start planning your next trip",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onPlanButtonClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            ),
            modifier = Modifier.size(width = 120.dp, height = 48.dp)
        ) {
            Text(
                text = "PLAN",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun BottomNavBarWithIcons(
    onHomeClicked: () -> Unit,
    onBookingsClicked: () -> Unit,
    onVouchersClicked: () -> Unit,
    onMyTripsClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    currentScreen: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Home
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onHomeClicked)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = if(currentScreen == "HOME") buttonColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "HOME",
                color = if(currentScreen == "HOME") buttonColor else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Bookings
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onBookingsClicked)
        ) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Bookings",
                tint = if(currentScreen == "BOOKINGS") buttonColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "BOOKINGS",
                color = if(currentScreen == "BOOKINGS") buttonColor else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Vouchers
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onVouchersClicked)
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Vouchers",
                tint = if(currentScreen == "VOUCHERS") buttonColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "VOUCHERS",
                color = if(currentScreen == "VOUCHERS") buttonColor else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // My Trips
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onMyTripsClicked)
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = "My Trips",
                tint = if(currentScreen == "MY TRIPS") buttonColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "MY TRIPS",
                color = if(currentScreen == "MY TRIPS") buttonColor else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Profile
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onProfileClicked)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Profile",
                tint = if(currentScreen == "PROFILE") buttonColor else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "PROFILE",
                color = if(currentScreen == "PROFILE") buttonColor else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}