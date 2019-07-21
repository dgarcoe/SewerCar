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


/**
 * Created by Daniel on 23/06/2019.
 */
class PlayingScreen (val game: SewerCarGame): Screen, InputProcessor {

    lateinit var worldRenderer : WorldRenderer

    var elapsed: Float = 0.0f

    val SEWER_GENERATION_TIME = 2f

    override fun hide() {

    }

    override fun show() {
        worldRenderer = WorldRenderer(game.world)
        Gdx.input.setInputProcessor(this);

    }

    override fun render(delta: Float) {
        elapsed += delta

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

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

        worldRenderer.render()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

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