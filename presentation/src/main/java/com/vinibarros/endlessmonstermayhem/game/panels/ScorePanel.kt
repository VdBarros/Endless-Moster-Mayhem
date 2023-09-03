package com.vinibarros.endlessmonstermayhem.game.panels

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp
import com.vinibarros.endlessmonstermayhem.util.getPixelFromSp


class ScorePanel(private val context: Context, private  val gameLoop: GameLoop) {
    fun draw(canvas: Canvas, currentScore: Int) {
        drawScore(canvas, currentScore)
        drawUPS(canvas)
        drawFPS(canvas)
    }

    private fun drawScore(canvas: Canvas, score: Int) {
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = context.getPixelFromSp(16F)
        canvas.drawText("Score: $score", context.getPixelFromDp(24).toFloat(), context.getPixelFromDp(24).toFloat(), paint)
    }

    private fun drawUPS(canvas: Canvas) {
        val averageUPS = gameLoop.getAverageUPS().toString()
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = context.getPixelFromSp(16F)
        canvas.drawText("UPS: $averageUPS", context.getPixelFromDp(24).toFloat(), context.getPixelFromDp(48).toFloat(), paint)
    }

    private fun drawFPS(canvas: Canvas) {
        val averageFPS = gameLoop.getAverageFPS().toString()
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = context.getPixelFromSp(16F)
        canvas.drawText("FPS: $averageFPS", context.getPixelFromDp(24).toFloat(), context.getPixelFromDp(72).toFloat(), paint)
    }
}