package com.mekomimi.hertzlab

import com.jsyn.JSyn
import com.jsyn.Synthesizer
import com.jsyn.unitgen.SineOscillator
import com.jsyn.util.WaveRecorder
import java.io.File

//将生成的声波录制下来
class Recorder {
    private val synth:Synthesizer= JSyn.createSynthesizer()
    private val sine:SineOscillator=SineOscillator().apply{ frequency.set(10000.0); amplitude.set(0.4) }
    private val recorder: WaveRecorder =WaveRecorder(synth, File("dog_whistle.wav"))

    private var ifRun:Boolean=false

    init {
        synth.apply{
            add(sine)
        }

        sine.output.connect(recorder.input)
    }

    fun start(){
        ifRun=true
        synth.start()
        recorder.start()

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
        recorder.stop()
        synth.stop()
        ifRun=false
    }
}