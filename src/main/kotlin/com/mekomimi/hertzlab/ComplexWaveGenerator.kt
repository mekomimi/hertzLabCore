package com.mekomimi.hertzlab

import com.jsyn.JSyn
import com.jsyn.Synthesizer
import com.jsyn.unitgen.LineOut
import com.jsyn.unitgen.SineOscillator
import com.jsyn.unitgen.SquareOscillator
import com.jsyn.unitgen.EnvelopeDAHDSR

//复杂声波生成器
class ComplexWaveGenerator {
    private var synth: Synthesizer? = null
    private var sine: SineOscillator? = null
    private var square: SquareOscillator? = null
    private var env: EnvelopeDAHDSR? = null
    private var lineOut: LineOut? = null

    fun init() {
        synth = JSyn.createSynthesizer()
        sine = SineOscillator().apply {
            frequency.set(440.0)
            amplitude.set(0.3)
        }
        square = SquareOscillator().apply {
            frequency.set(880.0)
            amplitude.set(0.1)
        }
        env = EnvelopeDAHDSR()

        lineOut = LineOut()

        synth?.apply {
            add(sine)
            add(square)
            add(env)
            add(lineOut)
        }

        // 混合两个波形到 envelope
        sine?.output?.connect(env?.input)
        square?.output?.connect(env?.input)

        // envelope 输出到左右声道
        env?.output?.connect(0, lineOut?.input, 0)
        env?.output?.connect(0, lineOut?.input, 1)
    }

    fun start() {
        synth?.start()
        lineOut?.start()
        env?.attack?.set(0.1)
        env?.decay?.set(0.2)
        env?.sustain?.set(0.5)
        env?.release?.set(0.3)
        env?.input?.set(1.0) // 触发包络
    }

    fun stop() {
        synth?.stop()
    }
}
