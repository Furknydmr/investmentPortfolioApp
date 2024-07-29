package com.furkan.bitirmeproje4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import com.furkan.bitirmeproje4.databinding.ActivityUserOnerilenPortfoyBinding

class UserOnerilenPortfoy : AppCompatActivity() {
    private lateinit var binding: ActivityUserOnerilenPortfoyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOnerilenPortfoyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAnasayfa.setOnClickListener{
            val intent = Intent(applicationContext, AnaSayfa::class.java)
            startActivity(intent)
        }
        selectRandomPortfolio()
        binding.btnHesabimLogo.setOnClickListener {
            showPopupMenu(it)
        }
    }
    private fun showPopupMenu(view: android.view.View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.anasayfa_hesabim_logo, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_account_details -> {
                    val intent = Intent(this, AccountDetailsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
    private fun logout() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clear the saved user data
        editor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    private fun selectRandomPortfolio() {
        val dbHelper = PortfolioDatabaseHelper(this)
        val randomPortfolio = dbHelper.getRandomPortfolioByRiskLevel("belirli_risk_seviyesi")

        if (randomPortfolio != null) {
            // Rastgele seçilen portföyü kullanabilirsiniz
            // Örneğin, portföyün adını yazdırmak için:
            println("Random Portfolio Name: ${randomPortfolio.name}")
        } else {
            // Belirli risk seviyesine sahip portföy bulunamadı
            println("No portfolio found for the specified risk level.")
        }
    }
}