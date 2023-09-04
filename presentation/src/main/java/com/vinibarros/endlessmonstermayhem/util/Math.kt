package com.vinibarros.endlessmonstermayhem.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

fun getDistanceBetweenPoints(p1x: Double, p1y: Double, p2x: Double, p2y: Double): Double {
    return sqrt((p1x - p2x).pow(2.0) + (p1y - p2y).pow(2.0))
}

fun isGameObjectVisible(positionX: Double, positionY: Double, width: Int, height: Int, centerPositionX: Double, centerPositionY: Double, visibilityMargin: Double): Boolean {
    val halfWidth = width/2
    val halfHeight = height/2
     return positionX >= centerPositionX - visibilityMargin - halfWidth && positionX < centerPositionX + halfWidth+ visibilityMargin &&
            positionY >= centerPositionY - visibilityMargin - halfHeight && positionY < centerPositionY + halfHeight + visibilityMargin
}

fun View.getPixelFromDp(value: Int) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    value.toFloat(),
    context.resources.displayMetrics
).toDouble()

fun Context.getPixelFromDp(value: Int) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    value.toFloat(),
    resources.displayMetrics
).toDouble()

fun Context.getPixelFromSp(value: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value,
        resources.displayMetrics
    )
}