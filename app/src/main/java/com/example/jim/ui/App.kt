package com.example.jim.ui

import androidx.compose.runtime.Composable
import com.example.jim.ui.navigation.RootNavigation
import com.example.jim.ui.theme.JimTheme

@Composable
fun App() {
    JimTheme() {
        RootNavigation()
    }
}