package com.vinibarros.endlessmonstermayhem.game.panels

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.presentation.R


class ScorePanel(private val context: Context) {
    fun draw(canvas: Canvas, currentScore: Int) {
        val paint = Paint()
        val color = ContextCompat.getColor(context, R.color.magenta)
        paint.color = color
        paint.textSize = 50F
        canvas.drawText("Score: $currentScore", 100F, 100F, paint)
    }
}