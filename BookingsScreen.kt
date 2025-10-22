package com.example.splashmaniaapp.ui.screen.bookings

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.splashmaniaapp.R
import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.ui.screen.mytrips.BottomNavBarWithIcons
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

val backgroundColor = Color(0xFFEEFFFF)
val buttonColor = Color(0xFF0038D0)
val selectedColor = Color(0xFFE3F2FD)

@Composable
fun BookingsScreen(
    viewModel: BookingsViewModel = viewModel(),
    onSelectDate: () -> Unit,
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNavBarWithIcons(
                onHomeClicked = onHomeClicked,
                onBookingsClicked = onBookingsClicked,
                onVouchersClicked = onVouchersClicked,
                onMyTripsClicked = onMyTripsClicked,
                onProfileClicked = onProfileClicked,
                currentScreen = "BOOKINGS"
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
                text = "Bookings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(bottom = 50.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(R.drawable.splashmania_pic),
                contentDescription = "SplashMania Logo",
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 10.dp, bottom = 100.dp)
            )

            Button(
                onClick = onSelectDate,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Start a Booking")
            }
        }
    }
}

@Composable
fun BookingScaffold(
    currentScreen: String = "BOOKINGS",
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomNavBarWithIcons(
                onHomeClicked = onHomeClicked,
                onBookingsClicked = onBookingsClicked,
                onVouchersClicked = onVouchersClicked,
                onMyTripsClicked = onMyTripsClicked,
                onProfileClicked = onProfileClicked,
                currentScreen = currentScreen
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
        ) {
            content(innerPadding)
        }
    }
}

@Composable
fun BookingDateScreen(
    viewModel: BookingsViewModel = viewModel(),
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedDate = uiState.selectedDate
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val pickedDate = LocalDate(year, month + 1, dayOfMonth)
                val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                if (pickedDate >= today) {
                    viewModel.selectDate(pickedDate)
                } else {
                    Toast.makeText(context, "Please select today or a future date", Toast.LENGTH_SHORT).show()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(Unit) {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        datePickerDialog.datePicker.minDate = today.timeInMillis
    }

    BookingScaffold(currentScreen = "BOOKINGS") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .padding(bottom = 200.dp),
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
                    text = "Bookings",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Select Date",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            Text(
                text = selectedDate?.let {
                    "${it.dayOfMonth} ${it.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }} ${it.year}"
                } ?: "No date selected",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )

            Button(
                onClick = { datePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Pick a Date")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onBackClicked) { Text("Back") }
                Button(
                    onClick = onNextClicked,
                    enabled = selectedDate != null
                ) {
                    Text("Next")
                }
            }
        }
    }
}



