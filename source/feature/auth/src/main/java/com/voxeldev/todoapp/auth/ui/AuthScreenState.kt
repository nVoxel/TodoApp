package com.voxeldev.todoapp.auth.ui

/**
 * @author nvoxel
 */
sealed class AuthScreenState(val stateKey: String) {

    data class ChooseMethod(
        val oauthToken: String? = null,
    ) : AuthScreenState(stateKey = "choose")

    data class BearerMethod(
        val login: String = "",
        val password: String = "",
    ) : AuthScreenState(stateKey = "bearer")

    data object Success : AuthScreenState(stateKey = "success")
}
