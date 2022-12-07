package com.example.jim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jim.ui.components.ExerciseListItem
import com.example.jim.ui.components.FormField
import com.example.jim.ui.navigation.Routes
import com.example.jim.ui.viewmodels.WorkoutSessionViewModel
import kotlinx.coroutines.launch

@Composable
fun WorkoutSessionScreen(navHostController: NavHostController) {
    val viewModel: WorkoutSessionViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    if (state.workoutSaveSuccess) {
        navHostController.navigate(Routes.home.route) {
            popUpTo(0)
        }
    }
    Column {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Workout Session", style = MaterialTheme.typography.h4)
        }
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            FormField(
                value = state.newExerciseName,
                onValueChange = { state.newExerciseName = it },
                placeholder = { Text(text = "Exercise Name") }
            )
        }
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button(onClick = {
                scope.launch {
                    viewModel.addExercise()
                }
            }) {
                Text(text = "Add Exercise")
            }


            Spacer(modifier = Modifier.width(16.dp))


            Button(onClick = {
                scope.launch {
                    viewModel.addWorkout()
                }
            }) {
                Text(text = "Complete Workout")
            }

        }

        if (state.exercises.isNotEmpty()) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(state.exercises, key = { it.id!! }) { exercise ->
                    ExerciseListItem(
                        exercise = exercise,
                        sets = state.sets.filter { set -> set.exerciseId == exercise.id },
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


        }


    }
}