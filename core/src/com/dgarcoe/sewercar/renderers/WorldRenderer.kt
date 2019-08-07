package com.dgarcoe.sewercar.renderers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.dgarcoe.sewercar.World
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.dgarcoe.sewercar.entities.Sewer
import com.badlogic.gdx.graphics.Pixmap



/**
 * Created by Daniel on 30/06/2019.
 */
class WorldRenderer(var world: World) {

    val DEFAULT_CAMERA_SPEED = 150f
    private val WIDTH_CAMERA = 128
    private val HEIGHT_CAMERA = 256
    var cam: OrthographicCamera? = null
    var viewPort: Viewport? = null

    private var batch: SpriteBatch? = null
    private var texture: Texture? = null
    private var originY : Float = 0f
    private val width: Int = 128
    private val height : Int = 256

    private var playerRenderer: PlayerRenderer? = null
    private var sewerRenderer: SewerRenderer? = null

    var speed: Float = 0.0f

    /** for debug rendering  */
    var debugRenderer = ShapeRenderer()

    init {

        val aspectRatio = Gdx.graphics.height/Gdx.graphics.width

        cam = OrthographicCamera()
        viewPort = FitViewport(WIDTH_CAMERA.toFloat()*aspectRatio, HEIGHT_CAMERA.toFloat(),cam)
        (viewPort as FitViewport).apply()
        cam!!.setToOrtho(false, WIDTH_CAMERA.toFloat(), HEIGHT_CAMERA.toFloat())
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)

        cam!!.update()

        batch = SpriteBatch()

        texture = Texture(Gdx.files.internal("bg/road.png"))
        texture!!.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.Repeat)
        playerRenderer = PlayerRenderer(batch!!)
        playerRenderer!!.loadEntityTextures()
        sewerRenderer = SewerRenderer(batch!!)
        sewerRenderer!!.loadEntityTextures()
    }

    fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        cam!!.position.set(cam!!.position.x, cam!!.position.y, 0f)
        cam!!.update()

        batch!!.begin()
        batch!!.setProjectionMatrix(cam!!.combined)
        batch!!.enableBlending()

        batch!!.draw(texture,0f,0f,0, originY.toInt(), width, height)
        val sewerIterator = world.sewers.iterator()
        for (sewer in sewerIterator) {
            sewer.update()

            if (sewer.alive) {
                sewerRenderer!!.drawEntity(sewer)
                sewer.position.y -= speed*delta
                sewer.bounds.y -= speed*delta
            } else {
                if (sewer.collidable) {
                    world.player!!.score += sewer.points
                }
                sewerIterator.remove()
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

        originY = (originY-speed*delta) % HEIGHT_CAMERA

}

    fun resize(width: Int, height: Int)  {
        viewPort!!.update(width, height)
        cam!!.position.set((WIDTH_CAMERA/2).toFloat(), (HEIGHT_CAMERA/2).toFloat(),0f)
        batch!!.setProjectionMatrix(cam!!.combined)

    }

    fun dispose() {
        playerRenderer!!.dispose()
        sewerRenderer!!.dispose()
        texture!!.dispose()
    }

    fun startMoving() {
        speed = DEFAULT_CAMERA_SPEED
    }

    fun initMoving() {
        speed = 0f
    }

    fun stopMoving() {
        speed = 0f
    }

}