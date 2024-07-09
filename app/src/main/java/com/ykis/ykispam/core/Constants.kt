package com.ykis.ykispam.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Buttons
    const val SIGN_OUT = "Вийти з аккаунта"

    //Screens
//    const val SIGN_UP_SCREEN = "Sign up"
//    const val VERIFY_EMAIL_SCREEN = "Verify email"

    //Labels


    //Messages
    const val VERIFY_DELETE_FLAT = "Appartment deleted"

    const val REVOKE_ACCESS_MESSAGE = "Вам потрібно повторно автентифікуватися, перш ніж скасувати доступ."

    //Error Messages
    const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."



    //Collection References
    const val USERS = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val ROLE = "role"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"


    //Failure Message
    const val INCORRECT_CODE = "IncorrectCode"
    const val NO_FLAT_DELETE = "Failed to delete apartment"
    const val NO_USER_IDENTIFIER = "There is no user record corresponding to this identifier. The user may have been deleted."

    const val PASSWORD_FAILURE  = "The password is invalid or the user does not have a password."
}