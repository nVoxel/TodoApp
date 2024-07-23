package com.voxeldev.todoapp.about.div

import android.net.Uri
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

/**
 * @author nvoxel
 */
class TodoDivActionHandler(
    private val onOpenProfile: () -> Unit,
    private val onOpenRepo: () -> Unit,
    private val onClose: () -> Unit,
) : DivActionHandler() {

    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver,
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        return if (url.scheme == SCHEME_TODO && handleScreenAction(action = url)) {
            true
        } else {
            super.handleAction(action, view, resolver)
        }
    }

    private fun handleScreenAction(action: Uri): Boolean {
        return when (action.host) {
            CLOSE_ACTION -> {
                onClose()
                true
            }
            PROFILE_ACTION -> {
                onOpenProfile()
                true
            }
            REPO_ACTION -> {
                onOpenRepo()
                true
            }
            else -> false
        }
    }

    private companion object {
        const val SCHEME_TODO = "todo-action"

        const val CLOSE_ACTION = "close"
        const val PROFILE_ACTION = "profile"
        const val REPO_ACTION = "repo"
    }
}
