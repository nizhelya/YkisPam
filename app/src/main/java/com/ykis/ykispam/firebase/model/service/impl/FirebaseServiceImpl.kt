package com.ykis.ykispam.firebase.model.service.impl

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.EmailAuthProvider.PROVIDER_ID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.ykis.ykispam.core.Constants.CREATED_AT
import com.ykis.ykispam.core.Constants.DISPLAY_NAME
import com.ykis.ykispam.core.Constants.EMAIL
import com.ykis.ykispam.core.Constants.PHOTO_URL
import com.ykis.ykispam.core.Constants.SIGN_IN_REQUEST
import com.ykis.ykispam.core.Constants.SIGN_UP_REQUEST
import com.ykis.ykispam.core.Constants.USERS
import com.ykis.ykispam.core.Response.*
import com.ykis.ykispam.core.trace
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.firebase.model.service.repo.OneTapSignInResponse
import com.ykis.ykispam.firebase.model.service.repo.ReloadUserResponse
import com.ykis.ykispam.firebase.model.service.repo.RevokeAccessResponse
import com.ykis.ykispam.firebase.model.service.repo.SendEmailVerificationResponse
import com.ykis.ykispam.firebase.model.service.repo.SendPasswordResetEmailResponse
import com.ykis.ykispam.firebase.model.service.repo.SignInResponse
import com.ykis.ykispam.firebase.model.service.repo.SignInWithGoogleResponse
import com.ykis.ykispam.firebase.model.service.repo.SignOutResponse
import com.ykis.ykispam.firebase.model.service.repo.SignUpResponse
import com.ykis.ykispam.firebase.model.service.repo.addUserFirestoreResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FirebaseServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    private var signInClient: GoogleSignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
) : FirebaseService {

    override val isUserAuthenticatedInFirebase
        get() = auth.currentUser != null


    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val isEmailVerified: Boolean
        get() = auth.currentUser?.isEmailVerified != null

    override val uid = auth.currentUser?.uid.toString()
    override val displayName = auth.currentUser?.displayName.toString()
    override val photoUrl = auth.currentUser?.photoUrl.toString()
    override val email = auth.currentUser?.email.toString()
    override val providerId = auth.currentUser?.providerId.toString()

    override val currentUser get() = auth.currentUser


    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose { auth.removeAuthStateListener(authStateListener) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)


    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun revokeAccessEmail(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.apply {
                db.collection(USERS).document(uid).delete().await()
                signInClient.revokeAccess().await()
                oneTapClient.signOut().await()
                delete().await()
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }


    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }
//    override fun signOut() = auth.signOut()

    override suspend fun signOut(): SignOutResponse {
        return try {
            oneTapClient.signOut().await()
            auth.signOut()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Success(signUpResult)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore()
            }
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


    override suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()

        }

    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String,
        password: String
    ): SignUpResponse {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            try {
//                val credential = EmailAuthProvider.getCredential(email, password)
////                val authResult = auth.signInWithCredential(credential).await()
////                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
////                if (isNewUser) {
                addUserToFirestore()
////                }
            } catch (e: Exception) {
                Failure(e)
            }

            Success(true)

        } catch (e: Exception) {
            Failure(e)
        }
    }

    private suspend fun addUserToFirestore() {
        auth.currentUser?.apply {
            val user = toUser()
            db.collection(USERS).document(uid).set(user).await()
        }
    }

    override suspend fun addUserFirestore(): addUserFirestoreResponse {
        return try {
            addUserToFirestore()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}

fun FirebaseUser.toUser() = mapOf(
    DISPLAY_NAME to displayName,
    PROVIDER_ID to providerId,
    EMAIL to email,
    PHOTO_URL to photoUrl?.toString(),
    CREATED_AT to serverTimestamp()
)
