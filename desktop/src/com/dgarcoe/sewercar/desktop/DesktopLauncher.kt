package com.dgarcoe.sewercar.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.dgarcoe.sewercar.SewerCarGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.title = "Sewer Car"
        config.width = 640
        config.height = 1280
        LwjglApplication(SewerCarGame(), config)
    }
}
