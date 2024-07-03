package com.voxeldev.todoapp.auth.ui

/**
 * @author nvoxel
 */
sealed class AuthScreenState(val stateKey: String) {

    data object ChooseMethod : AuthScreenState(stateKey = "choose")

    data class BearerMethod(
        val login: String = "",
        val password: String = "",
    ) : AuthScreenState(stateKey = "bearer")

    data object OAuthMethodInfo : AuthScreenState(stateKey = "oauthInfo")

    data class OAuthMethod(
        val token: String = "",
    ) : AuthScreenState(stateKey = "oauth")

    data object Success : AuthScreenState(stateKey = "success")
}
