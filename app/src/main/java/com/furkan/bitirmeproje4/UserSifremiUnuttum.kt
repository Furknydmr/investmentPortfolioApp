package com.furkan.bitirmeproje4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.furkan.bitirmeproje4.databinding.ActivityUserSifremiUnuttumBinding

class UserSifremiUnuttum : AppCompatActivity() {
    private lateinit var binding: ActivityUserSifremiUnuttumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSifremiUnuttumBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}