package com.example.jim.ui.navigation

import android.window.SplashScreen
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.jim.ui.screens.*
import com.example.jim.ui.viewmodels.RootNavigationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel: RootNavigationViewModel = viewModel()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                TopAppBar() {
                    if (currentDestination?.route == Routes.home.route) {
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu button")
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                    Text(text = "Home")
                }
            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.launchNavigation.route || it.route == Routes.splashScreen.route } == true) {
                DropdownMenuItem(onClick = {
                    // TODO Log the user out
                    viewModel.signOutUser()
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.launchNavigation.route) {
                        popUpTo(0) // clear back stack and basically start the app from scratch
                    }
                }) {
                    Icon(Icons.Default.ExitToApp, "Logout")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Logout")
                }
            }
        },
        floatingActionButton = {
            if (currentDestination?.route == Routes.home.route) {
                FloatingActionButton(onClick = {navController.navigate(Routes.workoutSession.route)}) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Start workout session")
                }
            }
//            else if (currentDestination?.route == Routes.workoutSession.route) {
//                FloatingActionButton(onClick = {navController.navigate(Routes.addExercise.route)}) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add exercise")
//                }
//            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.splashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(route = Routes.launchNavigation.route, startDestination = Routes.launch.route) {
                composable(route = Routes.launch.route) { LaunchScreen(navController) }
                composable(route = Routes.signIn.route) { SignInScreen(navController) }
                composable(route = Routes.signUp.route) { SignUpScreen(navController) }
            }
            navigation(route = Routes.homeNavigation.route, startDestination = Routes.home.route) {
//                composable(
//                    route = "editexercise?id={id}",
//                    arguments = listOf(navArgument("id") { defaultValue = "new" })
//                ) { navBackStackEntry ->
//                    WorkoutModificationScreen(navController, navBackStackEntry.arguments?.get("id").toString())
//                }
                composable(route = Routes.workoutSession.route) { WorkoutSessionScreen(navController) }
                composable(route = Routes.home.route) { HomeScreen(navController) }
            }
            composable(route = Routes.splashScreen.route) { SplashScreen(navController) }
        }
    }
}