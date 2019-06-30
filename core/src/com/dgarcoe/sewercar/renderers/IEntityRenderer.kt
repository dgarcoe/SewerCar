package com.dgarcoe.sewercar.renderers

import com.dgarcoe.sewercar.entities.GameObject

/**
 * Created by Daniel on 30/06/2019.
 */
interface IEntityRenderer {

    fun loadEntityTextures()
    fun createEntityAnimations()
    fun drawEntity(gameObject: GameObject)
    fun dispose()
}