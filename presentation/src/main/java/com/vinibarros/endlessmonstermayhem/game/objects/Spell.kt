package com.vinibarros.endlessmonstermayhem.game.objects

import android.content.Context
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class Spell(context: Context, spellCaster: Player) : Circle(
    context,
    ContextCompat.getColor(context, R.color.spell),
    spellCaster.positionX,
    spellCaster.positionY,
    context.getPixelFromDp(8)
) {
    init {
        velocityX = spellCaster.directionX * context.getPixelFromDp(MAX_SPEED.toInt())
        velocityY = spellCaster.directionY * context.getPixelFromDp(MAX_SPEED.toInt())
    }

    override fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 400
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }
}