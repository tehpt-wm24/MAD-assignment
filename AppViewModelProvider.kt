package com.example.splashmaniaapp.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.splashmaniaapp.SplashManiaApplication
import com.example.splashmaniaapp.ui.screen.bookings.BookingsViewModel
import com.example.splashmaniaapp.ui.screen.home.HomeViewModel
import com.example.splashmaniaapp.ui.screen.login.LoginViewModel
import com.example.splashmaniaapp.ui.screen.mytrips.MyTripsViewModel
import com.example.splashmaniaapp.ui.screen.profile.ProfileViewModel
import com.example.splashmaniaapp.ui.screen.vouchers.VouchersViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Auth ViewModels
        initializer {
            LoginViewModel()
        }

        // Main Navigation ViewModels
        initializer {
            HomeViewModel()
        }
        initializer {
            BookingsViewModel()
        }
        initializer {
            VouchersViewModel()
        }
        initializer {
            val application = this.splashManiaApplication()
            MyTripsViewModel(this.splashManiaApplication().container.bookingsRepository, userId = 1)
        }
        initializer {
            ProfileViewModel()
        }
    }
}

fun CreationExtras.splashManiaApplication(): SplashManiaApplication {
    return (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as? SplashManiaApplication)
        ?: throw IllegalStateException("Application is not SplashManiaApplication")
}