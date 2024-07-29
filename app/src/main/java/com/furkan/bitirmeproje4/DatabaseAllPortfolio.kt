package com.furkan.bitirmeproje4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkan.bitirmeproje4.databinding.ActivityDatabaseAllPortfolioBinding

class DatabaseAllPortfolio : AppCompatActivity() {
    private lateinit var binding: ActivityDatabaseAllPortfolioBinding
    private lateinit var dbHelper: PortfolioDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseAllPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = PortfolioDatabaseHelper(this)
        val portfolios = dbHelper.getAllPortfolios()
        val adapter = PortfolioAdapter(portfolios)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.btnDelPortfolio.setOnClickListener {
            dbHelper.clearAllPortfolios()
            Toast.makeText(this, "Tüm portföyler silindi.", Toast.LENGTH_SHORT).show()
            val newPortfolios = dbHelper.getAllPortfolios()

            adapter.updatePortfolios(newPortfolios)

        }

        binding.btnSavePortfolio.setOnClickListener {
            val name = binding.etPortfolioName.text.toString()
            val assetAllocation = binding.etAssetAllocation.text.toString()
            val riskLevel = binding.etRiskLevel.text.toString()

            if (name.isNotEmpty() && assetAllocation.isNotEmpty() && riskLevel.isNotEmpty()) {
                val id = dbHelper.addPortfolio(name, assetAllocation, riskLevel)
                if (id > -1) {
                    Toast.makeText(this, "Portföy başarıyla eklendi.", Toast.LENGTH_SHORT).show()
                    clearFields()
                    val newPortfolios = dbHelper.getAllPortfolios()
                    adapter.updatePortfolios(newPortfolios)
                } else {
                    Toast.makeText(this, "Portföy eklenemedi.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearFields() {
        binding.etPortfolioName.text.clear()
        binding.etAssetAllocation.text.clear()
        binding.etRiskLevel.text.clear()
    }
}
