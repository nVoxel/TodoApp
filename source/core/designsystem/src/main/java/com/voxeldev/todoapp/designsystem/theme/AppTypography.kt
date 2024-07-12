package com.voxeldev.todoapp.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.voxeldev.todoapp.designsystem.preview.AppTypographyPreview
import com.voxeldev.todoapp.designsystem.preview.base.PreviewBase

object AppTypography {

    val largeTitle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 38.sp,
    )

    val title = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 32.sp,
    )

    val button = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        lineHeight = 24.sp,
    )

    val body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
    )

    val bodyStrikethrough = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
        textDecoration = TextDecoration.LineThrough,
    )

    val subhead = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
    )

    val primaryTextField = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 32.sp,
    )
}

@Preview(name = "Text styles")
@Composable
private fun Preview() {
    PreviewBase {
        AppTypographyPreview()
    }
}
