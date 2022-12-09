package com.example.jim.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Workout
import com.example.jim.ui.models.Set
import com.example.jim.ui.repositories.ExercisesRepository
import com.example.jim.ui.repositories.SetsRepository
import com.example.jim.ui.repositories.UserRepository
import com.example.jim.ui.repositories.WorkoutsRepository
import java.time.LocalDate

class HomeScreenState {
    val _workouts = mutableStateListOf<Workout>()
    val workouts: List<Workout> get() = _workouts
    val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises
    val _sets = mutableStateListOf<Set>()
    val sets: List<Set> get() = _sets
    var userProfileExists by mutableStateOf(false)
    var loading by mutableStateOf(true)
    var weekly_duration by mutableStateOf(0)
    var monthly_duration by mutableStateOf(0)
}

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {
    val uiState = HomeScreenState()
    suspend fun getWorkoutsExercisesAndSets() {
        val workouts = WorkoutsRepository.getUserWorkouts()
        val exercises = ExercisesRepository.getUserExercises()
        val sets = SetsRepository.getUserSets()
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
        uiState._exercises.clear()
        uiState._exercises.addAll(exercises)
        uiState._sets.clear()
        uiState._sets.addAll(sets)
    }

    suspend fun createUserProfile(name: String, birthday: String): Boolean {
        return try {
            LocalDate.parse(birthday)
            println("Birthday done")
            UserRepository.createUserProfile(name, birthday)
            println("Done did it")
            true
        } catch (e: Exception) {
            println("Excepted")
            false
        }
    }

    suspend fun userProfileExists() {
        uiState.userProfileExists = UserRepository.userProfileExists()
    }

    fun calculateDurations() {
        uiState.workouts.filter { workout ->
            LocalDate.parse(workout.date).isAfter(
                LocalDate.now()
                    .minusDays(7)
            )
        }.forEach { workout -> uiState.weekly_duration += workout.duration!! }

        uiState.workouts.filter { workout ->
            LocalDate.parse(workout.date).isAfter(
                LocalDate.now().minusMonths(1)
            )
        }.forEach { workout -> uiState.monthly_duration += workout.duration!! }
    }
}