package com.example.splashmaniaapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.splashmaniaapp.data.AuthManager
import com.example.splashmaniaapp.ui.screen.bookings.BookingDateScreen
import com.example.splashmaniaapp.ui.screen.bookings.BookingDetailsScreen
import com.example.splashmaniaapp.ui.screen.bookings.BookingsScreen
import com.example.splashmaniaapp.ui.screen.bookings.BookingsViewModel
import com.example.splashmaniaapp.ui.screen.bookings.NationalitySelectionScreen
import com.example.splashmaniaapp.ui.screen.bookings.PaymentScreen
import com.example.splashmaniaapp.ui.screen.bookings.TicketQuantityScreen
import com.example.splashmaniaapp.ui.screen.bookings.TicketScreen
import com.example.splashmaniaapp.ui.screen.bookings.VoucherSelectionScreen
import com.example.splashmaniaapp.ui.screen.home.AboutSplashManiaScreen
import com.example.splashmaniaapp.ui.screen.home.AccountSecurityScreen
import com.example.splashmaniaapp.ui.screen.home.ContactUsScreen
import com.example.splashmaniaapp.ui.screen.home.DeleteAccountDialog
import com.example.splashmaniaapp.ui.screen.home.HomeScreen
import com.example.splashmaniaapp.ui.screen.home.ChangeUsernameScreen
import com.example.splashmaniaapp.ui.screen.home.ChangeEmailScreen
import com.example.splashmaniaapp.ui.screen.home.SettingsScreen
import com.example.splashmaniaapp.ui.screen.login.ForgotPasswordScreen
import com.example.splashmaniaapp.ui.screen.login.LoginScreen
import com.example.splashmaniaapp.ui.screen.login.LoginViewModel
import com.example.splashmaniaapp.ui.screen.login.SignUpScreen
import com.example.splashmaniaapp.ui.screen.mytrips.CancelledScreen
import com.example.splashmaniaapp.ui.screen.mytrips.CompletedScreen
import com.example.splashmaniaapp.ui.screen.mytrips.MyTripsScreen
import com.example.splashmaniaapp.ui.screen.mytrips.UpcomingScreen
import com.example.splashmaniaapp.ui.screen.profile.ChangeAccountNumberScreen
import com.example.splashmaniaapp.ui.screen.profile.ChangePhoneNumberScreen
import com.example.splashmaniaapp.ui.screen.profile.MyVouchersScreen
import com.example.splashmaniaapp.ui.screen.profile.PaymentDetailsScreen
import com.example.splashmaniaapp.ui.screen.profile.PaymentMethod
import com.example.splashmaniaapp.ui.screen.profile.ProfileScreen
import com.example.splashmaniaapp.ui.screen.vouchers.VoucherDetailsScreen
import com.example.splashmaniaapp.ui.screen.vouchers.VouchersScreen
import com.example.splashmaniaapp.ui.screen.vouchers.VouchersViewModel

