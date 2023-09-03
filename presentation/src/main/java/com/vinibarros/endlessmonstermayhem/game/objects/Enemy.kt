package com.vinibarros.endlessmonstermayhem.game.objects

import android.content.Context
import androidx.core.content.ContextCompat
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.game.view.EndlessMonsterMayhemSurfaceView
import com.vinibarros.endlessmonstermayhem.presentation.R
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp


class Enemy : Circle {
    private var player: Player
    private var context: Context
    constructor(context: Context, player: Player) : super(
        context,
        ContextCompat.getColor(context, R.color.enemy),
        Math.random() * context.getPixelFromDp(1000),
        Math.random() * context.getPixelFromDp(1000),
        context.getPixelFromDp(16)
    ) {
        this.context = context
        this.player = player
    }

    override fun update() {
        val distanceToPlayerX: Double = player.positionX - positionX
        val distanceToPlayerY: Double = player.positionY - positionY

        val distanceToPlayer = getDistanceBetweenObjects(this, player)

        val directionX = distanceToPlayerX / distanceToPlayer
        val directionY = distanceToPlayerY / distanceToPlayer

        if (distanceToPlayer > 0) {
            velocityX = directionX * context.getPixelFromDp(MAX_SPEED.toInt())
            velocityY = directionY * context.getPixelFromDp(MAX_SPEED.toInt())
        } else {
            velocityX = 0.0
            velocityY = 0.0
        }
        positionX += velocityX
        positionY += velocityY
    }

    companion object {
        private const val SPEED_PIXELS_PER_SECOND: Double = Player.SPEED_PIXELS_PER_SECOND * 0.6
        private const val MAX_SPEED = SPEED_PIXELS_PER_SECOND / GameLoop.MAX_UPS
        private const val SPAWNS_PER_MINUTE = 20.0
        private const val SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE / 60.0
        private const val UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND
        private var updatesUntilNextSpawn = UPDATES_PER_SPAWN
        fun readyToSpawn(): Boolean {
            return if (updatesUntilNextSpawn <= 0) {
                updatesUntilNextSpawn += UPDATES_PER_SPAWN
                true
            } else {
                updatesUntilNextSpawn--
                false
            }
        }
    }
}