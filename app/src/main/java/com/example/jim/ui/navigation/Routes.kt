package com.example.jim.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val home = Screen("home")
    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val exercises = Screen("exercises")
    val editExercise = Screen("editexercise?id={id}")
    val splashScreen = Screen("splashscreen")
}