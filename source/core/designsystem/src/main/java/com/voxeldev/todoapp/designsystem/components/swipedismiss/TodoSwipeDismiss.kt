package com.voxeldev.todoapp.designsystem.components.swipedismiss

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CancellationException
import kotlin.math.roundToInt

/**
 * The directions in which a [TodoSwipeDismiss] can be dismissed.
 */
@ExperimentalMaterial3Api
enum class DismissDirection {
    /**
     * Can be dismissed by swiping in the reading direction.
     */
    StartToEnd,

    /**
     * Can be dismissed by swiping in the reverse of the reading direction.
     */
    EndToStart,
}

/**
 * Possible values of [DismissState].
 */
@ExperimentalMaterial3Api
enum class DismissValue {
    /**
     * Indicates the component has not been dismissed yet.
     */
    Default,

    /**
     * Indicates the component has been dismissed in the reading direction.
     */
    DismissedToEnd,

    /**
     * Indicates the component has been dismissed in the reverse of the reading direction.
     */
    DismissedToStart,
}

/**
 * State of the [TodoSwipeDismiss] composable.
 *
 * @param initialValue The initial value of the state.
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending state change.
 * @param positionalThreshold The positional threshold to be used when calculating the target state
 * while a swipe is in progress and when settling after the swipe ends. This is the distance from
 * the start of a transition. It will be, depending on the direction of the interaction, added or
 * subtracted from/to the origin offset. It should always be a positive value.
 */
@ExperimentalMaterial3Api
class DismissState(
    initialValue: DismissValue,
    confirmValueChange: (DismissValue) -> Boolean = { true },
    positionalThreshold: Density.(totalDistance: Float) -> Float =
        SwipeToDismissDefaults.FixedPositionalThreshold,
) {
    internal val swipeableState = SwipeableV2State(
        initialValue = initialValue,
        confirmValueChange = confirmValueChange,
        positionalThreshold = positionalThreshold,
        velocityThreshold = DismissThreshold,
    )

    internal val offset: Float? get() = swipeableState.offset

    /**
     * Require the current offset.
     *
     * @throws IllegalStateException If the offset has not been initialized yet
     */
    fun requireOffset(): Float = swipeableState.requireOffset()

    /**
     * The current state value of the [DismissState].
     */
    val currentValue: DismissValue get() = swipeableState.currentValue

    /**
     * The target state. This is the closest state to the current offset (taking into account
     * positional thresholds). If no interactions like animations or drags are in progress, this
     * will be the current state.
     */
    val targetValue: DismissValue get() = swipeableState.targetValue

    /**
     * The fraction of the progress going from currentValue to targetValue, within [0f..1f] bounds.
     */
    val progress: Float get() = swipeableState.progress

    /**
     * The direction (if any) in which the composable has been or is being dismissed.
     *
     * If the composable is settled at the default state, then this will be null. Use this to
     * change the background of the [TodoSwipeDismiss] if you want different actions on each side.
     */
    val dismissDirection: DismissDirection?
        get() =
            if (offset == 0f || offset == null) null else if (offset!! > 0f) DismissDirection.StartToEnd else DismissDirection.EndToStart

    /**
     * Whether the component has been dismissed in the given [direction].
     *
     * @param direction The dismiss direction.
     */
    fun isDismissed(direction: DismissDirection): Boolean {
        return currentValue == if (direction == DismissDirection.StartToEnd) DismissValue.DismissedToEnd else DismissValue.DismissedToStart
    }

    /**
     * Set the state without any animation and suspend until it's set
     *
     * @param targetValue The new target value
     */
    suspend fun snapTo(targetValue: DismissValue) {
        swipeableState.snapTo(targetValue)
    }

    /**
     * Reset the component to the default position with animation and suspend until it if fully
     * reset or animation has been cancelled. This method will throw [CancellationException] if
     * the animation is interrupted
     *
     * @return the reason the reset animation ended
     */
    suspend fun reset() = swipeableState.animateTo(targetValue = DismissValue.Default)

    /**
     * Dismiss the component in the given [direction], with an animation and suspend. This method
     * will throw [CancellationException] if the animation is interrupted
     *
     * @param direction The dismiss direction.
     */
    suspend fun dismiss(direction: DismissDirection) {
        val targetValue = if (direction == DismissDirection.StartToEnd) DismissValue.DismissedToEnd else DismissValue.DismissedToStart
        swipeableState.animateTo(targetValue = targetValue)
    }

    companion object {
        /**
         * The default [Saver] implementation for [DismissState].
         */
        fun Saver(
            confirmValueChange: (DismissValue) -> Boolean,
            positionalThreshold: Density.(totalDistance: Float) -> Float,
        ) =
            Saver<DismissState, DismissValue>(
                save = { it.currentValue },
                restore = {
                    DismissState(
                        it,
                        confirmValueChange,
                        positionalThreshold,
                    )
                },
            )
    }
}

/**
 * Create and [remember] a [DismissState].
 *
 * @param initialValue The initial value of the state.
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending state change.
 * @param positionalThreshold The positional threshold to be used when calculating the target state
 * while a swipe is in progress and when settling after the swipe ends. This is the distance from
 * the start of a transition. It will be, depending on the direction of the interaction, added or
 * subtracted from/to the origin offset. It should always be a positive value.
 */
@Composable
@ExperimentalMaterial3Api
fun rememberDismissState(
    initialValue: DismissValue = DismissValue.Default,
    confirmValueChange: (DismissValue) -> Boolean = { true },
    positionalThreshold: Density.(totalDistance: Float) -> Float =
        SwipeToDismissDefaults.FixedPositionalThreshold,
): DismissState {
    return rememberSaveable(
        saver = DismissState.Saver(confirmValueChange, positionalThreshold),
    ) {
        DismissState(initialValue, confirmValueChange, positionalThreshold)
    }
}

/**
 * SwipeToDismiss component from Material3 v1.1.2
 * @see <a href="https://issuetracker.google.com/issues/348333888">Issue with SwipeToDismissBox</a>
 */
@Composable
@ExperimentalMaterial3Api
fun TodoSwipeDismiss(
    state: DismissState,
    background: @Composable RowScope.() -> Unit,
    dismissContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    directions: Set<DismissDirection> = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    Box(
        modifier
            .swipeableV2(
                state = state.swipeableState,
                orientation = Orientation.Horizontal,
                enabled = state.currentValue == DismissValue.Default,
                reverseDirection = isRtl,
            )
            .swipeAnchors(
                state = state.swipeableState,
                possibleValues = setOf(
                    DismissValue.Default,
                    DismissValue.DismissedToEnd,
                    DismissValue.DismissedToStart,
                ),
            ) { value, layoutSize ->
                val width = layoutSize.width.toFloat()
                when (value) {
                    DismissValue.DismissedToEnd -> if (DismissDirection.StartToEnd in directions) width else null
                    DismissValue.DismissedToStart -> if (DismissDirection.EndToStart in directions) -width else null
                    DismissValue.Default -> 0f
                }
            },
    ) {
        Row(
            content = background,
            modifier = Modifier.matchParentSize(),
        )
        Row(
            content = dismissContent,
            modifier = Modifier.offset { IntOffset(state.requireOffset().roundToInt(), 0) },
        )
    }
}

/** Contains default values for [TodoSwipeDismiss] and [DismissState]. */
@ExperimentalMaterial3Api
object SwipeToDismissDefaults {
    /** Default positional threshold of 56.dp for [DismissState]. */
    val FixedPositionalThreshold: Density.(totalDistance: Float) -> Float = { _ -> 56.dp.toPx() }
}

private val DismissThreshold = 125.dp
