//package com.dgarcoe.sewercar.controllers
//
//
//import com.dgarcoe.sewercar.World
//import com.dgarcoe.sewercar.entities.PlayerCar
//
//
///**
// * Created by Daniel on 07/07/2019.
// */
//class PlayerController(private val world: World) {
//
//    private var player: PlayerCar? = null
//
//    internal enum class Keys {
//        LEFT, RIGHT
//    }
//
//    init {
//        this.player = world.player!!
//        initKeys()
//    }
//
//    private fun initKeys() {
//        keys.put(Keys.LEFT, false)
//        keys.put(Keys.RIGHT, false)
//    }
//
//    // ** Key presses and touches **************** //
//
//    //PRESSED FUNCTIONS
//
//    fun leftPressed() {
//        keys.get(keys.put(Keys.LEFT, true))
//    }
//
//    fun rightPressed() {
//        keys.get(keys.put(Keys.RIGHT, true))
//    }
//
//    //RELEASED FUNCTIONS
//
//    fun leftReleased() {
//        keys.get(keys.put(Keys.LEFT, false))
//    }
//
//    fun rightReleased() {
//        keys.get(keys.put(Keys.RIGHT, false))
//    }
//
//    /** MAIN UPDATE METHOD  */
//    fun update(delta: Float) {
//
//        processInput()
//        neonGaia.update(delta, camOffsetLeft, camOffsetRight, viewportWidth, viewportHeight, neonGaia.getPosition())
//        for (i in 0 until world.getPlayerBullets().size) {
//            world.getPlayerBullets().get(i).update(delta, camOffsetLeft, camOffsetRight, viewportWidth, viewportHeight, neonGaia.getPosition())
//        }
//    }
//
//    private fun processMovementInput() {
//
//        if (keys[Keys.LEFT]) {
//            neonGaia.getState().moveBack()
//            neonGaia.getVelocity().x = -NeonGaia.SPEED
//        }
//
//        if (keys[Keys.RIGHT]) {
//            neonGaia.getState().moveFront()
//            neonGaia.getVelocity().x = NeonGaia.SPEED
//        }
//
//
//        // need to check if both or none direction are pressed, then Bob is idle
//        if (keys[Keys.LEFT] && keys[Keys.RIGHT] ||
//                !keys[Keys.LEFT] && !keys[Keys.RIGHT]) {
//            neonGaia.getState().stand()
//            neonGaia.getVelocity().x = 0
//            neonGaia.getVelocity().y = 0
//        }
//
//    }
//
//    companion object {
//
//        internal var keys: MutableMap<Keys, Boolean> = HashMap()
//
//        init {
//            keys.put(Keys.LEFT, false)
//            keys.put(Keys.RIGHT, false)
//        }
//    }
//
//}
