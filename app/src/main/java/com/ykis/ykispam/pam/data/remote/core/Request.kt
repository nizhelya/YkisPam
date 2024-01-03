package com.ykis.ykispam.pam.data.remote.core

import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Request @Inject constructor(private val networkHandler: NetworkHandler) {

    fun <T : BaseResponse, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> execute(call, transform)
            false -> Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T : BaseResponse, R> execute(
        call: Call<T>,
        transform: (T) -> R
    ): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSucceed()) {
                true -> Either.Right(transform((response.body()!!)))
                false -> Either.Left(response.parseError())
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}

fun <T : BaseResponse> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && (body() as BaseResponse).success == 1
}

fun <T : BaseResponse> Response<T>.parseError(): Failure {
    val message = (body() as BaseResponse).message
    return when (message) {
        "Incorrect reading" -> Failure.FailIncorrectReading
        "Reading did not add" -> Failure.FailAddReading
        "Failed to update contacts" -> Failure.FailUpdateBti
        "Failed to delete apartment" -> Failure.FailDeleteFlat
        "FlatAlreadyInDataBase" -> Failure.FlatAlreadyInDataBase
        "Required field(s) is missing" -> Failure.MissingFields
        "IncorrectCode" -> Failure.IncorrectCode
        "there is a user has this email",
        "Token is invalid" -> Failure.TokenError
        "can't send email to you" -> Failure.CantSendEmailError
        else -> Failure.ServerError
    }
}