package com.vinibarros.endlessmonstermayhem.game.panels

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.pow
import kotlin.math.sqrt

class Joystick(
    private val outerCircleCenterPositionX: Double,
    private val outerCircleCenterPositionY: Double,
    outerCircleRadius: Double,
    innerCircleRadius: Double
) {
    private var innerCircleCenterPositionX: Double
    private var innerCircleCenterPositionY: Double
    private val outerCircleRadius: Double
    private val innerCircleRadius: Double
    private val innerCirclePaint: Paint
    private val outerCirclePaint: Paint
    var isPressed = false
    private var joystickCenterToTouchDistance = 0.0
    var actuatorX = 0.0
        private set
    var actuatorY = 0.0
        private set

    init {
        innerCircleCenterPositionX = outerCircleCenterPositionX
        innerCircleCenterPositionY = outerCircleCenterPositionY

        this.outerCircleRadius = outerCircleRadius
        this.innerCircleRadius = innerCircleRadius

        outerCirclePaint = Paint()
        outerCirclePaint.color = Color.GRAY
        outerCirclePaint.style = Paint.Style.FILL_AND_STROKE
        innerCirclePaint = Paint()
        innerCirclePaint.color = Color.BLUE
        innerCirclePaint.style = Paint.Style.FILL_AND_STROKE
    }

    fun draw(canvas: Canvas) {
        canvas.drawCircle(
            outerCircleCenterPositionX.toFloat(),
            outerCircleCenterPositionY.toFloat(),
            outerCircleRadius.toFloat(),
            outerCirclePaint
        )

        canvas.drawCircle(
            innerCircleCenterPositionX.toFloat(),
            innerCircleCenterPositionY.toFloat(),
            innerCircleRadius.toFloat(),
            innerCirclePaint
        )
    }

    fun update() {
        updateInnerCirclePosition()
    }

    private fun updateInnerCirclePosition() {
        innerCircleCenterPositionX =
            (outerCircleCenterPositionX + actuatorX * outerCircleRadius)
        innerCircleCenterPositionY =
            (outerCircleCenterPositionY + actuatorY * outerCircleRadius)
    }

    fun setActuator(touchPositionX: Double, touchPositionY: Double) {
        val deltaX = touchPositionX - outerCircleCenterPositionX
        val deltaY = touchPositionY - outerCircleCenterPositionY
        val deltaDistance = sqrt(deltaX.pow(2.0) + deltaY.pow(2.0))
        if (deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius
            actuatorY = deltaY / outerCircleRadius
        } else {
            actuatorX = deltaX / deltaDistance
            actuatorY = deltaY / deltaDistance
        }
    }

    fun isPressed(touchPositionX: Double, touchPositionY: Double): Boolean {
        joystickCenterToTouchDistance = sqrt(
            (outerCircleCenterPositionX - touchPositionX).pow(2.0) +
                    (outerCircleCenterPositionY - touchPositionY).pow(2.0)
        )
        return joystickCenterToTouchDistance < outerCircleRadius
    }

    fun resetActuator() {
        actuatorX = 0.0
        actuatorY = 0.0
    }
}