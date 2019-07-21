package com.dgarcoe.sewercar.entities

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2



/**
 * Created by Daniel on 30/06/2019.
 */
abstract class GameObject(var position: Vector2, var bounds: Rectangle, var boundsSize: Vector2) {

    enum class ObjectID {
        PLAYER, SEWER, CAR, MISC
    }

    private val objID: ObjectID? = null
    var alive: Boolean = true
    var collidable: Boolean = true
    var health: Float = 0.toFloat()
    var damage: Float = 0.toFloat()
    var stateTime = 0f
    private var remove: Boolean = false

    var velocity = Vector2()

    init {
        velocity.x = 0f
        velocity.y = 0f

        remove = false
    }

    open fun update(delta: Float, camOffsetLeft: Float, camOffsetRight: Float,
                    viewportWidth: Int, viewportHeight: Int, playerPosition: Vector2) {
        stateTime += delta
       position.mulAdd(velocity,delta)
    }

    fun collided(obj: GameObject): Boolean {
        return this.bounds.overlaps(obj.bounds) && collidable
    }

}