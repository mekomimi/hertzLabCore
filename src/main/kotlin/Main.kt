import com.mekomimi.hertzlab.ComplexWaveGenerator
import com.mekomimi.hertzlab.DogWhistle
import com.mekomimi.hertzlab.Recorder
import com.mekomimi.hertzlab.WaveGenerator

fun main() {
    //dogWhistle()

    record()
}

fun record(){
    val recorder= Recorder()
    recorder.start()

    Thread.sleep(5000)

    recorder.stop()
}

fun dogWhistle(){
    val dogWhistle= DogWhistle()
    dogWhistle.init()
    dogWhistle.start()
}

fun baseWave(){
    val waveGen = WaveGenerator()

    waveGen.init(17000.0)
    waveGen.start()

    println("输入频率：")
    var line: String?
    while(readLine().also { line=it }!=null){
        var hertz=line?.toDouble()

        if (hertz != null) {
            waveGen.setFrequency(hertz)
        }
    }
}

fun complex(){
    val generator= ComplexWaveGenerator()
    generator.init()
    generator.start()
}