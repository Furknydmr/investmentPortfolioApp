package com.furkan.bitirmeproje4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.furkan.bitirmeproje4.databinding.ActivityAccountDetailsBinding

class AccountDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountDetailsBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        val currentUser = getCurrentUser()
        currentUser?.let {
            binding.tvUsername.text = it.username
            binding.tvPhone.text = it.phone
            binding.tvTolerans.text = it.tolerans
        }

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun getCurrentUser(): User? {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)
        val currentUser = if (username != null) {
            dbHelper.getUserByUsername(username)
        } else {
            null
        }

        // Kullanıcı adını ve telefon numarasını doğrudan kullanıcıdan alıp formatlayarak geri döndürün
        return currentUser?.let {
            val formattedUsername = "Kullanıcı Adı: ${it.username}"
            val formattedPhone = "Telefon: ${it.phone}"
            val formattedTolerans = "tolerans: ${it.tolerans}"
            User(it.id, formattedUsername, it.password, formattedPhone, formattedTolerans)
        }
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
