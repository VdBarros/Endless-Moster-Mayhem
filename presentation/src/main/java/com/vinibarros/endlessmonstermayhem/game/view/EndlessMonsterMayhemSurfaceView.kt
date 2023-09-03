package com.vinibarros.endlessmonstermayhem.game.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.window.layout.WindowMetricsCalculator
import com.vinibarros.endlessmonstermayhem.game.core.GameDisplay
import com.vinibarros.endlessmonstermayhem.game.core.GameLoop
import com.vinibarros.endlessmonstermayhem.game.graphics.Animator
import com.vinibarros.endlessmonstermayhem.game.graphics.SpriteSheet
import com.vinibarros.endlessmonstermayhem.game.map.TileMap
import com.vinibarros.endlessmonstermayhem.game.objects.Circle
import com.vinibarros.endlessmonstermayhem.game.objects.Enemy
import com.vinibarros.endlessmonstermayhem.game.objects.Player
import com.vinibarros.endlessmonstermayhem.game.objects.Spell
import com.vinibarros.endlessmonstermayhem.game.panels.Joystick
import com.vinibarros.endlessmonstermayhem.game.panels.ScorePanel
import com.vinibarros.endlessmonstermayhem.util.getPixelFromDp

class EndlessMonsterMayhemSurfaceView(
    context: Context,
    private val onGameOver: (Int) -> Unit
) : SurfaceView(context), SurfaceHolder.Callback {

    private val tilemap: TileMap
    private var joystickPointerId = 0
    private val joystick: Joystick
    private val player: Player
    private var gameLoop: GameLoop
    private val enemyList = ArrayList<Enemy>()
    private val spellList = ArrayList<Spell>()
    private var numberOfSpellsToCast = 0
    private val scorePanel: ScorePanel
    private var currentScore = 0
    private val gameDisplay: GameDisplay

    init {
        val surfaceHolder = holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setFormat(PixelFormat.RGBA_8888)
        gameLoop = GameLoop(this, surfaceHolder)
        scorePanel = ScorePanel(context, gameLoop)
        joystick = Joystick(getPixelFromDp(100), getPixelFromDp(300), getPixelFromDp(36), getPixelFromDp(28))

        val spriteSheet = SpriteSheet(context)
        val animator = Animator(spriteSheet.playerSpriteArray)
        player = Player(context, joystick, getPixelFromDp(500), getPixelFromDp(500), getPixelFromDp(30), animator)

        val displayMetrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(context as Activity).bounds
        gameDisplay = GameDisplay(displayMetrics.width(), displayMetrics.height(), player)
        tilemap = TileMap(spriteSheet, context)
        isFocusable = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                if (joystick.isPressed) {
                    numberOfSpellsToCast++
                } else if (joystick.isPressed(event.x.toDouble(), event.y.toDouble())) {
                    joystickPointerId = event.getPointerId(event.actionIndex)
                    joystick.isPressed = true
                } else {
                    numberOfSpellsToCast++
                }
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (joystick.isPressed) {
                    joystick.setActuator(event.x.toDouble(), event.y.toDouble())
                }
                return true
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                if (joystickPointerId == event.getPointerId(event.actionIndex)) {
                    joystick.isPressed = false
                    joystick.resetActuator()
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (gameLoop.state == Thread.State.TERMINATED) {
            holder.addCallback(this)
            gameLoop = GameLoop(this, holder)
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        tilemap.draw(canvas, gameDisplay)
        player.draw(canvas, gameDisplay)

        for (enemy in enemyList) {
            enemy.draw(canvas, gameDisplay)
        }

        for (spell in spellList) {
            spell.draw(canvas, gameDisplay)
        }

        joystick.draw(canvas)
        scorePanel.draw(canvas, currentScore)

        if (player.healthPoint <= 0) {
            gameLoop.endLoop()
            onGameOver.invoke(currentScore)
        }
    }

    fun update() {
        if (player.healthPoint <= 0) {
            return
        }

        joystick.update()
        player.update()

        if (Enemy.readyToSpawn()) {
            enemyList.add(Enemy(context, player))
        }

        for (enemy in enemyList) {
            enemy.update()
        }

        while (numberOfSpellsToCast > 0) {
            spellList.add(Spell(context, player))
            numberOfSpellsToCast--
        }

        for (spell in spellList) {
            spell.update()
        }

        val iteratorEnemy: MutableIterator<Enemy> = enemyList.iterator()
        while (iteratorEnemy.hasNext()) {
            val enemy: Circle = iteratorEnemy.next()
            if (Circle.isColliding(enemy, player)) {
                iteratorEnemy.remove()
                player.healthPoint--
                continue
            }
            val iteratorSpell: MutableIterator<Spell> = spellList.iterator()
            while (iteratorSpell.hasNext()) {
                val spell: Circle = iteratorSpell.next()
                if (Circle.isColliding(spell, enemy)) {
                    currentScore++
                    iteratorSpell.remove()
                    iteratorEnemy.remove()
                    break
                }
            }
        }

        gameDisplay.update()
    }

    fun pause() {
        gameLoop.stopLoop()
    }
}
