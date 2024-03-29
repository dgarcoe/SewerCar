package com.dgarcoe.sewercar.screens

import aurelienribon.tweenengine.TweenManager
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
import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.Timeline
import aurelienribon.tweenengine.Tween.registerAccessor
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut
import com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.dgarcoe.sewercar.ui.tween.ActorAccessor
import com.badlogic.gdx.scenes.scene2d.actions.Actions.run
import com.dgarcoe.sewercar.sounds.SFXManager


/**
 * Created by Daniel on 23/06/2019.
 */
class MainMenuScreen (val game: SewerCarGame, val skin: Skin,
                      val fontTitle: BitmapFont, val sfxManager: SFXManager): Screen, InputProcessor {

    private val WIDTH_CAMERA = 128
    private val HEIGHT_CAMERA = 256

    private val WIDTH_BUTTON_PERCENT = 0.45f
    private val HEIGHT_BUTTON_PERCENT = 0.05f

    lateinit var stage: Stage
    lateinit var table: Table

    private var tweenManager: TweenManager? = null

    private var batch: SpriteBatch? = null
    private var textureBg: Texture? = null
    private var textureSewer: Texture? = null
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


        batch = SpriteBatch()

        textureBg = Texture(Gdx.files.internal("bg/road.png"))
        textureSewer = Texture(Gdx.files.internal("sewers/basic_sewer.png"))

        stage = Stage()
        table = Table(skin)

        table.setFillParent(true)
        Gdx.input.inputProcessor = stage
    }

    private fun setStage() {

        val headingStyle = Label.LabelStyle()
        headingStyle.font = fontTitle

        val heading = Label("Sewer Car", headingStyle)

        val buttonStartGame = TextButton("Play", skin)
        buttonStartGame.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                game.startGame()
            }
        })
        buttonStartGame.pad(15f)

        val buttonSettings = TextButton("Settings", skin)
        buttonSettings.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {

            }
        })
        buttonSettings.pad(15f)

        val buttonExit = TextButton("Exit", skin)
        buttonExit.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                sfxManager.dispose()
                stage.addAction(sequence(fadeOut(0.5f), run(Runnable { Gdx.app.exit() })))
            }
        })
        buttonExit.pad(15f)

        val buttonWidth = Gdx.graphics.width*WIDTH_BUTTON_PERCENT
        val buttonHeight = Gdx.graphics.height*HEIGHT_BUTTON_PERCENT

        table.add(heading).spaceBottom(100f).expandX().row()
        table.add(buttonStartGame).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()
        table.add(buttonSettings).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()
        table.add(buttonExit).width(buttonWidth).height(buttonHeight).spaceBottom(15f).row()

        stage.addActor(table)
        stageAnimations(heading,buttonStartGame,buttonSettings,buttonExit)
    }

    private fun stageAnimations(heading: Label, buttonPlay: TextButton, buttonSettings: TextButton,
                                buttonExit: TextButton) {

        //Creating animations with Tween engine
        tweenManager = TweenManager()
        registerAccessor(Actor::class.java, ActorAccessor())

        //Heading and buttons fade-in
        Timeline.createSequence().beginSequence()
                .push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0f))
                .push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0f))
                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0f))
                .push(Tween.from(heading, ActorAccessor.ALPHA, .5f).target(0f))
                .push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .1f).target(1f))
                .push(Tween.to(buttonSettings, ActorAccessor.ALPHA, .1f).target(1f))
                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .1f).target(1f))
                .end().start(tweenManager)

        //Table fade-in
        Tween.from(table, ActorAccessor.ALPHA, .5f).target(0f).start(tweenManager)
        Tween.from(table, ActorAccessor.Y, .5f).target((Gdx.graphics.height / 8).toFloat()).start(tweenManager)

        tweenManager!!.update(Gdx.graphics.deltaTime)

    }


    override fun hide() {
        dispose()
        Gdx.input.inputProcessor = null
    }

    override fun show() {
        println("Starting main menu")
        initStage()
        setStage()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cam!!.update()

        batch!!.setProjectionMatrix(cam!!.combined)
        batch!!.begin()
        batch!!.draw(textureBg,0f,0f,0,0, width, height)
        batch!!.draw(textureSewer,80f,20f,0,0, 22, 22)
        batch!!.draw(textureSewer,10f,100f,0,0, 22, 22)
        batch!!.draw(textureSewer,40f,200f,0,0, 22, 22)
        batch!!.end()

        stage.act(delta)
        stage.draw()
        tweenManager!!.update(delta);
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        viewPort!!.update(width, height)
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
        batch!!.setProjectionMatrix(cam!!.combined)
    }

    override fun dispose() {
        Gdx.app.log("GAME MENU", "Disposing main menu things");
        textureBg!!.dispose()
        textureSewer!!.dispose()
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