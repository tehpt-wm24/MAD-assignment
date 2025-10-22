package com.example.splashmaniaapp.ui.screen.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.splashmaniaapp.ui.navigation.navigateToBookingDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

class BookingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BookingsUiState())
    val uiState: StateFlow<BookingsUiState> = _uiState.asStateFlow()

    // ✅ 四个 package 的价格表
    private val pricePackages = mapOf(
        "Malaysian-Weekday" to mapOf(
            "Adult" to 110.0,
            "Child" to 90.0,
            "Senior" to 90.0
        ),
        "Malaysian-Weekend" to mapOf(
            "Adult" to 120.0,
            "Child" to 100.0,
            "Senior" to 100.0
        ),
        "Non-Malaysian-Weekday" to mapOf(
            "Adult" to 140.0,
            "Child" to 120.0,
            "Senior" to 120.0
        ),
        "Non-Malaysian-Weekend" to mapOf(
            "Adult" to 150.0,
            "Child" to 130.0,
            "Senior" to 130.0
        )
    )

    private val availableVouchers = listOf(
        AppliedVoucher(
            id = "voucher_1",
            code = "RM20OFF",
            discount = 20.0,
            minSpend = 250.0
        )
    )

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    tickets = listOf(
                        BookingTicket(type = "Adult", price = 0.0, quantity = 0),
                        BookingTicket(type = "Child", price = 0.0, quantity = 0),
                        BookingTicket(type = "Senior", price = 0.0, quantity = 0)
                    )
                )
            }
        }
    }

    fun selectDate(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        checkAndRefreshPrices()
    }

    fun selectNationality(nationality: String) {
        _uiState.update { it.copy(selectedNationality = nationality) }
        checkAndRefreshPrices()
    }

    private fun checkAndRefreshPrices() {
        val state = _uiState.value
        val nationality = state.selectedNationality
        val date = state.selectedDate

        if (nationality != null && date != null) {
            refreshTicketPrices(nationality, date)
        } else {
            println("DEBUG -> nationality=$nationality, date=$date")
        }
    }

    private fun refreshTicketPrices(nationality: String, date: LocalDate) {
        val isWeekend = (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY)
        val newPrices = getPricesForNationalityAndDay(nationality, isWeekend)

        println("DEBUG -> $nationality $date (${if (isWeekend) "Weekend" else "Weekday"})")

        _uiState.update { currentState ->
            val updatedTickets = currentState.tickets.map { ticket ->
                ticket.copy(price = newPrices[ticket.type] ?: 0.0)
            }
            currentState.copy(tickets = updatedTickets)
        }
    }

    private fun getPricesForNationalityAndDay(
        nationality: String,
        isWeekend: Boolean
    ): Map<String, Double> {
        val key = when {
            nationality == "Malaysian" && !isWeekend -> "Malaysian-Weekday"
            nationality == "Malaysian" && isWeekend -> "Malaysian-Weekend"
            nationality == "Non-Malaysian" && !isWeekend -> "Non-Malaysian-Weekday"
            nationality == "Non-Malaysian" && isWeekend -> "Non-Malaysian-Weekend"
            else -> "Malaysian-Weekday"
        }
        return pricePackages[key] ?: emptyMap()
    }

    fun updateTicketQuantity(ticketType: String, quantity: Int) {
        _uiState.update { currentState ->
            val updatedTickets = currentState.tickets.map { ticket ->
                if (ticket.type == ticketType) ticket.copy(quantity = quantity) else ticket
            }
            val total = calculateTotal(updatedTickets)
            val discount = currentState.appliedVoucher?.let {
                if (total >= it.minSpend) it.discount else 0.0
            } ?: 0.0
            currentState.copy(
                tickets = updatedTickets,
                totalAmount = total,
                finalAmount = total - discount
            )
        }
    }

    private fun calculateTotal(tickets: List<BookingTicket>): Double {
        return tickets.sumOf { it.price * it.quantity }
    }

    fun selectVoucher(voucher: AppliedVoucher) {
        _uiState.update { currentState ->
            val discount = if (currentState.totalAmount >= voucher.minSpend) voucher.discount else 0.0
            val finalAmount = currentState.totalAmount - discount
            currentState.copy(
                appliedVoucher = voucher,
                finalAmount = finalAmount
            )
        }
    }

    fun setPaymentMethod(method: String) {
        _uiState.update { currentState ->
            currentState.copy(paymentMethod = method)
        }
    }

    fun onBookingSelected(bookingId: Int, navController: NavHostController) {
        navController.navigateToBookingDetails(bookingId)
    }

    fun confirmPayment() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newBooking = Booking(
                    id = currentState.bookings.size + 1,
                    date = currentState.selectedDate ?: LocalDate(2025, 1, 1),
                    status = "Upcoming",
                    tickets = currentState.tickets,
                    totalAmount = currentState.totalAmount,
                    appliedVoucher = currentState.appliedVoucher
                )

                currentState.copy(
                    bookingConfirmed = true,
                    qrCodeData = "QR_CODE_${System.currentTimeMillis()}",
                    bookings = currentState.bookings + newBooking
                )
            }
        }
    }

    fun getAvailableVouchers(): List<AppliedVoucher> = availableVouchers

    fun cancelBooking(bookingId: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    bookings = currentState.bookings.map { booking ->
                        if (booking.id == bookingId) booking.copy(status = "Cancelled") else booking
                    }
                )
            }
        }
    }
}