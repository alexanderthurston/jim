package com.example.jim.ui.viewmodels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.repositories.UserRepository

class RootNavigationViewModel(application: Application): AndroidViewModel(application) {
    fun signOutUser() {
        UserRepository.signOutUser()
    }
}