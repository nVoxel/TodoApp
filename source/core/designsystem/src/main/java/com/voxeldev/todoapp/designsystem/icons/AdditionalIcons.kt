package com.voxeldev.todoapp.designsystem.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * @author nvoxel
 */
object AdditionalIcons {

    private var _importanceHigh: ImageVector? = null
    val ImportanceHigh: ImageVector
        get() {
            _importanceHigh = ImageVector.Builder(
                name = "High",
                defaultWidth = 10.dp,
                defaultHeight = 16.dp,
                viewportWidth = 10f,
                viewportHeight = 16f,
            ).apply {
                path(
                    fill = SolidColor(Color(0xFFFF453A)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(1.69505f, 11.0403f)
                    curveTo(2.5223f, 11.0403f, 2.9765f, 10.5301f, 3.0008f, 9.6519f)
                    lineTo(3.1468f, 1.84004f)
                    curveTo(3.1549f, 1.7229f, 3.163f, 1.5975f, 3.163f, 1.5055f)
                    curveTo(3.163f, 0.5604f, 2.5872f, 0f, 1.695f, 0f)
                    curveTo(0.8029f, 0f, 0.219f, 0.5604f, 0.219f, 1.5055f)
                    curveTo(0.219f, 1.5975f, 0.2271f, 1.7229f, 0.2271f, 1.84f)
                    lineTo(0.381184f, 9.65186f)
                    curveTo(0.4136f, 10.5301f, 0.8597f, 11.0403f, 1.695f, 11.0403f)
                    close()
                    moveTo(8.32117f, 11.0403f)
                    curveTo(9.1484f, 11.0403f, 9.6107f, 10.5301f, 9.635f, 9.6519f)
                    lineTo(9.78102f, 1.84004f)
                    curveTo(9.781f, 1.7229f, 9.7891f, 1.5975f, 9.7891f, 1.5055f)
                    curveTo(9.7891f, 0.5604f, 9.2214f, 0f, 8.3212f, 0f)
                    curveTo(7.429f, 0f, 6.8451f, 0.5604f, 6.8451f, 1.5055f)
                    curveTo(6.8451f, 1.5975f, 6.8532f, 1.7229f, 6.8613f, 1.84f)
                    lineTo(7.01541f, 9.65186f)
                    curveTo(7.0397f, 10.5301f, 7.4858f, 11.0403f, 8.3212f, 11.0403f)
                    close()
                    moveTo(1.68694f, 16f)
                    curveTo(2.6196f, 16f, 3.3739f, 15.264f, 3.3739f, 14.3356f)
                    curveTo(3.3739f, 13.4072f, 2.6196f, 12.6628f, 1.6869f, 12.6628f)
                    curveTo(0.7543f, 12.6628f, 0f, 13.4072f, 0f, 14.3356f)
                    curveTo(0f, 15.264f, 0.7543f, 16f, 1.6869f, 16f)
                    close()
                    moveTo(8.32117f, 16f)
                    curveTo(9.2457f, 16f, 10f, 15.264f, 10f, 14.3356f)
                    curveTo(10f, 13.4072f, 9.2457f, 12.6628f, 8.3212f, 12.6628f)
                    curveTo(7.3885f, 12.6628f, 6.6261f, 13.4072f, 6.6261f, 14.3356f)
                    curveTo(6.6261f, 15.264f, 7.3885f, 16f, 8.3212f, 16f)
                    close()
                }
            }.build()

            return _importanceHigh!!
        }

    private var _importanceLow: ImageVector? = null
    val ImportanceLow: ImageVector
        get() {
            _importanceLow = ImageVector.Builder(
                name = "Low",
                defaultWidth = 12.dp,
                defaultHeight = 16.dp,
                viewportWidth = 12f,
                viewportHeight = 16f,
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF8E8E93)),
                    fillAlpha = 1.0f,
                    stroke = null,
                    strokeAlpha = 1.0f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1.0f,
                    pathFillType = PathFillType.NonZero,
                ) {
                    moveTo(5.99659f, 0f)
                    curveTo(5.3836f, 0f, 4.9749f, 0.4601f, 4.9749f, 1.1393f)
                    verticalLineTo(9.09233f)
                    lineTo(5.03622f, 10.6771f)
                    lineTo(3.75573f, 9.08503f)
                    lineTo(2.20279f, 7.41993f)
                    curveTo(2.0189f, 7.2227f, 1.7805f, 7.0767f, 1.474f, 7.0767f)
                    curveTo(0.9223f, 7.0767f, 0.5f, 7.5076f, 0.5f, 8.1356f)
                    curveTo(0.5f, 8.4205f, 0.609f, 8.6907f, 0.8201f, 8.9244f)
                    lineTo(5.24056f, 13.6714f)
                    curveTo(5.4313f, 13.8758f, 5.7173f, 14f, 5.9966f, 14f)
                    curveTo(6.2759f, 14f, 6.5619f, 13.8758f, 6.7526f, 13.6714f)
                    lineTo(11.1799f, 8.92436f)
                    curveTo(11.391f, 8.6907f, 11.5f, 8.4205f, 11.5f, 8.1356f)
                    curveTo(11.5f, 7.5076f, 11.0777f, 7.0767f, 10.526f, 7.0767f)
                    curveTo(10.2195f, 7.0767f, 9.9811f, 7.2227f, 9.7904f, 7.4199f)
                    lineTo(8.23746f, 9.08503f)
                    lineTo(6.95697f, 10.6771f)
                    lineTo(7.02508f, 9.09233f)
                    verticalLineTo(1.13928f)
                    curveTo(7.0251f, 0.4601f, 6.6096f, 0f, 5.9966f, 0f)
                    close()
                }
            }.build()

            return _importanceLow!!
        }
}
