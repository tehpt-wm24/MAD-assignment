package com.example.splashmaniaapp.data.repository

import com.example.splashmaniaapp.data.dao.BookingDao
import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.ui.screen.mytrips.Trip
import com.example.splashmaniaapp.ui.screen.mytrips.TripStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class OfflineBookingsRepository(private val bookingDao: BookingDao) : BookingsRepository {
    override suspend fun insert(booking: Booking) = bookingDao.insert(booking)

    override fun getBookings(): Flow<List<Booking>> = bookingDao.getBookings()

    override suspend fun cancelBooking(bookingId: Int) = bookingDao.cancelBooking(bookingId)

    override fun getUserBookings(userId: Int): Flow<List<Booking>> = bookingDao.getUserBookings(userId)

    override fun getUserBookingsByStatus(userId: Int, status: String): Flow<List<Booking>> {
        return bookingDao.getUserBookingsByStatus(userId, status)
    }

    override fun getBookingById(bookingId: Int): Flow<Booking?> = bookingDao.getBookingById(bookingId)

    override suspend fun updateBookingStatus(bookingId: Int, status: String) = bookingDao.updateBookingStatus(bookingId, status)

    override suspend fun deleteBooking(bookingId: Int) = bookingDao.deleteBooking(bookingId)

    override fun getUpcomingBookingsCount(userId: Int): Flow<Int> = bookingDao.getUpcomingBookingsCount(userId)

    override fun getTotalSpent(userId: Int): Flow<Double> = bookingDao.getTotalSpent(userId)
}