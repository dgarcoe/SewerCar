package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.Game
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

/**
 * Created by Daniel on 29/06/2019.
 */
class Sewer(position: Vector2, bounds: Rectangle, boundsSize: Vector2) :
        GameObject(position,bounds, boundsSize) {

    val BOUNDX = 32
    val BOUNDY = 32

    fun update(positionUpdate: Vector3, boundsUpdate: Vector3) {

        position.x = positionUpdate.x
        bounds.x = boundsUpdate.x

        if (position.y < -32f) {
            alive = false
        }
    }

}