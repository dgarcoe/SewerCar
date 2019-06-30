package com.dgarcoe.sewercar.renderers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.dgarcoe.sewercar.World
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable










/**
 * Created by Daniel on 30/06/2019.
 */
class WorldRenderer(world: World) {

    private val DEFAULT_CAMERA_SPEED = 3f
    private val WIDTH_CAMERA = 1500
    private val HEIGHT_CAMERA = 2048
    private var cam: OrthographicCamera? = null
    private val camSpeed: Float = 0f

    private var batch: SpriteBatch? = null
    private var texture: Texture? = null
    private var background: TextureRegion? = null
    private var originY : Float = 0f
    private val width: Int = 1500
    private val height : Int = 2048

    init {
      cam = OrthographicCamera(WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.setToOrtho(false, WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
        world.camOffsetUp = HEIGHT_CAMERA.toFloat()
        world.camOffsetDown = 0f
        world.viewportHeight = HEIGHT_CAMERA
        world.viewportWidth = WIDTH_CAMERA

        cam!!.update()

        batch = SpriteBatch()

        texture = Texture(Gdx.files.internal("bg/road.png"));
        texture!!.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

    }

    fun render() {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam!!.position.set(cam!!.position.x, cam!!.position.y, 0f);
        cam!!.update();

        batch!!.setProjectionMatrix(cam!!.combined);
        batch!!.begin();
        batch!!.enableBlending();

        batch!!.draw(texture,0f,0f,0,originY.toInt(), width, height);
        batch!!.end()

        originY-=DEFAULT_CAMERA_SPEED
    }

}