package com.vinibarros.endlessmonstermayhem.game.objects

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.game.graphics.Animator
import com.vinibarros.endlessmonstermayhem.game.panels.HealthBar
import com.vinibarros.endlessmonstermayhem.game.panels.Joystick
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getDistanceBetweenPoints


class Player(
    context: Context,
    joystick: Joystick,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator
) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.player),
        positionX,
        positionY,
        radius
    ) {
    private val joystick: Joystick
    private val healthBar: HealthBar
    private var healthPoints = MAX_HEALTH_POINTS
    private val animator: Animator
    private val playerState: PlayerState

    init {
        this.joystick = joystick
        healthBar = HealthBar(context, this)
        this.animator = animator
        playerState = PlayerState(this)
    }

    override fun update() {
        velocityX = joystick.actuatorX * MAX_SPEED
        velocityY = joystick.actuatorY * MAX_SPEED

        positionX += velocityX
        positionY += velocityY

        if (velocityX != 0.0 || velocityY != 0.0) {
            val distance: Double = getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }
        playerState.update()
    }

    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        animator.draw(canvas, gameDisplay, this)
        healthBar.draw(canvas, gameDisplay)
    }

    var healthPoint: Int
        get() = healthPoints
        set(healthPoints) {
            if (healthPoints >= 0) this.healthPoints = healthPoints
        }

    fun getPlayerState(): PlayerState {
        return playerState
    }

    companion object {
        const val SPEED_PIXELS_PER_SECOND = 400.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        const val MAX_HEALTH_POINTS = 1
    }
}