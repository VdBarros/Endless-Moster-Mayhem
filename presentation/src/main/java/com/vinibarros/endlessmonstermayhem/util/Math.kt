package com.vinibarros.endlessmonstermayhem.util

import kotlin.math.pow
import kotlin.math.sqrt

fun getDistanceBetweenPoints(p1x: Double, p1y: Double, p2x: Double, p2y: Double): Double {
    return sqrt((p1x - p2x).pow(2.0) + (p1y - p2y).pow(2.0))
}