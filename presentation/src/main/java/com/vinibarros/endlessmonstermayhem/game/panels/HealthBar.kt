package com.vinibarros.endlessmonstermayhem.game.panels

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.objects.Player
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class HealthBar(context: Context, private val player: Player) {
    private val context: Context
    private val borderPaint: Paint = Paint()
    private val healthPaint: Paint
    private val width = 100
    private val height = 20
    private val margin = 2

    init {
        this.context = context
        val borderColor = ContextCompat.getColor(context, R.color.healthBarBorder)
        borderPaint.color = borderColor
        healthPaint = Paint()
        val healthColor = ContextCompat.getColor(context, R.color.healthBarHealth)
        healthPaint.color = healthColor
    }

    fun draw(canvas: Canvas, gameDisplay: GameDisplay) {
        val x = player.positionX.toFloat()
        val y = player.positionY.toFloat()
        val distanceToPlayer = context.getPixelFromDp(30).toFloat()
        val healthPointPercentage = player.healthPoint.toFloat() / Player.MAX_HEALTH_POINTS

        val borderTop: Float
        val borderLeft: Float = x - width / 2
        val borderRight: Float = x + width / 2
        val borderBottom: Float = y - distanceToPlayer
        borderTop = borderBottom - height
        canvas.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(borderLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(borderRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(borderBottom.toDouble()).toFloat(),
            borderPaint
        )

        val healthTop: Float
        val healthRight: Float
        val healthWidth: Float = (width - 2 * margin).toFloat()
        val healthHeight: Float = (height - 2 * margin).toFloat()
        val healthLeft: Float = borderLeft + margin
        healthRight = healthLeft + healthWidth * healthPointPercentage
        val healthBottom: Float = borderBottom - margin
        healthTop = healthBottom - healthHeight
        canvas.drawRect(
            gameDisplay.gameToDisplayCoordinatesX(healthLeft.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthTop.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesX(healthRight.toDouble()).toFloat(),
            gameDisplay.gameToDisplayCoordinatesY(healthBottom.toDouble()).toFloat(),
            healthPaint
        )
    }
}