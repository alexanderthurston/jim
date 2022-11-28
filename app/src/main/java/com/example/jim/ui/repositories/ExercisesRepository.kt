package com.example.jim.ui.repositories

import com.example.jim.ui.models.Exercise
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object ExercisesRepository {

    private val exercisesCache = mutableListOf<Exercise>()
    private var cacheInitialized = false

    suspend fun getExercises(workoutId: String): List<Exercise> {
        if (!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("exercises")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .whereEqualTo("workoutId", workoutId)
                .get()
                .await()
            exercisesCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }

        return exercisesCache

    }

    suspend fun createExercise(
        name: String
    ): Exercise {
        val doc = Firebase.firestore.collection("exercises").document()
        val exercise = Exercise(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            name = name
        )
        doc.set(exercise).await()
        exercisesCache.add(exercise)
        return exercise
    }

    suspend fun updateExercise(exercise: Exercise) {
        Firebase.firestore
            .collection("exercises")
            .document(exercise.id!!)
            .set(exercise)
            .await()

        val oldExerciseIndex = exercisesCache.indexOfFirst {
            it.id == exercise.id
        }
        exercisesCache[oldExerciseIndex] = exercise
    }
}
