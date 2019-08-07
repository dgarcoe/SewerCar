package com.dgarcoe.sewercar.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.dgarcoe.sewercar.SewerCarGame
import com.dgarcoe.sewercar.sounds.SFXManager
import javax.swing.text.LabelView

/**
 * Created by Daniel on 02/08/2019.
 */
class GameOverScreen(val game: SewerCarGame, val skin: Skin, val fontTitle:BitmapFont,
                     val fontScore:BitmapFont, val sfxManager: SFXManager): Screen, InputProcessor {

    private val WIDTH_CAMERA = 128
    private val HEIGHT_CAMERA = 256

    private val WIDTH_BUTTON_PERCENT = 0.45f
    private val HEIGHT_BUTTON_PERCENT = 0.05f

    lateinit var stage: Stage
    lateinit var table: Table

    private val width: Int = 128
    private val height : Int = 256
    var cam: OrthographicCamera? = null
    var viewPort: Viewport? = null

    private fun initStage() {

        val aspectRatio = Gdx.graphics.height/Gdx.graphics.width

        cam = OrthographicCamera(WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        viewPort = FitViewport(WIDTH_CAMERA.toFloat()*aspectRatio, HEIGHT_CAMERA.toFloat(),cam)
        (viewPort as FitViewport).apply()
        cam!!.setToOrtho(false, WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
        cam!!.update()

        stage = Stage()
        table = Table(skin)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    private fun setStage() {

        val headingStyle = Label.LabelStyle()
        headingStyle.font = fontTitle

        val scoreStyle = Label.LabelStyle()
        scoreStyle.font = fontScore

        val heading = Label("GAME OVER", headingStyle)

        val hiScore = Label("High score: "+game.preferences!!.getLong("HiScore"),scoreStyle)
        val score = Label("Score: "+game.world.player!!.score,scoreStyle)

        val buttonStartGame = TextButton("Play again", skin)
        buttonStartGame.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.startGame()
            }
        })
        buttonStartGame.pad(15f)

        val buttonMainMenu = TextButton("Main menu",skin)
        buttonMainMenu.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.goMainMenu()
            }
        })
        buttonMainMenu.pad(15f)

        val buttonExit = TextButton("Exit", skin)
        buttonExit.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                sfxManager.dispose()
                stage.addAction(Actions.sequence(Actions.fadeOut(0.5f), Actions.run(Runnable { Gdx.app.exit() })))
            }
        })
        buttonExit.pad(15f)

        val buttonWidth = Gdx.graphics.width*WIDTH_BUTTON_PERCENT
        val buttonHeight = Gdx.graphics.height*HEIGHT_BUTTON_PERCENT

        table.add(heading).spaceBottom(100f).expandX().row()
        table.add(hiScore).spaceBottom(30f).expandX().row()
        table.add(score).spaceBottom(30f).expandX().row()
        table.add(buttonStartGame).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()
        table.add(buttonMainMenu).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()
        table.add(buttonExit).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()

        stage.addActor(table)
    }

    override fun hide() {
        dispose()
        Gdx.input.inputProcessor = null
    }

    override fun show() {
        initStage()
        setStage()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        viewPort!!.update(width, height)
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
    }

    override fun dispose() {
        stage.dispose()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyTyped(character: Char): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrolled(amount: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyUp(keycode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyDown(keycode: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
