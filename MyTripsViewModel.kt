package com.example.splashmaniaapp.ui.screen.mytrips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.data.repository.BookingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MyTripsViewModel(
    private val bookingsRepository: BookingsRepository,
    private val userId: Int = 1
): ViewModel() {

    private val dummyTrips = listOf(
        Trip(
            id = 1,
            title = "[Weekend / Public Holiday] Admission Ticket",
            date = "6th September 2025",
            ticketType = "Weekend",
            adults = 2,
            children = 0,
            seniors = 1,
            status = TripStatus.COMPLETED,
            totalPrice = 340.0
        ),
        Trip(
            id = 2,
            title = "[Weekday] Admission Ticket",
            date = "19th March 2025",
            ticketType = "Weekday",
            adults = 2,
            children = 0,
            seniors = 0,
            status = TripStatus.COMPLETED,
            totalPrice = 220.0
        ),
        Trip(
            id = 3,
            title = "[Weekday] Admission Ticket",
            date = "15th August 2025",
            ticketType = "Weekday",
            adults = 1,
            children = 0,
            seniors = 1,
            status = TripStatus.CANCELLED,
            totalPrice = 260.0
        )
    )

    private val _uiState = MutableStateFlow(
        MyTripsUiState(
            trips = dummyTrips,
            selectedStatus = TripStatus.UPCOMING
        )
    )
    val uiState: StateFlow<MyTripsUiState> = _uiState

    init {
        loadBookingsByStatus(TripStatus.UPCOMING)
    }

    private fun loadBookingsByStatus(status: TripStatus) {
        viewModelScope.launch {
            when (status) {
                TripStatus.UPCOMING -> {
                    bookingsRepository.getUserBookingsByStatus(userId, "upcoming").collect { bookings ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                selectedStatus = status,
                                trips = bookings.map { it.toTrip() },
                                upcomingCount = bookings.size,
                                completedCount = dummyTrips.count { it.status == TripStatus.COMPLETED },
                                cancelledCount = dummyTrips.count { it.status == TripStatus.CANCELLED }
                            )
                        }
                    }
                }
                else -> {
                    val filteredTrips = dummyTrips.filter { it.status == status }
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            selectedStatus = status,
                            trips = filteredTrips,
                            upcomingCount = currentState.upcomingCount,
                            completedCount = dummyTrips.count { it.status == TripStatus.COMPLETED },
                            cancelledCount = dummyTrips.count { it.status == TripStatus.CANCELLED }
                        )
                    }
                }
            }
        }
    }


    fun changeTripStatus(status: TripStatus) {
        loadBookingsByStatus(status)
    }

    fun cancelTrip(bookingId: Int) {
        viewModelScope.launch {
            try {
                bookingsRepository.cancelBooking(bookingId)

                val cancelledBooking = bookingsRepository.getBookingById(bookingId).firstOrNull()

                if (cancelledBooking != null) {
                    val cancelledTrip = cancelledBooking.toTrip().copy(status = TripStatus.CANCELLED)

                    _uiState.update { currentState ->
                        val updatedTrips = currentState.trips.mapNotNull { trip ->
                            if (trip.id == bookingId) null else trip
                        } + cancelledTrip

                        currentState.copy(trips = updatedTrips)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(cancelErrorMessage = e.message ?: "Failed to cancel trip")
                }
            }
        }
    }

    private fun Booking.toTrip(): Trip {
        return Trip(
            id = id,
            title = when (packageType.lowercase()) {
                "weekday" -> "[Weekday] Admission Ticket"
                "weekend" -> "[Weekend / Public Holiday] Admission Ticket"
                else -> "[${packageType.replaceFirstChar { it.uppercase() }}] Admission Ticket"
            },
            date = formatDate(date),
            ticketType = packageType,
            adults = adults,
            children = children,
            seniors = seniors,
            status = when (status.lowercase()) {
                "upcoming" -> TripStatus.UPCOMING
                "completed" -> TripStatus.COMPLETED
                "cancelled" -> TripStatus.CANCELLED
                else -> TripStatus.UPCOMING
            },
            totalPrice = totalPrice
        )
    }

    private fun formatDate(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val day = localDateTime.dayOfMonth
        val suffix = getDaySuffix(day)

        val month = localDateTime.month.name.lowercase()
            .replaceFirstChar { it.uppercase() }

        return "$day$suffix $month ${localDateTime.year}"
    }

    private fun getDaySuffix(day: Int): String {
        return if (day in 11..13) {
            "th"
        } else {
            when (day % 10) {
                1 -> "st"
                2 -> "nd"
                3 -> "rd"
                else -> "th"
            }
        }
    }

    fun onValueChange(newUiState: MyTripsUiState) {
        _uiState.update { newUiState }
    }
}