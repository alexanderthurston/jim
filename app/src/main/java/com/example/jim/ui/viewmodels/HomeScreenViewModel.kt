package com.example.jim.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.models.Workout
import com.example.jim.ui.repositories.WorkoutsRepository

class HomeScreenState {
    val _workouts = mutableStateListOf<Workout>()
    val workouts: SnapshotStateList<Workout> get() = _workouts
    var showRecentWorkoutsFirst by mutableStateOf(false)
    var loading by mutableStateOf(true)
}

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = HomeScreenState()
    suspend fun getTodos() {
        val workouts = WorkoutsRepository.getWorkouts()
        uiState._workouts.clear()
        uiState._workouts.addAll(workouts)
    }

//    suspend fun toggleTodoCompletion(exercise: Exercise) {
//        val todoCopy = todo.copy(completed = !(todo.completed ?: true))
//        // optimistically update the ui
//        uiState._todos[uiState._todos.indexOf(todo)] = todoCopy
//
//        // go to firebase
//        TodosRepository.updateTodo(todoCopy)
//    }
}