package com.dgarcoe.sewercar.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
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
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar


/**
 * Created by Daniel on 23/06/2019.
 */
class PlayingScreen (val game: SewerCarGame, val skin: Skin): Screen, InputProcessor {

    private val HEALTHBAR_HEIGHT_PERCENT = 0.02f
    private val HEALTHBAR_WIDTH_PERCENT = 0.25f
    private val HEALTHBAR_PAINT_PERCENT_TOP = 0.07f
    private val HEALTHBAR_PAINT_PERCENT_LEFT = 0.01f

    val SEWER_GENERATION_TIME = 1.5f

    lateinit var worldRenderer : WorldRenderer

    var elapsed: Float = 0.0f

    lateinit var stage: Stage
    lateinit var table: Table

    var score: Label? = null

    var progressBarStyle: ProgressBar.ProgressBarStyle? = null
    var healthBar: ProgressBar? = null

    override fun hide() {
        dispose()
        Gdx.input.inputProcessor = null
    }

    private fun initStage() {
        stage = Stage()
        table = Table(skin)

        table.setFillParent(true)

        configureHealthbar()
    }

    private fun configureHealthbar() {

        progressBarStyle = ProgressBar.ProgressBarStyle()

        var pixmap = Pixmap(Math.round(Gdx.graphics.width * HEALTHBAR_WIDTH_PERCENT),
                Math.round(Gdx.graphics.height * HEALTHBAR_HEIGHT_PERCENT), Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.RED)
        pixmap.fill()
        var drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()

        progressBarStyle!!.background = drawable

        pixmap = Pixmap(0,
                Math.round(Gdx.graphics.height * HEALTHBAR_HEIGHT_PERCENT), Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GREEN)
        pixmap.fill()
        drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()

        progressBarStyle!!.knob = drawable

        pixmap = Pixmap(Math.round(Gdx.graphics.width * HEALTHBAR_WIDTH_PERCENT),
                Math.round(Gdx.graphics.height * HEALTHBAR_HEIGHT_PERCENT), Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GREEN)
        pixmap.fill()
        drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()

        progressBarStyle!!.knobBefore = drawable

        healthBar = ProgressBar(0.0f, 100.0f, 5f, false, progressBarStyle);
        healthBar!!.setValue(100.0f);
        healthBar!!.setAnimateDuration(0.25f);
        val paintPosY = Gdx.graphics.height - Gdx.graphics.height * HEALTHBAR_PAINT_PERCENT_TOP
        val paintPosX = Gdx.graphics.width * HEALTHBAR_PAINT_PERCENT_LEFT
        healthBar!!.setBounds(paintPosX, paintPosY, Gdx.graphics.width * HEALTHBAR_WIDTH_PERCENT,
                Gdx.graphics.height * HEALTHBAR_HEIGHT_PERCENT)

    }

    private fun setStage() {

        Gdx.app.log("LOL","HEIGHT: "+Gdx.graphics.height)
        score = Label("Score: " + String.format("%06d", game.world.player!!.score), skin)
        score!!.setColor(126f, 1f, 1f, 1f)

        if (Gdx.graphics.height > 1500) {
            score!!.setFontScale(4f)
        } else {
            score!!.setFontScale(2f)
        }

        table.top()
        table.add(score).expandX().center().row()
        stage.addActor(table)
        stage.addActor(healthBar)
    }

    private fun updateHUD() {

        score!!.setText("Score: " + String.format("%06d", game.world.player!!.score))
        healthBar!!.setValue(game.world.player!!.health)
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