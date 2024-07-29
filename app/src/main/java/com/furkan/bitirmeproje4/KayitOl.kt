package com.furkan.bitirmeproje4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.furkan.bitirmeproje4.databinding.ActivityKayitOlBinding

class KayitOl : AppCompatActivity() {
    private lateinit var binding: ActivityKayitOlBinding
    private lateinit var editTextNewUsername: EditText
    private lateinit var editTextNewPassword: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var buttonRegister: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var portfolioDbHelper: PortfolioDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKayitOlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGirisSayfasinaDon.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        editTextNewUsername = findViewById(R.id.epostaKayit)
        editTextNewPassword = findViewById(R.id.parolaKayit)
        editTextPhone = findViewById(R.id.telefonNumarasıkayit)
        buttonRegister = findViewById(R.id.btnUyeOl)

        dbHelper = DatabaseHelper(this)
        portfolioDbHelper = PortfolioDatabaseHelper(this)

        buttonRegister.setOnClickListener {
            val newUsername = editTextNewUsername.text.toString().trim()
            val newPassword = editTextNewPassword.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val riskTolerance = intent.getStringExtra("risk_tolerance") ?: ""

            if (newUsername.isEmpty() || newPassword.isEmpty() || phone.isEmpty() || riskTolerance.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val id = dbHelper.addUser(newUsername, newPassword, phone, riskTolerance)
            if (id > 0) {
                val userId = id // Kullanıcı kimliğini burada alıyoruz
                Toast.makeText(this, "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                // Kullanıcıya uygun portföyü seç ve veritabanına ekle
                val selectedPortfolio = portfolioDbHelper.getRandomPortfolioByRiskLevel(riskTolerance)
                if (selectedPortfolio != null) {
                    val portfolioId = portfolioDbHelper.addPortfolio(selectedPortfolio.name, selectedPortfolio.assetAllocation, selectedPortfolio.riskLevel)
                    if (portfolioId > 0) {
                        Log.d("Selected Portfolio", selectedPortfolio.toString())

                        // Kullanıcı ve portföy arasındaki ilişkiyi oluşturun
                        val userPortfolioId = dbHelper.addUserPortfolio(userId, portfolioId)
                        if (userPortfolioId > 0) {
                            Log.d("User Portfolio", "User Portfolio added successfully")
                        } else {
                            Toast.makeText(this, "User Portfolio could not be added", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Portföy eklenirken hata oluştu", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Uygun portföy bulunamadı", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {

                Toast.makeText(this, "Kayıt başarısız", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
