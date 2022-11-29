package com.example.jim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jim.ui.components.ExerciseListItem
import com.example.jim.ui.components.WorkoutListItem
import com.example.jim.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeScreenViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    val sortedList by remember(true) { derivedStateOf { state.workouts.sortedBy { it.date } } }

    LaunchedEffect(true) {
        val loadingTodos = async {viewModel.getTodos()}
        delay(2000)
        loadingTodos.await()
        state.loading = false
    }

    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))

        } else {
            //Recent Workouts
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Recent Workout Logs")
            }
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)) {
                items(sortedList, key = {it.id!!}) { workout ->
                    WorkoutListItem(
                        workout = workout,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            //Workout Durations

        }
    }
}