@Composable
fun SplashManiaAppNavHost(
    navController: NavHostController,
) {
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentUsername by remember { mutableStateOf("ping ting") }
    var currentEmail by remember { mutableStateOf("pingting060906@gmail.com") }
    var currentPassword by remember { mutableStateOf("123456") }
    var currentPhone by remember { mutableStateOf("+601111480378") }
    var currentAccount by remember { mutableStateOf("******9408")}

    NavHost(
        navController = navController,
        startDestination = LoginDestination.route
    ) {

        // Auth Screens
        composable(LoginDestination.route) {
            LoginScreen(
                onLoginSuccess = {
                    isLoggedIn = true
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true}
                    }
                },
                onSignUpClicked = { navController.navigate(SignUpDestination.route) },
                onForgotPasswordClicked = { navController.navigate(ForgotPasswordDestination.route) }
            )
        }

        composable(SignUpDestination.route) {
            val loginViewModel: LoginViewModel = viewModel()

            SignUpScreen(
                viewModel = loginViewModel,
                onSignUpButtonClicked = { email, password, username ->
                    AuthManager.signUp(email, password, username) // Save new user
                    isLoggedIn = true
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                },
                onSignInClicked = {
                    navController.navigate(LoginDestination.route) {
                        popUpTo(SignUpDestination.route) { inclusive = true }
                    }
                },
                onBackClicked = {
                    if(navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(HomeDestination.route)
                    }
                },
            )
        }

        composable(ForgotPasswordDestination.route) {
            val loginViewModel: LoginViewModel = viewModel()

            ForgotPasswordScreen(
                viewModel = loginViewModel,
                onCancelClicked = {
                    if(navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(HomeDestination.route)
                    }
                },
                onUpdateClicked = { newPassword ->
                    currentPassword = newPassword
                    isLoggedIn = true
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                },
                onBackClicked = {
                    if(navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(HomeDestination.route)
                    }
                }
            )
        }

        composable(HomeDestination.route) {
            HomeScreen(
                onCloseClicked = { navController.popBackStack() },
                onBookingClick = { id -> navController.navigateToBookingDetails(id) },
                onVoucherClick = { id -> navController.navigateToVoucherDetails(id) },
                onProfileClick = { navController.navigate("profile") },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) },
                onAboutClicked = { navController.navigate(AboutSplashManiaDestination.route) },
                onContactUsClicked = { navController.navigate(ContactUsDestination.route) },
                onSettingsClicked = { navController.navigate(SettingsDestination.route) }
            )
        }

        composable(BookingsDestination.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BookingsDestination.route)
            }
            val bookingsViewModel: BookingsViewModel = viewModel(parentEntry)

            BookingsScreen(
                viewModel = bookingsViewModel,
                onSelectDate = { navController.navigate(BookingDateDestination.route) },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(VouchersDestination.route) {backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(VouchersDestination.route)
            }
            val vouchersViewModel: VouchersViewModel = viewModel(parentEntry)

            VouchersScreen(
                viewModel = vouchersViewModel,
                onRedeemClicked = { id -> vouchersViewModel.redeemVoucher(id) },
                onVoucherClicked = { voucherId ->
                    navController.navigateToVoucherDetails(voucherId)
                },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) },
            )
        }

        composable(MyTripsDestination.route) {
            MyTripsScreen(
                onUpcomingClicked = { navController.navigate(UpcomingDestination.route) },
                onCompletedClicked = { navController.navigate(CompletedDestination.route) },
                onCancelledClicked = { navController.navigate(CancelledDestination.route) },
                onPlanButtonClicked = { navController.navigate(BookingsDestination.route) },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(ProfileDestination.route) {
            ProfileScreen(
                username = currentUsername,
                userEmail = currentEmail,
                onMyVouchersClicked = { navController.navigate(MyVouchersDestination.route) },
                onPaymentDetailsClicked = { navController.navigate(PaymentDetailsDestination.route) },
                onLogoutClicked = {
                    isLoggedIn = false
                    navController.navigate(HomeDestination.route)
                },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }



        // Home sub-screens
        composable(AboutSplashManiaDestination.route) {
            AboutSplashManiaScreen(
                onBackClicked = { navController.popBackStack() },
            )
        }

        composable(ContactUsDestination.route) {
            ContactUsScreen(
                onBackClicked = { navController.popBackStack() },
            )
        }

        composable(SettingsDestination.route) {
            SettingsScreen(
                username = currentUsername,
                email = currentEmail,
                onBackClicked = { navController.popBackStack() },
                onChangeUsernameClicked = { navController.navigate(ChangeUsernameDestination.route) },
                onChangeEmailClicked = { navController.navigate(ChangeEmailDestination.route) },
                onAccountSecurityClicked = { navController.navigate(ChangePasswordDestination.route) },
                onDeleteAccountClicked = { navController.navigate(DeleteAccountDestination.route) }
            )
        }

        composable(ChangeUsernameDestination.route) {
            ChangeUsernameScreen(
                onBackClicked = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() },
                onChangeUsernameClicked = { newUsername ->
                    currentUsername = newUsername
                    navController.popBackStack()
                }
            )
        }

        composable(ChangeEmailDestination.route) {
            ChangeEmailScreen(
                onBackClicked = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() },
                onChangeEmailClicked = { newEmail ->
                    currentEmail = newEmail
                    navController.popBackStack()
                }
            )
        }

        composable(ChangePasswordDestination.route) {
            AccountSecurityScreen(
                onBackClicked = { navController.popBackStack() },
                onCancelClicked = { navController.popBackStack() },
                onUpdateClicked = { newPassword, confirmPassword ->
                    if(newPassword == confirmPassword) {
                        currentPassword = newPassword
                        navController.popBackStack()
                    }
                }
            )
        }

        composable(DeleteAccountDestination.route) {
            DeleteAccountDialog(
                onDismiss = { navController.popBackStack() },
                onConfirmDelete = {
                    isLoggedIn = false
                    navController.popBackStack()
                    navController.navigate(LoginDestination.route)
                }
            )
        }

        // Bookings sub-screens
        composable(BookingDateDestination.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BookingsDestination.route)
            }
            val bookingsViewModel: BookingsViewModel = viewModel(parentEntry)

            BookingDateScreen(
                viewModel = bookingsViewModel,
                onNextClicked = { navController.navigate(NationalitySelectionDestination.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable(NationalitySelectionDestination.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BookingsDestination.route)
            }
            val bookingsViewModel: BookingsViewModel = viewModel(parentEntry)

            NationalitySelectionScreen(
                viewModel = bookingsViewModel,
                onNextClicked = { navController.navigate(TicketQuantityDestination.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable(TicketQuantityDestination.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BookingsDestination.route)
            }
            val bookingsViewModel: BookingsViewModel = viewModel(parentEntry)

            TicketQuantityScreen(
                viewModel = bookingsViewModel,
                onNextClicked = { navController.navigate(PaymentDestination.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable(PaymentDestination.route) {
            PaymentScreen(
                onApplyVoucher = { navController.navigate(VoucherSelectionDestination.route) },
                onPaymentDone = { navController.navigate(TicketDestination.route) },
                onBackClicked = { navController.popBackStack() }
            )
        }

        composable(VoucherSelectionDestination.route) {
            VoucherSelectionScreen(
                onApplyClicked = { navController.popBackStack() } // return to Payment
            )
        }

        composable(TicketDestination.route) {
            TicketScreen(
                onFinish = { navController.navigateAndClearBackStack(HomeDestination.route) }
            )
        }

        composable(
            route = BookingDetailsDestination.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.IntType })
        ) {
            backStackEntry ->
            val bookingId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            val viewModel: BookingsViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()
            val booking = uiState.bookings.find { it.id == bookingId }

            if (booking != null) {
                val entityBooking = com.example.splashmaniaapp.data.entity.Booking(
                    id = booking.id,
                    date = booking.date.toEpochDays().toLong(), // adapt LocalDate → Long millis
                    packageType = booking.status,
                    nationality = "", // map properly
                    adults = booking.tickets.find { it.type == "Adult" }?.quantity ?: 0,
                    children = booking.tickets.find { it.type == "Child" }?.quantity ?: 0,
                    seniors = booking.tickets.find { it.type == "Senior" }?.quantity ?: 0,
                    totalPrice = booking.totalAmount,
                    status = booking.status,
                    voucherUsed = booking.appliedVoucher?.code,
                    paymentMethod = ""
                )
                BookingDetailsScreen(
                    booking = entityBooking,
                    onCancelBooking = { id -> viewModel.cancelBooking(id) }
                )
            } else {
                Text("Booking not found")
            }
        }

        // Vouchers sub-screens
        composable(
            route = VoucherDetailsDestination.route,
            arguments = listOf(navArgument("voucherId") { type = NavType.IntType })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(VouchersDestination.route)
            }
            val voucherId = backStackEntry.arguments?.getInt("voucherId") ?: 0
            val vouchersViewModel: VouchersViewModel = viewModel(parentEntry)

            LaunchedEffect(voucherId) {
                vouchersViewModel.syncRedeemedStatus(voucherId)
            }

            val uiState by vouchersViewModel.uiState.collectAsState()
            val voucher = uiState.vouchers.find { it.id == voucherId }

            if (voucher != null) {
                VoucherDetailsScreen(
                    viewModel = vouchersViewModel,
                    voucherId = voucherId,
                    onBackClicked = { navController.popBackStack() }
                )
            } else {
                Text("Voucher not found")
            }
        }

        // MyTrips sub-screens
        composable(UpcomingDestination.route) {
            UpcomingScreen(
                onBackClicked = { navController.popBackStack() },
                onPlanButtonClicked = { navController.navigate(BookingsDestination.route) },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(CompletedDestination.route) {
            CompletedScreen(
                onBackClicked = { navController.popBackStack() },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(CancelledDestination.route) {
            CancelledScreen(
                onBackClicked = { navController.popBackStack() },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        // Profile sub-screens
        composable(MyVouchersDestination.route) {
            MyVouchersScreen(
                vouchers = emptyList(), // Pass actual vouchers from view model
                onBackClicked = { navController.popBackStack() },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(PaymentDetailsDestination.route) {
            PaymentDetailsScreen(
                paymentMethods = listOf(
                    PaymentMethod(
                        type = "Touch n Go",
                        isDefault = true,
                        details = currentPhone
                    ),
                    PaymentMethod(
                        type = "FPX Online Banking",
                        isDefault = false,
                        details = currentAccount
                    )
                ),
                onBackClicked = { navController.popBackStack() },
                onChangePhoneNumber = { navController.navigate(ChangePhoneNumberDestination.route) },
                onChangeAccountNumber = { navController.navigate(ChangeAccountNumberDestination.route) },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(ChangePhoneNumberDestination.route) {
            ChangePhoneNumberScreen(
                currentPhone = currentPhone,
                onBackClicked = { navController.popBackStack() },
                onUpdateClicked = { newPhone ->
                    currentPhone = newPhone
                    navController.popBackStack()
                },
                onCancelClicked = { navController.popBackStack() },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(ChangeAccountNumberDestination.route) {
            ChangeAccountNumberScreen(
                currentAccount = currentAccount,
                onBackClicked = { navController.popBackStack() },
                onUpdateClicked = { newAccount ->
                    currentAccount = newAccount
                    navController.popBackStack()
                },
                onCancelClicked = { navController.popBackStack() },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }

        composable(ProfileDestination.route) {
            ProfileScreen(
                username = currentUsername,
                userEmail = currentEmail,
                onMyVouchersClicked = { navController.navigate(MyVouchersDestination.route) },
                onPaymentDetailsClicked = { navController.navigate(PaymentDetailsDestination.route) },
                onLogoutClicked = {
                    isLoggedIn = false
                    navController.navigate(LoginDestination.route) {
                        popUpTo(ProfileDestination.route) { inclusive = true }
                    }
                },
                onHomeClicked = { navController.navigate(HomeDestination.route) },
                onBookingsClicked = { navController.navigate(BookingsDestination.route) },
                onVouchersClicked = { navController.navigate(VouchersDestination.route) },
                onMyTripsClicked = { navController.navigate(MyTripsDestination.route) },
                onProfileClicked = { navController.navigate(ProfileDestination.route) }
            )
        }
    }
}