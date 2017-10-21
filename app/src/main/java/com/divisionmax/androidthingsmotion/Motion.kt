package com.divisionmax.androidthingsmotion

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class Motion : Activity() {

    private val tag: String = "MotionActivity"
    private val MOTION_PIN: String = "GPIO_35"
    private val LED_PIN: String = "GPIO_174"
    private var motionSensorGpio: Gpio? = null
    private var ledGpio: Gpio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)
        var txtValue = findViewById<TextView>(R.id.txtvw_value)
//        motionSensorGpio = PeripheralManagerService().openGpio(MOTION_PIN)
        ledGpio = PeripheralManagerService().openGpio(LED_PIN)
        ledGpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        ledGpio?.value = true

//        if (motionSensorGpio == null){
//            Log.d(tag, "No motion sensor available")
//        }else {
//            Log.d(tag, "There is a motion sensor")
//        }
//        motionSensorGpio?.setDirection(Gpio.DIRECTION_IN)
//        motionSensorGpio?.setActiveType(Gpio.ACTIVE_HIGH)
//        motionSensorGpio?.setEdgeTriggerType(Gpio.EDGE_BOTH)
//        motionSensorGpio?.registerGpioCallback(object : GpioCallback() {
//            override fun onGpioEdge(gpio: Gpio): Boolean {
//                if (gpio.value) {
//                    Log.i(tag, String.format("Sensor value, %s", gpio.value))
//                    txtValue.setText(gpio.value.toString())
//                } else {
//                    Log.i(tag, "No sensor value available")
//                    txtValue.setText(gpio.value.toString())
//                }
//
//                return true
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
//        motionSensorGpio?.close()
        ledGpio?.close()
 }
}
