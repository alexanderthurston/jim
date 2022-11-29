package com.example.jim.ui.navigation

data class Screen(val route: String)

object Routes {
    val launchNavigation = Screen("launchnavigation")
    val homeNavigation = Screen("homenavigation")
    val launch = Screen("launch")
    val signIn = Screen("signin")
    val signUp = Screen("signup")
    val home = Screen("home")
    val workoutSession = Screen("workoutsession?id={id}")
    val addExercise = Screen("addexercise")
    val splashScreen = Screen("splashscreen")
}