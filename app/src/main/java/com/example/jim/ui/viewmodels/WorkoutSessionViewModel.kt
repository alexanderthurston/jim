package com.example.jim.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Set
import com.example.jim.ui.repositories.ExercisesRepository
import com.example.jim.ui.repositories.SetsRepository

class WorkoutSessionScreenState {
    val workoutSaveSuccess by mutableStateOf(false)
    val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises
    val _sets = mutableStateListOf<Set>()
    val sets: List<Set> get() = _sets
    var newExerciseName by mutableStateOf("")

}

class WorkoutSessionViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutSessionScreenState()

    suspend fun addExercise() {
        val exercise = ExercisesRepository.createExercise(uiState.newExerciseName)
        uiState._exercises.add(exercise)
        uiState.newExerciseName = ""
    }

    suspend fun addSet(weight: Float, reps: Int, exerciseId: String) {
        val set = SetsRepository.createSet(reps, weight, exerciseId)
        uiState._sets.add(set)
        uiState.newExerciseName = ""
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