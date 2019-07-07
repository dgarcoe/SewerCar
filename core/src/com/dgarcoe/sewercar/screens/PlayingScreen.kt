package com.dgarcoe.sewercar.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.dgarcoe.sewercar.SewerCarGame
import com.dgarcoe.sewercar.renderers.WorldRenderer
import com.badlogic.gdx.math.Vector3



/**
 * Created by Daniel on 23/06/2019.
 */
class PlayingScreen (val game: SewerCarGame): Screen, InputProcessor {

    lateinit var worldRenderer : WorldRenderer

    override fun hide() {

    }

    override fun show() {
        worldRenderer = WorldRenderer(game.world)
        Gdx.input.setInputProcessor(this);

    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

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
        game.world.player!!.update(movement,movement)
        //game.world.player!!.position.x = movement.x

        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

}