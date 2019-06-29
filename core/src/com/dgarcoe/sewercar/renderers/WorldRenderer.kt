package com.dgarcoe.sewercar.renderers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.dgarcoe.sewercar.World
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch






/**
 * Created by Daniel on 30/06/2019.
 */
class WorldRenderer(world: World) {

    private val DEFAULT_CAMERA_SPEED = 0.5f
    private val WIDTH_CAMERA = 1500
    private val HEIGHT_CAMERA = 2048
    private var cam: OrthographicCamera? = null
    private val camSpeed: Float = 0.toFloat()

    private var batch: SpriteBatch? = null
    private var background: Texture? = null

    init {
      cam = OrthographicCamera(WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.setToOrtho(false, WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
        //world.setCamOffset(0, WIDTH_CAMERA);
        //world.setViewportSize(WIDTH_CAMERA, HEIGHT_CAMERA);
        cam!!.update()

        batch = SpriteBatch()

        background = Texture(Gdx.files.internal("bg/road.png"));

    }

    fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch!!.begin();
        batch!!.enableBlending();
        val originX : Float = 0F
        val originY : Float = 0F
        val length : Float = 1500F
        val height : Float = 2048F
        batch!!.draw(background,originX,originY,length,height);
        batch!!.end()

    }

}