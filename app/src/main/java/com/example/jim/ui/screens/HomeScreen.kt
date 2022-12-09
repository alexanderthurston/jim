package com.example.jim.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jim.ui.components.FormField
import com.example.jim.ui.components.WorkoutListItem
import com.example.jim.ui.viewmodels.HomeScreenViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeScreenViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    val sortedList by remember(true) { derivedStateOf { state.workouts.sortedBy { it.date } } }

    LaunchedEffect(true) {
        val loadingWorkouts = async { viewModel.getWorkoutsExercisesAndSets() }
        delay(2000)
        loadingWorkouts.await()
        viewModel.calculateDurations()
        viewModel.userProfileExists()
        state.loading = false
    }

    Column {
        if (state.loading) {
            Spacer(modifier = Modifier.height(16.dp))

        } else if (!state.userProfileExists) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Please enter your name and birthday",
                    style = MaterialTheme.typography.h4
                )
            }

            Column {
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Name")
                    Spacer(modifier = Modifier.width(4.dp))
                    FormField(
                        value = "$name",
                        onValueChange = { name = it },
                        placeholder = { Text(text = "Enter name") }
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Birthday")
                    Spacer(modifier = Modifier.width(4.dp))
                    FormField(
                        value = "$birthday",
                        onValueChange = { birthday = it },
                        placeholder = { Text(text = "Enter birthday YYYY-MM-DD") }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        scope.launch {
                            state.userProfileExists = viewModel.createUserProfile(name, birthday)
                        }
                    }) {
                        Text(text = "Add info")
                    }
                }
            }
        } else {

            //put ad here
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    AdView(context).apply{
                        setAdSize(AdSize.BANNER)
                        adUnitId = "ca-app-pub-3940256099942544/6300978111"
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )
            //Recent Workouts
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Recent Workout Logs", style = MaterialTheme.typography.h4)
            }

            //Workout Durations
            Spacer(modifier = Modifier.height(16.dp))
            Column() {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Last week you worked out for ${state.weekly_duration} minutes!",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Last month you worked out for ${state.monthly_duration} minutes!",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(sortedList, key = { it.id!! }) { workout ->
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