@Composable
fun SelectDateView(viewModel: BookingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "DATE",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Add this extension function
        fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate {
            val daysUntil = (dayOfWeek.isoDayNumber - this.dayOfWeek.isoDayNumber + 7) % 7
            return if (daysUntil == 0) {
                this.plus(7, DateTimeUnit.DAY) // Next week same day
            } else {
                this.plus(daysUntil, DateTimeUnit.DAY)
            }
        }

        // Simple date selection
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val tomorrow = today.plus(1, DateTimeUnit.DAY)

        // Calculate next Saturday
        val weekendDate = if (today.dayOfWeek == DayOfWeek.SATURDAY) {
            today
        } else {
            val daysUntilSaturday = (DayOfWeek.SATURDAY.isoDayNumber - today.dayOfWeek.isoDayNumber + 7) % 7
            today.plus(daysUntilSaturday, DateTimeUnit.DAY)
        }

        DateOption(
            date = today,
            onClick = { viewModel.selectDate(today) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateOption(
            date = tomorrow,
            onClick = { viewModel.selectDate(tomorrow) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DateOption(
            date = weekendDate,
            onClick = { viewModel.selectDate(weekendDate) }
        )
    }
}

@Composable
fun DateOption(date: LocalDate, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val formatted = "${date.dayOfMonth} ${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.year}"
            val weekday = date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
            Text(
                text = formatted,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = weekday,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun NationalitySelectionScreen(
    viewModel: BookingsViewModel = viewModel(),
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedNationality = uiState.selectedNationality

    BookingScaffold(currentScreen = "BOOKINGS") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .padding(bottom = 200.dp),
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
                    text = "Bookings",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Select Nationality",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            NationalityOption(
                text = "Malaysian citizens",
                isSelected = selectedNationality == "Malaysian",
                onClick = {
                    viewModel.selectNationality("Malaysian")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NationalityOption(
                text = "Non-Malaysian citizens",
                isSelected = selectedNationality == "Non-Malaysian",
                onClick = {
                    viewModel.selectNationality("Non-Malaysian")
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onBackClicked) { Text("Back") }
                Button(
                    onClick = {
                        if(selectedNationality != null) {
                            onNextClicked()
                        }
                    },
                    enabled = selectedNationality != null
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
fun NationalityOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val cardBg = if(isSelected) {
        backgroundColor
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = if(isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.LightGray
    }

    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Black
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        }
    }
}

@Composable
fun TicketQuantityScreen(
    viewModel: BookingsViewModel = viewModel(),
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.selectedDate, uiState.selectedNationality) {
        val nationality = uiState.selectedNationality
        val date = uiState.selectedDate
        if(nationality != null && date != null) {
            println("DEBUG TicketQuantityScreen -> ready with $nationality on $date")
        }
    }

    BookingScaffold(currentScreen = "BOOKINGS") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(vertical = 20.dp)
                    .padding(bottom = 40.dp),
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
                    text = "Bookings",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }

            Text(
                text = "Select Number of Tickets",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            uiState.tickets.forEach { ticket ->
                TicketSelector(
                    ticket = ticket,
                    onQuantityChange = { quantity -> viewModel.updateTicketQuantity(ticket.type, quantity) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Total Payable: RM${uiState.totalAmount.toInt()}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onBackClicked() },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("BACK")
                }

                Button(
                    onClick = { onNextClicked() },
                    enabled = uiState.totalAmount > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("NEXT")
                }
            }
        }
    }
}

@Composable
fun TicketSelector(
    ticket: BookingTicket,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "${ticket.type}\n(${getAgeRange(ticket.type)})",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "RM${ticket.price.toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = buttonColor
                )
            }

            QuantitySelector(
                quantity = ticket.quantity,
                onIncrement = { onQuantityChange(ticket.quantity + 1) },
                onDecrement = { onQuantityChange((ticket.quantity - 1).coerceAtLeast(0)) }
            )
        }
    }
}

