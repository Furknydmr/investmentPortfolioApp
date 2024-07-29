package com.furkan.bitirmeproje4

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.furkan.bitirmeproje4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var checkBoxRememberMe: CheckBox
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextUsername = findViewById(R.id.etGirisEmail)
        editTextPassword = findViewById(R.id.etGirisParola)
        buttonLogin = findViewById(R.id.btnGirisYap)
        checkBoxRememberMe = findViewById(R.id.beniHatırla)

        binding.btnKayitOl.setOnClickListener{
            val intent = Intent(applicationContext, RiskTolerans::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnDatabase.setOnClickListener{
            val intent = Intent(applicationContext, DatabaseAnasayfa::class.java)
            startActivity(intent)
        }
        binding.btnSifremiUnuttum.setOnClickListener {
            val intent = Intent(applicationContext, UserSifremiUnuttum::class.java)
            startActivity(intent)
        }
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        dbHelper = DatabaseHelper(this)
        checkRememberedUser()
        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Lütfen kullanıcı adı ve şifre giriniz", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Giriş başarılı", Toast.LENGTH_SHORT).show()
                if (checkBoxRememberMe.isChecked) {
                    saveUserCredentials(username)
                }
                navigateToHome()
            } else {
                Toast.makeText(this, "Kullanıcı adı veya şifre hatalı", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun checkRememberedUser() {
        val username = sharedPreferences.getString("username", null)
        if (username != null) {
            Toast.makeText(this, "Tekrar hoşgeldiniz, $username", Toast.LENGTH_SHORT).show()
            navigateToHome()
        }
    }
    private fun saveUserCredentials(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }
    private fun navigateToHome() {
        // Ana sayfaya geçiş yap
        val intent = Intent(this, AnaSayfa::class.java)
        startActivity(intent)
        finish()
    }
}
