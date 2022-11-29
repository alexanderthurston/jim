package com.example.jim.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Set
import com.example.jim.ui.models.Workout
import com.example.jim.ui.repositories.ExercisesRepository
import com.example.jim.ui.repositories.SetsRepository
import com.example.jim.ui.repositories.WorkoutsRepository
import java.time.LocalDate
import kotlin.reflect.typeOf

class WorkoutSessionScreenState {
    val workoutSaveSuccess by mutableStateOf(false)
    val start by mutableStateOf(System.currentTimeMillis()/1000)
    val end by mutableStateOf(0)
    val _exercises = mutableStateListOf<Exercise>()
    val exercises: List<Exercise> get() = _exercises
    val _sets = mutableStateListOf<Set>()
    val sets: List<Set> get() = _sets
    var newExerciseName by mutableStateOf("")
//    var newSetWeight by mutableStateOf(0)
//    var newSetReps by mutableStateOf(0)

}

class WorkoutSessionViewModel(application: Application): AndroidViewModel(application) {
    val uiState = WorkoutSessionScreenState()

    suspend fun addExercise() {
        val exercise = ExercisesRepository.createExercise(uiState.newExerciseName)
        uiState._exercises.add(exercise)
        uiState.newExerciseName = ""
    }

    suspend fun addSet(reps: Int, weight: Int, exerciseId: String) {
        val set = SetsRepository.createSet(reps, weight, exerciseId)
        uiState._sets.add(set)
    }

    suspend fun addWorkout() {
        val workout = WorkoutsRepository.createWorkout((((System.currentTimeMillis()/1000) - uiState.start)/60).toInt(), LocalDate.now().toString())
//        if(workout::class.simpleName == "Workout"){
//            uiState.workoutSaveSuccess = true
//        } else {
//
//        }

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