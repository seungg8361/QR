package com.example.qr

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun startBarcodeReader(view:View){
        IntentIntegrator(this).initiateScan()
    }

    fun startBarcodeReaderCustom(view: View){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("QR 코드를 스캔하여 주세요")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    fun startBarcodeReaderCustomActivity(view: View){
        val integrator = IntentIntegrator(this)
        integrator.setBarcodeImageEnabled(true)
        integrator.captureActivity = MyBarcodeReaderActivity::class.java
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode,data);
        if (result != null){
            if (result.contents != null){
                Toast.makeText(this, "scanned: ${result.contents} format: ${result.formatName}", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
            if (result.barcodeImagePath != null){
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                scannedBitmap.setImageBitmap(bitmap)
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}