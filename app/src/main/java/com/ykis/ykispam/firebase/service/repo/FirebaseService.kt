package com.ykis.ykispam.firebase.service.repo

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.domain.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

typealias SignOutResponse = Resource<Boolean>
typealias RevokeAccessResponse = Resource<Boolean>
typealias OneTapSignInResponse = Resource<BeginSignInResult>
typealias SignInWithGoogleResponse = Resource<Boolean>
typealias SignUpResponse = Resource<Boolean>
typealias SendEmailVerificationResponse = Resource<Boolean>
typealias SignInResponse = Resource<Boolean>
typealias addUserFirestoreResponse = Resource<Boolean>
typealias ReloadUserResponse = Resource<Boolean>
typealias SendPasswordResetEmailResponse = Resource<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>


interface FirebaseService {
    val isUserAuthenticatedInFirebase: Boolean
    val uid: String
    val hasUser: Boolean
    val isEmailVerified: Boolean?
    val currentUser: FirebaseUser?
    val displayName: String
    val providerId: String
    val photoUrl: String
    val email: String

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun linkAccount(email: String, password: String)

    suspend fun deleteAccount()

    fun signOut(): Flow<Resource<Boolean>>
//    fun signOut()


    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse
    fun getProvider(viewModelScope: CoroutineScope):String


    //    suspend fun sendEmailVerification()
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String)

    //        suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun reloadFirebaseUser(): ReloadUserResponse


    fun revokeAccess(): Flow<Resource<Boolean>>

    //    suspend fun sendPasswordResetEmail(email: String)
    suspend fun addUserFirestore(): addUserFirestoreResponse

   fun revokeAccessEmail(): Flow<Resource<Boolean>>

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse

    suspend fun getUserRole() : UserRole
}

