package com.example.jim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jim.ui.components.WorkoutListItem
import com.example.jim.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeScreenViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    val sortedList by remember(true) { derivedStateOf { state.workouts.sortedBy { it.date } } }

    LaunchedEffect(true) {
        val loadingWorkouts = async {viewModel.getWorkoutsExercisesAndSets()}
        delay(2000)
//        println(state.loading)
        loadingWorkouts.await()
        viewModel.calculateDurations()
//        println(state.loading)
        state.loading = false
//        println(state.loading)
    }

    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))

        } else {
            //Recent Workouts
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(text = "Recent Workout Logs", style=MaterialTheme.typography.h4)
            }

            //Workout Durations
            Spacer(modifier = Modifier.height(16.dp))
            Column(){
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "Last week you worked out for ${state.weekly_duration} minutes!", style=MaterialTheme.typography.subtitle1)
                }
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Text(text = "Last month you worked out for ${state.weekly_duration} minutes!", style=MaterialTheme.typography.subtitle1)
                }
            }

            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)) {
                items(sortedList, key = {it.id!!}) { workout ->
                    WorkoutListItem(
                        workout = workout,
                        exercises = state.exercises.filter { it.workoutId == workout.id },
                        sets = state.sets.filter { it.workoutId == workout.id }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


        }
    }
}