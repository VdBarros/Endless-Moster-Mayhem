package com.vinibarros.endlessmonstermayhem.game.core

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.vinibarros.endlessmonstermayhem.game.view.EndlessMonsterMayhemSurfaceView


class GameLoop(endlessMonsterMayhemSurfaceView: EndlessMonsterMayhemSurfaceView, surfaceHolder: SurfaceHolder) : Thread() {
    private val endlessMonsterMayhemSurfaceView: EndlessMonsterMayhemSurfaceView
    private val surfaceHolder: SurfaceHolder
    private var isRunning = false
    private var averageUPS = 0.0
    private var averageFPS = 0.0

    init {
        this.endlessMonsterMayhemSurfaceView = endlessMonsterMayhemSurfaceView
        this.surfaceHolder = surfaceHolder
    }

    fun startLoop() {
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        var updateCount = 0
        var frameCount = 0
        var startTime: Long
        var elapsedTime: Long
        var sleepTime: Long

        var canvas: Canvas? = null
        startTime = System.currentTimeMillis()
        while (isRunning) {

            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    endlessMonsterMayhemSurfaceView.update()
                    updateCount++
                    endlessMonsterMayhemSurfaceView.draw(canvas)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                endlessMonsterMayhemSurfaceView.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            }

            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime)
                averageFPS = frameCount / (1E-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }

    fun stopLoop() {
        isRunning = false
        try {
            join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun endLoop() {
        isRunning = false
    }

    companion object {
        const val MAX_UPS = 30.0
        private const val UPS_PERIOD = 1E+3 / MAX_UPS
    }
}