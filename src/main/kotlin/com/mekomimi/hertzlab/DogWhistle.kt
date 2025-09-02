package com.mekomimi.hertzlab

import com.jsyn.JSyn
import com.jsyn.Synthesizer
import com.jsyn.unitgen.LineOut
import com.jsyn.unitgen.SineOscillator

//生成用于驱狗的声波
class DogWhistle {
    private var synth:Synthesizer?=null
    private var sine: SineOscillator? = null
    private var lineOut:LineOut?=null

    @Volatile private var ifRun:Boolean=false

    fun init(){
        synth=JSyn.createSynthesizer()
        sine=SineOscillator().apply{
            frequency.set(22000.0)
            amplitude.set(0.4)
        }
        lineOut= LineOut()

        synth?.apply {
            add(sine)
            add(lineOut)
        }

        sine?.output?.connect(lineOut?.input)
    }

    fun start(){
        ifRun=true
        synth?.start()
        lineOut?.start()

        // 用一个调制器慢慢拉高/降低频率
        Thread ({
            var f = 18000.0
            var step = 50.0
            while (ifRun) {
                sine?.frequency?.set(f)
                f += step
                if (f > 22000 || f < 18000) step = -step
                Thread.sleep(50) // 每 50ms 调整一次频率
            }
        }, "FrequencyModThread").apply { isDaemon = true }.start()
    }

    fun stop(){
        ifRun=false
        synth?.stop()
        lineOut?.stop()
    }
}