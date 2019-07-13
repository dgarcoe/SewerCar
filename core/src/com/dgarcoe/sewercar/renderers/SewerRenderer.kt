package com.dgarcoe.sewercar.renderers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dgarcoe.sewercar.entities.GameObject

/**
 * Created by Daniel on 13/07/2019.
 */
class SewerRenderer(val batch: SpriteBatch) : IEntityRenderer {

    private var texture: Texture? = null


    override fun loadEntityTextures() {
        texture = Texture(Gdx.files.internal("sewers/basic_sewer.png"));
    }

    override fun createEntityAnimations() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawEntity(gameObject: GameObject) {
        batch.draw(texture,gameObject.position.x,gameObject.position.y)
    }

    override fun dispose() {
        texture!!.dispose()
    }

}