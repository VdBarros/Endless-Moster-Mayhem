package com.vinibarros.endlessmonstermayhem.game.objects

import android.content.Context
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.presentation.R


class Spell(context: Context, spellCaster: Player) : Circle(
    context,
    ContextCompat.getColor(context, R.color.spell),
    spellCaster.positionX,
    spellCaster.positionY,
    25.0
) {
    init {
        velocityX = spellCaster.directionX * MAX_SPEED
        velocityY = spellCaster.directionY * MAX_SPEED
    }

    override fun update() {
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND = 800.0
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
    }
}