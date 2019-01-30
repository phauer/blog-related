package com.phauer

import com.phauer.AuthenticationResult.LdapGroup
import com.phauer.AuthenticationResult.Success

/**
 * create tailored result object
 */
class LdapDAO(
) {
    fun authenticate(username: String, password: String): AuthenticationResult {
        return Success(LdapGroup.READONLY)
    }
}

sealed class AuthenticationResult {
    data class Success(val group: LdapGroup) : AuthenticationResult()
    data class Failure(val reason: FailureReason) : AuthenticationResult()
    enum class FailureReason {
        BLANK_USER_OR_PW,
        INVALID_USER_OR_PW,
        USER_IS_NOT_IN_GROUP,
        CONNECTION_ISSUES
    }

    enum class LdapGroup {
        READONLY,
        NORMAL,
        ADMIN
    }
}