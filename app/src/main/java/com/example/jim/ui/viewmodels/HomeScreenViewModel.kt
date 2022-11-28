package com.example.jim.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.repositories.ExercisesRepository

class ExercisesScreenState {
    val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises
    var showHigherPriorityItemsFirst by mutableStateOf(false)
    var loading by mutableStateOf(true)
}

class HomeScreenViewModel(application: Application): AndroidViewModel(application) {
    val uiState = ExercisesScreenState()
    suspend fun getTodos() {
        val exercises = ExercisesRepository.getExercises()
        uiState._exercises.clear()
        uiState._exercises.addAll(exercises)
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