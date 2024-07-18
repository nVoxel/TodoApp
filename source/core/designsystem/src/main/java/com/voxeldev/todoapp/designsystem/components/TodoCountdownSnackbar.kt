package com.voxeldev.todoapp.designsystem.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voxeldev.todoapp.designsystem.R
import com.voxeldev.todoapp.designsystem.preview.annotations.ComponentDayNightPreviews
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase
import com.voxeldev.todoapp.designsystem.theme.AppTypography
import com.voxeldev.todoapp.designsystem.theme.LocalAppPalette
import kotlinx.coroutines.delay
import kotlin.math.ceil

private const val MILLIS_IN_SECOND = 1000
private const val INITIAL_PROGRESS = 1f
private const val COUNTDOWN_SPEED = 40

/**
 * @author nvoxel
 */
@Composable
fun TodoCountdownSnackbar(
    countdownActive: Boolean,
    text: String,
    totalSeconds: Int,
    color: Color,
    onContinue: () -> Unit,
    onDismiss: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    var snackbarVisible by rememberSaveable { mutableStateOf(false) }
    var snackbarMillis by rememberSaveable { mutableIntStateOf(totalSeconds * MILLIS_IN_SECOND) }
    var snackbarSeconds by rememberSaveable { mutableIntStateOf(totalSeconds) }
    var snackbarProgress by rememberSaveable { mutableFloatStateOf(INITIAL_PROGRESS) }

    val animatedColor = remember { Animatable(initialValue = color) }

    LaunchedEffect(key1 = countdownActive) {
        if (!countdownActive) {
            snackbarVisible = false
            snackbarMillis = totalSeconds * MILLIS_IN_SECOND
            snackbarSeconds = totalSeconds
            snackbarProgress = INITIAL_PROGRESS
            return@LaunchedEffect
        }

        snackbarVisible = true

        while (snackbarMillis > 0) {
            delay(COUNTDOWN_SPEED.toLong())
            snackbarMillis -= COUNTDOWN_SPEED
            snackbarSeconds = ceil(snackbarMillis / MILLIS_IN_SECOND.toFloat()).toInt()
            snackbarProgress = snackbarMillis / (totalSeconds * MILLIS_IN_SECOND.toFloat())
        }

        onContinue()
    }

    LaunchedEffect(key1 = countdownActive) {
        if (countdownActive) {
            animatedColor.animateTo(
                targetValue = appPalette.colorRed,
                animationSpec = tween(durationMillis = totalSeconds * MILLIS_IN_SECOND),
            )
        } else {
            animatedColor.snapTo(targetValue = color)
        }
    }

    BottomAnimatedVisibility(isVisible = snackbarVisible) {
        TodoCountdownSnackbar(
            text = text,
            progress = snackbarProgress,
            seconds = snackbarSeconds,
            color = animatedColor.value,
            onClick = onDismiss,
        )
    }
}

@Composable
private fun TodoCountdownSnackbar(
    text: String,
    progress: Float,
    seconds: Int,
    color: Color,
    onClick: () -> Unit,
) {
    val appPalette = LocalAppPalette.current

    val textStyle = TextStyle(
        color = appPalette.labelPrimary,
        fontSize = 14.sp,
    )
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = remember(seconds) {
        textMeasurer.measure(text = "$seconds", style = textStyle)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(width = 16.dp))

        Canvas(modifier = Modifier.size(width = 24.dp, height = 24.dp)) {
            val strokeStyle = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
            )

            drawCircle(
                color = appPalette.colorBlue.copy(alpha = 0.12f),
                style = strokeStyle,
            )

            drawArc(
                color = appPalette.colorBlue,
                startAngle = -90f,
                sweepAngle = (-360f * progress),
                useCenter = false,
                style = strokeStyle,
            )

            drawText(
                textMeasurer = textMeasurer,
                text = "$seconds",
                style = textStyle,
                topLeft = Offset(
                    x = center.x - textLayoutResult.size.width / 2,
                    y = center.y - textLayoutResult.size.height / 2,
                ),
            )
        }

        Spacer(modifier = Modifier.width(width = 16.dp))

        Text(
            modifier = Modifier.weight(weight = 1f),
            text = text,
            color = appPalette.labelPrimary,
            style = AppTypography.subhead,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.width(width = 16.dp))

        TodoSnackbarUndoAction(onClick = onClick)

        Spacer(modifier = Modifier.width(width = 8.dp))
    }
}

@Composable
private fun TodoSnackbarUndoAction(onClick: () -> Unit) {
    val appPalette = LocalAppPalette.current

    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 4.dp)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(size = 20.dp),
            imageVector = Icons.AutoMirrored.Default.Undo,
            tint = appPalette.colorBlue,
            contentDescription = stringResource(id = R.string.undo),
        )

        Spacer(modifier = Modifier.width(width = 8.dp))

        Text(
            text = stringResource(id = R.string.undo),
            color = appPalette.colorBlue,
            style = AppTypography.button,
        )
    }
}

@ComponentDayNightPreviews
@Composable
private fun Preview() {
    PreviewBase {
        val appPalette = LocalAppPalette.current

        Box(modifier = Modifier.padding(all = 8.dp)) {
            TodoCountdownSnackbar(
                text = "Deleting something",
                progress = 0.7f,
                seconds = 4,
                color = appPalette.backSecondary,
                onClick = {},
            )
        }
    }
}
