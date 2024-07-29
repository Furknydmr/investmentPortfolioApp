package com.furkan.bitirmeproje4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class RiskTolerans : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_tolerans)

        dbHelper = DatabaseHelper(this)

        val hesaplaButton: Button = findViewById(R.id.btnRiskHesap)

        hesaplaButton.setOnClickListener {
            val soru1Group: RadioGroup = findViewById(R.id.radioGroupSoru1)
            val soru2Group: RadioGroup = findViewById(R.id.radioGroupSoru2)
            val soru3Group: RadioGroup = findViewById(R.id.radioGroupSoru3)
            val soru4Group: RadioGroup = findViewById(R.id.radioGroupSoru4)

            val soru1Tag = soru1Group.checkedRadioButtonId?.let { findViewById<RadioButton>(it).tag.toString().toInt() }
            val soru2Tag = soru2Group.checkedRadioButtonId?.let { findViewById<RadioButton>(it).tag.toString().toInt() }
            val soru3Tag = soru3Group.checkedRadioButtonId?.let { findViewById<RadioButton>(it).tag.toString().toInt() }
            val soru4Tag = soru4Group.checkedRadioButtonId?.let { findViewById<RadioButton>(it).tag.toString().toInt() }

            if (soru1Tag != null && soru2Tag != null && soru3Tag != null && soru4Tag != null) {
                val toplamPuan = soru1Tag + soru2Tag + soru3Tag + soru4Tag

                val riskTolerans = when (toplamPuan) {
                    in 4..9 -> "Dusuk"
                    in 10..14 -> "Orta"
                    in 15..19 -> "Yuksek"
                    else -> "Cok Yuksek"
                }

                val intent = Intent(applicationContext, KayitOl::class.java)
                intent.putExtra("risk_tolerance", riskTolerans)
                startActivity(intent)
                finish()
            } else {
                // Kullanıcının tüm soruları cevapladığından emin olunmamışsa uygun bir mesaj gösterme
            }
        }
    }
}
