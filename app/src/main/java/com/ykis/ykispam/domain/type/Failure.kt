package com.ykis.ykispam.domain.type

/**
 * Базовый класс для обработки ошибок / сбоев / исключений. */

sealed class Failure {
    data object NetworkConnectionError : Failure()
    data object ServerError : Failure()
    data object TokenError : Failure()
    data object CantSendEmailError : Failure()

    data object FlatAlreadyInDataBase : Failure()
    data object IncorrectCode : Failure()

    data object MissingFields : Failure()
    data object FailDeleteFlat : Failure()
    data object FailUpdateBti : Failure()
    data object FailAddReading : Failure()
    data object FailIncorrectReading : Failure()

    data object NoSavedAccountsError : Failure()

    data object FilePickError : Failure()
}