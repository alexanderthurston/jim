package com.example.jim.ui.repositories

import com.example.jim.ui.models.Set
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object SetsRepository {

    private val setsCache = mutableListOf<Set>()
    private var cacheInitialized = false

    suspend fun getSets(workoutId: String, exerciseId: String): List<Set> {
        if (!cacheInitialized){
            val snapshot = Firebase.firestore
                .collection("sets")
                .whereEqualTo("userId", UserRepository.getCurrentUserId())
                .whereEqualTo("workoutId", workoutId)
                .whereEqualTo("exerciseId", exerciseId)
                .get()
                .await()
            setsCache.addAll(snapshot.toObjects())
            cacheInitialized = true
        }

        return setsCache

    }

    suspend fun createSet(
        reps: Int,
        weight: Int,
        exerciseId: String,
        workoutId: String? = "",
    ): Set {
        val doc = Firebase.firestore.collection("sets").document()
        val set = Set(
            id = doc.id,
            userId = UserRepository.getCurrentUserId(),
            reps = reps,
            weight = weight,
            exerciseId = exerciseId,
            workoutId = workoutId
        )
        doc.set(set).await()
        setsCache.add(set)
        return set
    }

    suspend fun updateSet(set: Set) {
        Firebase.firestore
            .collection("sets")
            .document(set.id!!)
            .set(set)
            .await()

        val oldSetIndex = setsCache.indexOfFirst {
            it.id == set.id
        }
        setsCache[oldSetIndex] = set
    }
}