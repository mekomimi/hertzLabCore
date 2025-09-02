package com.mekomimi.hertzlab

import com.jsyn.JSyn
import com.jsyn.Synthesizer
import com.jsyn.unitgen.LineOut
import com.jsyn.unitgen.SineOscillator

//最简单的声波生成器
class WaveGenerator {
    private var synth: Synthesizer? = null
    private var osc: SineOscillator? = null
    private var lineOut: LineOut? = null
    private var isRunning = false

    /**
     * 初始化合成器和振荡器
     */
    fun init(frequency: Double = 440.0, amplitude: Double = 0.5) {
        synth = JSyn.createSynthesizer()
        osc = SineOscillator().apply {
            this.frequency.set(frequency)
            this.amplitude.set(amplitude)
        }
        lineOut = LineOut()

        synth?.apply {
            add(osc)
            add(lineOut)
        }

        osc?.output?.connect(0, lineOut?.input, 0)
        osc?.output?.connect(0, lineOut?.input, 1)
    }

    /**
     * 启动声波
     */
    fun start() {
        if (isRunning) return
        synth?.start()
        lineOut?.start()
        isRunning = true
    }

    /**
     * 停止声波
     */
    fun stop() {
        if (!isRunning) return
        synth?.stop()
        isRunning = false
    }

    /**
     * 修改频率
     */
    fun setFrequency(frequency: Double) {
        osc?.frequency?.set(frequency)
    }

    /**
     * 修改振幅
     */
    fun setAmplitude(amplitude: Double) {
        osc?.amplitude?.set(amplitude)
    }
}