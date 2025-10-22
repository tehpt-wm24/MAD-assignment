package com.example.splashmaniaapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.splashmaniaapp.data.entity.Booking
import com.example.splashmaniaapp.ui.screen.mytrips.Trip
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(booking: Booking)

    @Query("SELECT * FROM bookings")
    fun getBookings(): Flow<List<Booking>>

    @Query("UPDATE bookings SET status = 'cancelled' WHERE id = :bookingId")
    suspend fun cancelBooking(bookingId: Int)

    @Query("SELECT * FROM bookings WHERE user_id = :userId ORDER BY date DESC")
    fun getUserBookings(userId: Int): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE user_id = :userId AND status = :status ORDER BY date DESC")
    fun getUserBookingsByStatus(userId: Int, status: String): Flow<List<Booking>>

    @Query("SELECT * FROM bookings WHERE id = :bookingId")
    fun getBookingById(bookingId: Int): Flow<Booking?>

    @Query("UPDATE bookings SET status = :status WHERE id = :bookingId")
    suspend fun updateBookingStatus(bookingId: Int, status: String)

    @Query("DELETE FROM bookings WHERE id = :bookingId")
    suspend fun deleteBooking(bookingId: Int)

    @Query("SELECT COUNT(*) FROM bookings WHERE user_id = :userId AND status = 'upcoming'")
    fun getUpcomingBookingsCount(userId: Int): Flow<Int>

    @Query("SELECT SUM(total_price) FROM bookings WHERE user_id = :userId AND status = 'completed'")
    fun getTotalSpent(userId: Int): Flow<Double>
}