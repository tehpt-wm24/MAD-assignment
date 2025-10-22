package com.example.splashmaniaapp.ui.screen.mytrips

data class MyTripsUiState(
    val isLoading: Boolean = true,
    val isCancelling: Boolean = false,
    val trips: List<Trip> = emptyList(),
    val selectedStatus: TripStatus = TripStatus.UPCOMING,
    val cancelledTripId: Int? = null,
    val errorMessage: String? = null,
    val cancelErrorMessage: String? = null,
    val upcomingCount: Int = 0,
    val completedCount: Int = 0,
    val cancelledCount: Int = 0
)

data class Trip(
    val id: Int,
    val title: String,
    val date: String,
    val ticketType: String,
    val adults: Int,
    val children: Int,
    val seniors: Int,
    val status: TripStatus,
    val totalPrice: Double = 0.0
)

enum class TripStatus {
    UPCOMING,
    COMPLETED,
    CANCELLED
}