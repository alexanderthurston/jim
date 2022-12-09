package com.example.jim.ui.repositories

import com.example.jim.ui.models.Exercise
import com.example.jim.ui.models.UserProfile
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignUpException(message: String?): RuntimeException(message)
class SignInException(message: String?): RuntimeException(message)
object UserRepository {
    suspend fun createUser(email: String, password: String){
        try {
            Firebase.auth.createUserWithEmailAndPassword(
                email,
                password,
            ).await()
        } catch (e: FirebaseAuthException) {
            throw SignUpException(e.message)
        }
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            Firebase.auth.signInWithEmailAndPassword(
                email,
                password
            ).await()
        } catch (e: FirebaseAuthException) {
            throw SignInException(e.message)
        }
    }

    suspend fun createUserProfile(
        name: String,
        birthday: String
    ): UserProfile {
        val doc = Firebase.firestore.collection("userProfiles").document()
        val userProfile = UserProfile(
            userId = getCurrentUserId(),
            name = name,
            birthday = birthday
        )
        doc.set(userProfile).await()
        return userProfile
    }

    suspend fun userProfileExists(): Boolean {
        val snapshot = Firebase.firestore
            .collection("userProfiles")
            .whereEqualTo("userId", getCurrentUserId())
            .get()
            .await()
        return !snapshot.isEmpty
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun isUserLoggedIn(): Boolean {
        return getCurrentUserId() != null
    }

    fun signOutUser() {
        Firebase.auth.signOut()
    }
}