package com.maritech.arterium.ui.barcode

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bobekos.bobek.scanner.BarcodeView
import com.maritech.arterium.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class BarcodeActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        disposable = findViewById<BarcodeView>(R.id.barcodeView)
                .setBeepSound(true)
                .drawOverlay()
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val returnIntent = Intent()
                    returnIntent.putExtra("result", it.displayValue)

                    setResult(RESULT_OK, returnIntent)
                    finish()
                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
    }
}