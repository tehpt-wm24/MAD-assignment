package com.example.splashmaniaapp.ui.screen.bookings

import kotlinx.datetime.LocalDate
import kotlinx.datetime.DayOfWeek

data class BookingsUiState(
    val isLoading: Boolean = true,
    val selectedDate: LocalDate? = null,
    val selectedNationality: String? = null,
    val selectedTicket: String? = null,
    val selectedTicketPrice: Int = 0,
    val ticketQuantity: Int = 0,
    val isWeekend: Boolean = false,
    val tickets: List<BookingTicket> = emptyList(),
    val totalAmount: Double = 0.0,
    val appliedVoucher: AppliedVoucher? = null,
    val finalAmount: Double = 0.0,
    val paymentMethod: String = "",
    val bookingConfirmed: Boolean = false,
    val qrCodeData: String = "",
    val errorMessage: String? = null,
    val dayOfWeek: DayOfWeek? = null,
    val bookings: List<Booking> = emptyList(),
)

data class Booking(
    val id: Int,
    val date: LocalDate,
    val status: String, // "Upcoming", "Completed", "Cancelled"
    val tickets: List<BookingTicket> = emptyList(),
    val totalAmount: Double = 0.0,
    val appliedVoucher: AppliedVoucher? = null
)

data class BookingTicket(
    val type: String, // "Adult", "Child", "Senior"
    val price: Double = 0.0,
    val quantity: Int = 0
)

data class AppliedVoucher(
    val id: String,
    val code: String,
    val discount: Double,
    val minSpend: Double
)