private fun getAgeRange(ticketType: String): String {
    return when(ticketType) {
        "Adult" -> "13 - 59 years old"
        "Child" -> "4 - 12 years old"
        "Senior" -> "60 years old and above"
        else -> ""
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            onClick = onDecrement,
            enabled = quantity > 0,
            modifier = Modifier.size(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = if(quantity > 0) buttonColor else Color.LightGray)
        ) {
            Text(
                text = "-",
                color = if(quantity > 0) Color.White else Color.Black,
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = "$quantity",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(30.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        Button(
            onClick = onIncrement,
            modifier = Modifier.size(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text(
                text = "+",
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PaymentScreen(
    viewModel: BookingsViewModel = viewModel(),
    onApplyVoucher: () -> Unit,
    onPaymentDone: () -> Unit,
    onBackClicked: () -> Unit,
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    BookingScaffold(
        onHomeClicked = onHomeClicked,
        onBookingsClicked = onBookingsClicked,
        onVouchersClicked = onVouchersClicked,
        onMyTripsClicked = onMyTripsClicked,
        onProfileClicked = onProfileClicked ,
        currentScreen = "BOOKINGS"
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Payment",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            PaymentView(viewModel)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onBackClicked) { Text("Back") }
                Button(onClick = onApplyVoucher) { Text("Apply Voucher") }
                Button(onClick = onPaymentDone) { Text("Pay Now") }
            }
        }
    }
}

@Composable
fun PaymentView(
    viewModel: BookingsViewModel,
    onBackClicked: () -> Unit = {},
    onApplyClicked: () -> Unit = {},
    onViewDetailsClicked: () -> Unit = {},
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDetails by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Payment",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Select Your Payment Method",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        PaymentMethodOption(
            text = "Touch n Go",
            selected = uiState.paymentMethod == "Touch n Go",
            onClick = { viewModel.setPaymentMethod("Touch n Go") }
        )
        PaymentMethodOption(
            text = "FPX Online Banking",
            selected = uiState.paymentMethod == "FPX Online Banking",
            onClick = { viewModel.setPaymentMethod("FPX Online Banking") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onApplyClicked() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Apply Voucher",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }

        uiState.appliedVoucher?.let { voucher ->
            Text(
                text = voucher.code,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = buttonColor,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "RM ${uiState.finalAmount.toInt()}",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "View details",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { showDetails = true },
                color = buttonColor,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onBackClicked() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text("BACK")
            }
            Button(
                onClick = { viewModel.confirmPayment() },
                enabled = uiState.paymentMethod.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("PAY")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "BOOKINGS"
        )
    }

    if(showDetails) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { showDetails = false },
            contentAlignment = Alignment.Center
        ) {
            ViewDetails(
                viewModel = viewModel,
                onDismiss = { showDetails = false }
            )
        }
    }
}

@Composable
fun PaymentMethodOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(if(selected) selectedColor else Color.Transparent, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = buttonColor)
        )
        Text(
            text,
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun VoucherSelectionScreen(
    viewModel: BookingsViewModel = viewModel(),
    onApplyClicked: () -> Unit,
) {
    BookingScaffold(currentScreen = "BOOKINGS") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Apply Voucher",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            ApplyVoucherView(viewModel)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onApplyClicked,
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("Apply")
                }
            }
        }
    }
}

@Composable
fun ApplyVoucherView(
    viewModel: BookingsViewModel,
    onBackClicked: () -> Unit = {}
) {
    val vouchers = viewModel.getAvailableVouchers()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Apply Voucher",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Enter promo code",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Available Vouchers",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))
        vouchers.forEach { voucher ->
            VoucherCard(
                voucher = voucher,
                onClick = { viewModel.selectVoucher(voucher) }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onBackClicked() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("BACK")
        }
    }
}

@Composable
fun VoucherCard(
    voucher: AppliedVoucher,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(R.drawable.voucher),
            contentDescription = "Voucher"
        )
        Text(
            text = voucher.code,
            style = MaterialTheme.typography.headlineSmall,
            color = buttonColor,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "RM${voucher.discount.toInt()} OFF VOUCHER",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "with min spend ${voucher.minSpend.toInt()}",
            style = MaterialTheme.typography.bodyMedium,
            )
        Text(
            text = "Available for all users",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun ViewDetails(
    viewModel: BookingsViewModel,
    onDismiss: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    val isMalaysian = uiState.selectedNationality == "Malaysian"
    val isWeekend = uiState.selectedDate?.let { date ->
        date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY
    } ?: false

    // 使用 ViewModel 中的价格常量
    val adultPrice = if(isMalaysian) {
        if(isWeekend) 120.0 else 110.0
    } else {
        if(isWeekend) 150.0 else 140.0
    }

    val childPrice = if(isMalaysian) {
        if(isWeekend) 100.0 else 90.0
    } else {
        if(isWeekend) 130.0 else 120.0
    }

    val seniorPrice = if(isMalaysian) {
        if(isWeekend) 100.0 else 90.0
    } else {
        if(isWeekend) 130.0 else 120.0
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Booking Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = "${if(isMalaysian) "Malaysian" else "Non-Malaysian"} Admission Ticket (${if (isWeekend) "Weekend" else "Weekday"})",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val dateFormatted = uiState.selectedDate?.let {
                "${it.dayOfMonth} ${it.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }} ${it.year}"
            } ?: ""

            Text(
                text = "Date: $dateFormatted",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            uiState.tickets.forEach { ticket ->
                if (ticket.quantity > 0) {
                    val ticketPrice = when (ticket.type) {
                        "Adult" -> adultPrice
                        "Child" -> childPrice
                        "Senior" -> seniorPrice
                        else -> 0.0
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${ticket.type} x ${ticket.quantity}")
                        Text("RM${(ticketPrice * ticket.quantity).toInt()}")
                    }
                }
            }

            uiState.appliedVoucher?.let { voucher ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(voucher.code, color = buttonColor, fontWeight = FontWeight.SemiBold)
                    Text("-RM${voucher.discount.toInt()}", color = buttonColor)
                }
            }

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "RM${uiState.finalAmount.toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = buttonColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("CLOSE", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TicketScreen(
    viewModel: BookingsViewModel = viewModel(),
    onFinish: () -> Unit
) {
    BookingScaffold(currentScreen = "BOOKINGS") { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Ticket",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(16.dp)
            )

            ConfirmationView(
                viewModel = viewModel,
                onHomeClicked = onFinish,
                onBookingsClicked = onFinish,
                onVouchersClicked = onFinish,
                onMyTripsClicked = onFinish,
                onProfileClicked = onFinish
            )

            Button(
                onClick = onFinish,
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Finish")
            }
        }
    }
}

@Composable
fun ConfirmationView(
    viewModel: BookingsViewModel,
    onHomeClicked: () -> Unit = {},
    onBookingsClicked: () -> Unit = {},
    onVouchersClicked: () -> Unit = {},
    onMyTripsClicked: () -> Unit = {},
    onProfileClicked: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val isWeekend = uiState.selectedDate?.dayOfWeek in listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)

    val displayName = uiState.selectedDate
        ?.dayOfWeek
        ?.name
        ?.lowercase()
        ?.replaceFirstChar { it.uppercase() }
        ?: ""
    val dateFormatted = uiState.selectedDate?.let {
        "${it.dayOfMonth} ${it.month.name.lowercase().replaceFirstChar { c -> c.uppercase() }} ${it.year}"
    } ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(backgroundColor)
    ) {
        Text(
            text = "Ticket",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Image(
            painter = painterResource(R.drawable.splashmania_pic),
            contentDescription = "SplashMania Logo",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 20.dp, bottom = 20.dp)
        )

        Image(
            painter = painterResource(R.drawable.qr_code),
            contentDescription = "QR Code",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Scan this QR code to enter",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
        ) {
            // Ticket type
            Text(
                text = "${uiState.selectedNationality ?: ""} Admission Ticket (${if(isWeekend) "Weekend" else "Weekday"})",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Date
            Text(
                text = "Date: $dateFormatted ($displayName)",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Ticket quantities and prices
            uiState.tickets.forEach {ticket ->
                if(ticket.quantity > 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${ticket.type} × ${ticket.quantity}")
                        Text("RM${(ticket.price * ticket.quantity).toInt()}")
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            // Voucher discount if applied
            uiState.appliedVoucher?.let { voucher ->
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(voucher.code, fontWeight = FontWeight.Bold)
                    Text("-RM${voucher.discount.toInt()}")
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // Total
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "TOTAL",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "RM${uiState.finalAmount.toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Payment Method
            Text(
                text = "Payment Method: ${uiState.paymentMethod}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BottomNavBarWithIcons(
            onHomeClicked = onHomeClicked,
            onBookingsClicked = onBookingsClicked,
            onVouchersClicked = onVouchersClicked,
            onMyTripsClicked = onMyTripsClicked,
            onProfileClicked = onProfileClicked,
            currentScreen = "MY TRIPS"
        )
    }
}

@Composable
fun BookingDetailsScreen(
    booking: Booking,
    onCancelBooking: (Int) -> Unit
) {
    val localDateTime = Instant.fromEpochMilliseconds(booking.date)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val formattedDate = "${localDateTime.dayOfMonth} " +
            "${localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }} " +
            "${localDateTime.year}"

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Booking ID: ${booking.id}")
        Text(text = "Date: $formattedDate")
        Text(text = "Package: ${booking.packageType}")
        Text(text = "Nationality: ${booking.nationality}")
        Text(text = "Adults: ${booking.adults}")
        Text(text = "Children: ${booking.children}")
        Text(text = "Seniors: ${booking.seniors}")
        Text(text = "Total Price: RM${booking.totalPrice}")
        Text(text = "Status: ${booking.status}")
        booking.voucherUsed?.let {
            Text(text = "Voucher Used: $it")
        }
        Text(text = "Payment Method: ${booking.paymentMethod}")
        Button(
            onClick = { onCancelBooking(booking.id) },
            enabled = booking.status.lowercase() == "upcoming" // only allow cancel if upcoming
        ) {
            Text("Cancel Booking")
        }
    }
}

@Composable
fun BookingsHistoryScreen(
    bookings: List<Booking>,
    onBookingClicked: (Booking) -> Unit
) {
    LazyColumn {
        items(bookings) { booking ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBookingClicked(booking) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Booking #${booking.id} - ${booking.status}")
                    Text("Date: ${booking.date}")
                    Text("Total: RM${booking.totalPrice.toInt()}")
                }

            }
        }
    }
}