package com.vinibarros.endlessmonstermayhem.game.objects

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.game.graphics.Animator
import com.vinibarros.endlessmonstermayhem.game.map.TileMap
import com.vinibarros.endlessmonstermayhem.game.panels.HealthBar
import com.vinibarros.endlessmonstermayhem.game.panels.Joystick
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getDistanceBetweenPoints
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class Player(
    val context: Context,
    joystick: Joystick,
    positionX: Double,
    positionY: Double,
    radius: Double,
    animator: Animator,
    tileMap: TileMap
) :
    Circle(
        context,
        ContextCompat.getColor(context, R.color.player),
        positionX,
        positionY,
        radius
    ) {
    var actionShootSpell = false
    private val joystick: Joystick
    private val healthBar: HealthBar
    private var healthPoints = MAX_HEALTH_POINTS
    val animator: Animator
    var numberOfSpellsToCast = 0
    private val tileMap: TileMap
    private val playerState: PlayerState
    private val cooldownTime = 15
    private var timeSinceLastSpell = 0


    init {
        this.joystick = joystick
        healthBar = HealthBar(context, this)
        this.animator = animator
        this.tileMap = tileMap
        playerState = PlayerState(this)
    }

    override fun update() {
        velocityX = joystick.actuatorX * context.getPixelFromDp(MAX_SPEED.toInt())
        velocityY = joystick.actuatorY * context.getPixelFromDp(MAX_SPEED.toInt())

        positionX += velocityX
        positionY += velocityY

        val playerCircle = RectF((positionX - radius).toFloat(),
            (positionY - radius).toFloat(), (positionX + radius).toFloat(), (positionY + radius).toFloat()
        )

        if (tileMap.checkPlayerCollision(playerCircle)){
            positionX += -velocityX
            positionY += -velocityY
        }

        if (velocityX != 0.0 || velocityY != 0.0) {
            val distance: Double = getDistanceBetweenPoints(0.0, 0.0, velocityX, velocityY)
            directionX = velocityX / distance
            directionY = velocityY / distance
        }

        if (actionShootSpell) {
            if (timeSinceLastSpell >= cooldownTime) {
                numberOfSpellsToCast++
                timeSinceLastSpell = 0
            }
        }
        timeSinceLastSpell++
        playerState.update()
    }

    override fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        animator.draw(canvas, gameDisplay, this, context)
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
        const val SPEED_PIXELS_PER_SECOND = 300
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        const val MAX_HEALTH_POINTS = 5
    }
}