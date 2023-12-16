package com.ykis.ykispam.core

object Constants {
    //App
    const val TAG = "AppTag"

    //Buttons
    const val SIGN_IN = "Sign in"
    const val RESET_PASSWORD = "Reset"
    const val SIGN_UP = "Sign up"
    const val SIGN_OUT = "Sign out"
    const val REVOKE_ACCESS = "Revoke Access"

    //Screens
    const val SIGN_IN_SCREEN = "Sign in"
    const val FORGOT_PASSWORD_SCREEN = "Forgot password"
    const val SIGN_UP_SCREEN = "Sign up"
    const val VERIFY_EMAIL_SCREEN = "Verify email"
    const val PROFILE_SCREEN = "Profile"

    //Labels
    const val EMAIL_LABEL = "Email"
    const val PASSWORD_LABEL = "Password"

    //Useful
    const val NO_VALUE = ""
    const val VERTICAL_DIVIDER = "|"

    //Texts
    const val FORGOT_PASSWORD = "Forgot password?"
    const val NO_ACCOUNT = "No account? Sign up."
    const val ALREADY_USER = "Already a user? Sign in."
    const val WELCOME_MESSAGE = "Welcome to our app."
    const val ALREADY_VERIFIED = "Already verified?"
    const val SPAM_EMAIL = "If not, please also check the spam folder."

    //Messages
    const val VERIFY_DELETE_FLAT = "Appartment deleted"

    const val VERIFY_EMAIL_MESSAGE = "We've sent you an email with a link to verify the email."
    const val EMAIL_NOT_VERIFIED_MESSAGE = "Your email is not verified."
    const val RESET_PASSWORD_MESSAGE = "We've sent you an email with a link to reset the password."
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
    const val ACCESS_REVOKED_MESSAGE = "Your access has been revoked."

    //Error Messages
    const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."



    //Collection References
    const val USERS = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    const val TASK_ID = "taskId"
    const val TASK_DEFAULT_ID = "-1"
    const val TASK_ID_ARG = "?$TASK_ID={$TASK_ID}"

    //Failure Message
    const val INCORRECT_CODE = "IncorrectCode"
    const val NO_FLAT_DELETE = "Failed to delete apartment"
    const val NO_USER_IDENTIFIER = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val PASSWORD_FAILURE  = "The password is invalid or the user does not have a password."
}