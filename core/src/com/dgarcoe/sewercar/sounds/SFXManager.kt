package com.dgarcoe.sewercar.sounds

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound

/**
 * Created by Daniel on 07/08/2019.
 */
class SFXManager {

    //Laser effects
    enum class SFX {
        BLIP_COUNTDOWN, BLIP_GO, SEWER_HIT, BROKEN_CAR, CAR_ENGINE
    }

    private var blipCountdown: Sound? = null
    private var blipGo: Sound? = null
    private var sewerHit: Sound? = null
    private var brokenCar: Sound? = null
    private var carEngine: Sound? = null


    fun loadEffects() {
        blipCountdown = Gdx.audio.newSound(Gdx.files.internal("sfx/sfx_sounds_Blip1.wav"))
        blipGo = Gdx.audio.newSound(Gdx.files.internal("sfx/sfx_sounds_Blip5.wav"))
        sewerHit = Gdx.audio.newSound(Gdx.files.internal("sfx/sfx_vehicle_breaks.wav"))
        brokenCar = Gdx.audio.newSound(Gdx.files.internal("sfx/sfx_exp_long4.wav"))
        carEngine = Gdx.audio.newSound(Gdx.files.internal("sfx/sfx_vehicle_carloop2.wav"))
    }

    fun playEffect(effect: SFX) {
        when(effect) {
            SFX.BLIP_COUNTDOWN -> blipCountdown!!.play()
            SFX.BLIP_GO -> blipGo!!.play()
            SFX.SEWER_HIT -> sewerHit!!.play()
            SFX.BROKEN_CAR -> brokenCar!!.play()
            SFX.CAR_ENGINE -> {
                val id = carEngine!!.play()
                carEngine!!.setVolume(id,0.2f)
                carEngine!!.setLooping(id,true)
            }
        }
    }

    fun stopEffect(effect: SFX) {
        when(effect) {
            SFX.CAR_ENGINE -> carEngine!!.stop()
        }
    }

    fun dispose() {
        blipCountdown!!.dispose()
        blipGo!!.dispose()
        sewerHit!!.dispose()
        brokenCar!!.dispose()
    }

}