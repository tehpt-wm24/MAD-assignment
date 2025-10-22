package com.example.splashmaniaapp.ui.navigation

import androidx.navigation.NavHostController

interface NavigationDestination {
    val route: String
    val title: String
}

// Auth destinations
object LoginDestination : NavigationDestination {
    override val route = "login"
    override val title = "Login"
}

object SignUpDestination : NavigationDestination {
    override val route = "sign_up"
    override val title = "Sign Up"
}

object ForgotPasswordDestination : NavigationDestination {
    override val route = "forgot_password"
    override val title = "Forgot Password"
}

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val title = "HOME"
}

object BookingsDestination : NavigationDestination {
    override val route = "bookings"
    override val title = "BOOKINGS"
}

object VouchersDestination : NavigationDestination {
    override val route = "vouchers"
    override val title = "VOUCHERS"
}

object MyTripsDestination : NavigationDestination {
    override val route = "my_trips"
    override val title = "MY TRIPS"
}

object ProfileDestination : NavigationDestination {
    override val route = "profile"
    override val title = "PROFILE"
}

// Home destination
object AboutSplashManiaDestination : NavigationDestination {
    override val route = "home/about"
    override val title = "About SplashMania"
}

object ContactUsDestination : NavigationDestination {
    override val route = "home/contact"
    override val title = "Contact Us"
}

object SettingsDestination : NavigationDestination {
    override val route = "home/settings"
    override val title = "Settings"
}

object ProfileUpdateDestination : NavigationDestination {
    override val route = "home/profile_update"
    override val title = "Profile Update"
}

object ChangeUsernameDestination : NavigationDestination {
    override val route = "home/change_username"
    override val title = "Change Username"
}

object ChangeEmailDestination : NavigationDestination {
    override val route = "home/change_email"
    override val title = "Change Email"
}

object AccountSecurityDestination : NavigationDestination {
    override val route = "home/security"
    override val title = "Account Security"
}

object ChangePasswordDestination : NavigationDestination {
    override val route = "home/change_password"
    override val title = "Change Password"
}

object DeleteAccountDestination : NavigationDestination {
    override val route = "home/delete_account"
    override val title = "Delete Account"
}

// Booking destination
object BookingDateDestination : NavigationDestination {
    override val route = "booking/date"
    override val title = "Select Date"
}

object NationalitySelectionDestination : NavigationDestination {
    override val route = "booking/nationality"
    override val title = "Select Nationality"
}

object TicketQuantityDestination : NavigationDestination {
    override val route = "booking/quantity"
    override val title = "Ticket Quantity"

    fun createRoute(date: String, nationality: String) =
        "booking/quantity/$date/$nationality"
}

object PaymentDestination : NavigationDestination {
    override val route = "booking/payment"
    override val title = "Payment"
}

object VoucherSelectionDestination : NavigationDestination {
    override val route = "booking/voucher"
    override val title = "Apply Voucher"
}

object TicketDestination : NavigationDestination {
    override val route = "booking/ticket"
    override val title = "Ticket"
}

object BookingDetailsDestination : NavigationDestination {
    override val route = "booking/details/{bookingId}"
    override val title = "Booking Details"

    fun createRoute(bookingId: Int) = "booking/details/$bookingId"
}

// Voucher destination
object VoucherDetailsDestination : NavigationDestination {
    override val route = "voucher_details/{voucherId}"
    override val title = "Voucher Details"

    fun createRoute(voucherId: Int) = "voucher_details/$voucherId"
}

object UpcomingDestination : NavigationDestination {
    override val route = "my_trips/upcoming"
    override val title = "Upcoming"
}

object CompletedDestination : NavigationDestination {
    override val route = "my_trips/completed"
    override val title = "Completed"
}

object CancelledDestination : NavigationDestination {
    override val route = "my_trips/cancelled"
    override val title = "Cancelled"
}

object MyVouchersDestination : NavigationDestination {
    override val route = "profile/my_vouchers"
    override val title = "My Vouchers"
}

object PaymentDetailsDestination : NavigationDestination {
    override val route = "profile/payment_details"
    override val title = "Payment Details"
}

object ChangePhoneNumberDestination : NavigationDestination {
    override val route = "profile/change_phone"
    override val title = "Change Phone Number"
}

object ChangeAccountNumberDestination : NavigationDestination {
    override val route = "profile/change_account"
    override val title = "Change Account Number"
}

fun NavHostController.navigateToBookingDetails(bookingId: Int) {
    this.navigate(BookingDetailsDestination.createRoute(bookingId))
}

fun NavHostController.navigateToVoucherDetails(voucherId: Int) {
    this.navigate(VoucherDetailsDestination.createRoute(voucherId))
}

fun NavHostController.navigateAndClearBackStack(destination: String) {
    navigate(destination) {
        popUpTo(0)
    }
}