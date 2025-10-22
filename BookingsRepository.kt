package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.ui.screen.mytrips.Trip
import kotlinx.coroutines.flow.Flow

interface BookingsRepository {
    suspend fun insert(booking: Booking)

    fun getBookings(): Flow<List<Booking>>

    suspend fun cancelBooking(bookingId: Int)

    fun getUserBookings(userId: Int): Flow<List<Booking>>

    fun getUserBookingsByStatus(userId: Int, status: String): Flow<List<Booking>>

    fun getBookingById(bookingId: Int): Flow<Booking?>

    suspend fun updateBookingStatus(bookingId: Int, status: String)

    suspend fun deleteBooking(bookingId: Int)

    fun getUpcomingBookingsCount(userId: Int): Flow<Int>

    fun getTotalSpent(userId: Int): Flow<Double>
}