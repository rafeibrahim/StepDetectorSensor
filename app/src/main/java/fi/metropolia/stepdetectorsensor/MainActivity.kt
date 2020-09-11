package fi.metropolia.stepdetectorsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.spi.FileTypeDetector

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var stepDetector: Sensor
    private var isDetectorSensorPresent = false
    private var stepsDetected: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorTxtTitle.text = getString(R.string.sensor_available)
            stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
            isDetectorSensorPresent = true
        } else {
            sensorTxtTitle.text = getString(R.string.sensor_not_available)
        }
    }

    @Override
    override fun onResume() {
        super.onResume()

        if (isDetectorSensorPresent) {
            sensorManager.registerListener(this, stepDetector, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    @Override
    override fun onPause() {
        super.onPause()

        if(isDetectorSensorPresent) {
            sensorManager.unregisterListener(this, stepDetector)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if (p0?.sensor == stepDetector) {
            stepsDetected = (stepsDetected + p0.values[0].toInt())
            stepsTextView.text = stepsDetected.toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}