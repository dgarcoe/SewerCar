package com.dgarcoe.sewercar.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.math.Rectangle
import com.dgarcoe.sewercar.SewerCarGame
import com.dgarcoe.sewercar.renderers.WorldRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Timer
import com.badlogic.gdx.utils.Timer.Task
import com.dgarcoe.sewercar.entities.Sewer
import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label


/**
 * Created by Daniel on 23/06/2019.
 */
class PlayingScreen (val game: SewerCarGame, val skin: Skin): Screen, InputProcessor {

    lateinit var worldRenderer : WorldRenderer

    var elapsed: Float = 0.0f

    val SEWER_GENERATION_TIME = 1.2f

    lateinit var stage: Stage
    lateinit var table: Table

    var health: Label? = null
    var score: Label? = null

    override fun hide() {
        dispose()
        Gdx.input.inputProcessor = null
    }

    private fun initStage() {
        stage = Stage()
        table = Table(skin)

       table.setFillParent(true)
    }

    private fun setStage() {

        health = Label("Health: " + game.world.player!!.health, skin)
        health!!.setColor(0.1f, 1f, 0.1f, 1f)
        health!!.setFontScale(2f)

        score = Label("Score: " + String.format("%06d", game.world.player!!.score), skin)
        score!!.setColor(126f, 1f, 1f, 1f)
        score!!.setFontScale(2f)


        table.top()
        table.add(score).expandX().center().row()
        table.add(health).left().row()
        stage.addActor(table)
    }

    private fun updateHUD() {

        val healthNo = Math.round(game.world.player!!.health)
        health!!.setText("Health: " + Integer.toString(healthNo))
        score!!.setText("Score: " + String.format("%06d", game.world.player!!.score))
    }

    override fun show() {
        worldRenderer = WorldRenderer(game.world)
        initStage()
        setStage()

        Gdx.input.setInputProcessor(this);

    }

    override fun render(delta: Float) {
        elapsed += delta

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (game.world.player!!.health<=0) {
            game.endGame()
        }

        if (elapsed > SEWER_GENERATION_TIME) {
            game.world.generateSewer()
            elapsed = 0f
        }

        for (sewer:Sewer in game.world.sewers) {
            if (sewer.collided(game.world.player!!)) {
                sewer.collidable = false
                game.world.player!!.health -= sewer.damage
                Gdx.input.vibrate(250)
            }
        }

        worldRenderer.render(delta)

        stage.act(delta)
        updateHUD()
        stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        worldRenderer.resize(width,height)
    }

    override fun dispose() {
        stage.dispose()
        worldRenderer.dispose()
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scrolled(amount: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        var movement = Vector3(screenX.toFloat(), screenY.toFloat(), 0f)
        movement = worldRenderer.cam!!.unproject(movement)

        val touchArea = Rectangle((game.world.player!!.bounds.x-20.0).toFloat(),game.world.player!!.bounds.y-5,
                game.world.player!!.bounds.width+20,game.world.player!!.bounds.height+10)

        //Set limits
        if (movement.x<4) {
            movement.x = 4f
        } else if (movement.x>100) {
            movement.x = 100f
        }

        if (touchArea.contains(movement.x,movement.y)) {
            game.world.player!!.update(movement, movement)
        }

        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

}