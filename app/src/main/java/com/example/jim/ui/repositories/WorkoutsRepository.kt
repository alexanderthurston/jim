package com.example.jim.ui.repositories

import com.example.jim.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object WorkoutsRepository {

    private val workoutsCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getUserWorkouts(): List<Workout> {
//        if (!cacheInitialized){
        val snapshot = Firebase.firestore
            .collection("workouts")
            .whereEqualTo("userId", UserRepository.getCurrentUserId())
            .get()
            .await()
        return snapshot.toObjects()
//            workoutsCache.addAll(snapshot.toObjects())
//            cacheInitialized = true
//        }
//
//        return workoutsCache

    }

    suspend fun createWorkout(
        duration: Int,
        date: String
    ): Workout {
        val doc = Firebase.firestore.collection("workouts").document()
        val workout = Workout(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            date = date,
            duration = duration
        )
        doc.set(workout).await()
        workoutsCache.add(workout)
        return workout
    }
}