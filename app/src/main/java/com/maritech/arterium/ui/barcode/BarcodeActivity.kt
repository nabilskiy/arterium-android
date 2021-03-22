package com.maritech.arterium.ui.barcode

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bobekos.bobek.scanner.BarcodeView
import com.maritech.arterium.R
import com.maritech.arterium.data.sharePref.Pref
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

class BarcodeActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        val programId = Pref.getInstance().getDrugProgramId(this)

        disposable = findViewById<BarcodeView>(R.id.barcodeView)
                .setBeepSound(true)
                .drawOverlay()
                .getObservable()
                .filter {
                    val mask: String =
                            if (programId == 2) {
                                "md0064"
                            } else {
                                "md0009"
                            }

                    it.displayValue
                            .toLowerCase(Locale.getDefault())
                            .startsWith(mask) && it.displayValue.length == 13
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val barcode: String = it.displayValue

                    val returnIntent = Intent()
                    returnIntent.putExtra("result", barcode)

                    setResult(RESULT_OK, returnIntent)
                    finish()

                }, {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                })
    }
}