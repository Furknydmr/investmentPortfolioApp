package com.furkan.bitirmeproje4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furkan.bitirmeproje4.databinding.ActivityAnaSayfaBinding
import com.furkan.bitirmeproje4.databinding.ActivityDatabaseAnasayfaBinding

class DatabaseAnasayfa : AppCompatActivity() {
    private lateinit var binding: ActivityDatabaseAnasayfaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseAnasayfaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAnasayfa.setOnClickListener{
            val intent = Intent(applicationContext, DatabaseAnasayfa::class.java)
            startActivity(intent)
        }
        binding.btnHesabimLogo.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnTumKullanicilar.setOnClickListener{
            val intent = Intent(applicationContext, DatabaseAllUsers::class.java)
            startActivity(intent)
        }
        binding.btnTumPorfoyler.setOnClickListener{
            val intent = Intent(applicationContext, DatabaseAllPortfolio::class.java)
            startActivity(intent)
        }
    }
}