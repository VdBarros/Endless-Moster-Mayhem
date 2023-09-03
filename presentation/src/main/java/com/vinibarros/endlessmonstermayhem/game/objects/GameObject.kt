package com.vinibarros.endlessmonstermayhem.game.objects

import android.graphics.Canvas
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import kotlin.math.pow
import kotlin.math.sqrt


abstract class GameObject {
    var positionX = 1.0
        protected set
    var positionY = 1.0
        protected set
    var velocityX = 0.0
    var velocityY = 0.0
    var directionX = 1.0
        protected set
    var directionY = 0.0
        protected set

    constructor()
    constructor(positionX: Double, positionY: Double) {
        this.positionX = positionX
        this.positionY = positionY
    }

    abstract fun draw(canvas: Canvas, gameDisplay: GameDisplay)
    abstract fun update()

    companion object {
        fun getDistanceBetweenObjects(obj1: GameObject, obj2: GameObject): Double {
            return sqrt(
                (obj2.positionX - obj1.positionX).pow(2.0) +
                        (obj2.positionY - obj1.positionY).pow(2.0)
            )
        }
    }
}