package com.dgarcoe.sewercar.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.dgarcoe.sewercar.SewerCarGame

/**
 * Created by Daniel on 02/08/2019.
 */
class GameOverScreen(val game: SewerCarGame, val skin: Skin): Screen, InputProcessor {

    lateinit var stage: Stage
    lateinit var table: Table

    private fun initStage() {
        stage = Stage()
        table = Table(skin)
        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    private fun setStage() {

        val heading = Label("GAME OVER", skin, "white")
        heading.setFontScale(3f)

        val buttonStartGame = TextButton("Play again", skin, "default")
        buttonStartGame.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.startGame()
            }
        })
        buttonStartGame.label.setFontScale(2f)
        buttonStartGame.pad(15f)

        val buttonMainMenu = TextButton("Main menu", skin, "default")
        buttonMainMenu.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.goMainMenu()
            }
        })
        buttonMainMenu.label.setFontScale(2f)
        buttonMainMenu.pad(15f)

        val buttonExit = TextButton("Exit", skin)
        buttonExit.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                //stage.addAction(sequence(fadeOut(0.5f), run(Runnable { Gdx.app.exit() })))
                Gdx.app.exit()
            }
        })
        buttonExit.label.setFontScale(2f)
        buttonExit.pad(15f)

        table.add(heading).spaceBottom(100f).expandX().row()
        table.add(buttonStartGame).width(500f).height(100f).spaceBottom(15f).row()
        table.add(buttonMainMenu).width(500f).height(100f).spaceBottom(15f).row()
        table.add(buttonExit).width(500f).height(100f).spaceBottom(15f).row()

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
