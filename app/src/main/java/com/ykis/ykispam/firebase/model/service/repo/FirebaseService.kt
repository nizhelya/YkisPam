package com.ykis.ykispam.firebase.model.service.repo

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.ykis.ykispam.firebase.model.service.entity.User
import com.ykis.ykispam.core.Response

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
typealias SignOutResponse = Response<Boolean>
typealias RevokeAccessResponse = Response<Boolean>
typealias OneTapSignInResponse = Response<BeginSignInResult>
typealias SignInWithGoogleResponse = Response<Boolean>
typealias SignUpResponse = Response<Boolean>
typealias SendEmailVerificationResponse = Response<Boolean>
typealias SignInResponse = Response<Boolean>
typealias addUserFirestoreResponse = Response<Boolean>
typealias ReloadUserResponse = Response<Boolean>
typealias SendPasswordResetEmailResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>


interface FirebaseService {
    val isUserAuthenticatedInFirebase: Boolean
    val uid: String
    val hasUser: Boolean
    val isEmailVerified: Boolean?

    //    val currentUser: Flow<User>
    val currentUser: FirebaseUser?

    val displayName: String
    val providerId: String
    val photoUrl: String
    val email: String

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut(): SignOutResponse
//    fun signOut()

    suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): SignInWithGoogleResponse

    //    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String)
    suspend fun firebaseSignUpWithEmailAndPassword(email: String, password: String): SignUpResponse
    suspend fun sendEmailVerification(): SendEmailVerificationResponse
    suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse

    //    suspend fun sendEmailVerification()
    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String)
//    suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String): SignInResponse
    suspend fun reloadFirebaseUser(): ReloadUserResponse
    //    suspend fun sendPasswordResetEmail(email: String)
    suspend fun revokeAccess(): RevokeAccessResponse
    suspend fun addUserFirestore(): addUserFirestoreResponse

    suspend fun revokeAccessEmail(): RevokeAccessResponse

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse


}

