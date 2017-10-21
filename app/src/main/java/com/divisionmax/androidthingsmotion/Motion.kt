package com.divisionmax.androidthingsmotion

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManagerService
import CustomCamera
import android.graphics.Bitmap
import android.os.Handler
import android.widget.ImageView

class Motion : Activity() {

    private val TAG: String = "MotionActivity"
    private val MOTION_PIN: String = "GPIO_35"
    private val LED_PIN: String = "GPIO_174"

    private var motionSensorGpio: Gpio? = null
    private var ledGpio: Gpio? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion)
        initCamera()
        imageView = findViewById(R.id.img_view)
        motionSensorGpio = PeripheralManagerService().openGpio(MOTION_PIN)
        ledGpio = PeripheralManagerService().openGpio(LED_PIN)
        ledGpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        ledGpio?.value = true

        flashLed()

        if (motionSensorGpio == null){
            Log.d(TAG, "No motion sensor available")
        }else {
            Log.d(TAG, "There is a motion sensor")
        }
        motionSensorGpio?.setDirection(Gpio.DIRECTION_IN)
        motionSensorGpio?.setActiveType(Gpio.ACTIVE_HIGH)
        motionSensorGpio?.setEdgeTriggerType(Gpio.EDGE_BOTH)
        motionSensorGpio?.registerGpioCallback(object : GpioCallback() {
            override fun onGpioEdge(gpio: Gpio): Boolean {
                if (gpio.value) {
                    Log.i(TAG, String.format("Sensor value, %s", gpio.value))
                    camera?.takePicture()
                } else {
                    Log.i(TAG, "No sensor value available")
                }

                return true
            }
        })
    }

    private var camera: CustomCamera? = null

    private fun initCamera() {
        camera = CustomCamera.getInstance()
        if (camera == null){
            Log.d(TAG, "Camera not available")
        }
        camera?.initializeCamera(this, Handler(), imageAvailableListener)
    }

    private val imageAvailableListener = object: CustomCamera.ImageCapturedListener {
        override fun onImageCaptured(bitmap: Bitmap) {
            imageView?.setImageBitmap(bitmap)
            //motionViewModel.uploadMotionImgae(bitmap)
        }
    }

    private fun flashLed() {
//        txtValue?.postDelayed({
//            val b = ledGpio?.value;
//            ledGpio?.value = !b!!
//            flashLed()
//        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        motionSensorGpio?.close()
        ledGpio?.close()
 }
}
