package com.dgarcoe.sewercar.renderers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.dgarcoe.sewercar.World
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.dgarcoe.sewercar.entities.Sewer

/**
 * Created by Daniel on 30/06/2019.
 */
class WorldRenderer(var world: World) {

    private val DEFAULT_CAMERA_SPEED = 2f
    private val WIDTH_CAMERA = 128
    private val HEIGHT_CAMERA = 256
    var cam: OrthographicCamera? = null

    private var batch: SpriteBatch? = null
    private var texture: Texture? = null
    private var originY : Float = 0f
    private val width: Int = 128
    private val height : Int = 256

    private var playerRenderer: PlayerRenderer? = null
    private var sewerRenderer: SewerRenderer? = null

    /** for debug rendering  */
    var debugRenderer = ShapeRenderer()

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
        playerRenderer = PlayerRenderer(batch!!)
        playerRenderer!!.loadEntityTextures()
        sewerRenderer = SewerRenderer(batch!!)
        sewerRenderer!!.loadEntityTextures()

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
        for (sewer: Sewer in world.sewers) {

            if (sewer.alive) {
                sewerRenderer!!.drawEntity(sewer)
                sewer.position.y -= DEFAULT_CAMERA_SPEED
                sewer.bounds.y -= DEFAULT_CAMERA_SPEED
            } else {
                Gdx.app.log("LOL","REMOVED")
                world.sewers.remove(sewer)
            }
        }
        playerRenderer!!.drawEntity(world.player!!)
        batch!!.end()

        //Debug renderer to check bounds
        /*debugRenderer.setProjectionMatrix(cam!!.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color(1f, 1f, 0f, 1f));
        debugRenderer.rect(world.player!!.bounds.x, world.player!!.bounds.y, world.player!!.bounds.width, world.player!!.bounds.height)
        for (sewer: Sewer in world.sewers) {
            debugRenderer.rect(sewer.bounds.x,sewer.bounds.y,sewer.bounds.width, sewer.bounds.height)
        }
        debugRenderer.end()*/

        originY-=DEFAULT_CAMERA_SPEED
}

}