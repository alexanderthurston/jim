package com.example.jim.ui.repositories

import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object WorkoutsRepository {

    private val workoutsCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getWorkouts(): List<Workout> {
        if (!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("workouts")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .get()
                .await()
            workoutsCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }

        return workoutsCache

    }

    suspend fun createWorkout(
        name: String
    ): Workout {
        val doc = Firebase.firestore.collection("workouts").document()
        val workout = Workout(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            name = name
        )
        doc.set(workout).await()
        workoutsCache.add(workout)
        return workout
    }

    suspend fun updateWorkout(workout: Workout) {
        Firebase.firestore
            .collection("workouts")
            .document(workout.id!!)
            .set(workout)
            .await()

        val oldWorkoutIndex = workoutsCache.indexOfFirst {
            it.id == workout.id
        }

        workoutsCache[oldWorkoutIndex] = workout
    }
}