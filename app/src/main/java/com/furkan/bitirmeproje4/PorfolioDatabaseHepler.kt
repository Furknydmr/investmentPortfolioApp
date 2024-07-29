package com.furkan.bitirmeproje4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlin.random.Random

class PortfolioDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "portfolios.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PORTFOLIOS = "portfolios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ASSET_ALLOCATION = "asset_allocation"
        private const val COLUMN_RISK_LEVEL = "risk_level"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createPortfoliosTableQuery = ("CREATE TABLE $TABLE_PORTFOLIOS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NAME TEXT, "
                + "$COLUMN_ASSET_ALLOCATION TEXT, "
                + "$COLUMN_RISK_LEVEL TEXT)")
        db.execSQL(createPortfoliosTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PORTFOLIOS")
        onCreate(db)
    }

    fun addPortfolio(name: String, assetAllocation: String, riskLevel: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_ASSET_ALLOCATION, assetAllocation)
            put(COLUMN_RISK_LEVEL, riskLevel)
        }
        val id = db.insert(TABLE_PORTFOLIOS, null, values)
        db.close()
        return id
    }

    fun getPortfoliosByRiskLevel(riskLevel: String): List<Portfolio> {
        val portfolioList = mutableListOf<Portfolio>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PORTFOLIOS, null, "$COLUMN_RISK_LEVEL = ?", arrayOf(riskLevel), null, null, null)
        cursor.use {
            val idIndex = it.getColumnIndex(COLUMN_ID)
            val nameIndex = it.getColumnIndex(COLUMN_NAME)
            val assetAllocationIndex = it.getColumnIndex(COLUMN_ASSET_ALLOCATION)
            val riskLevelIndex = it.getColumnIndex(COLUMN_RISK_LEVEL)

            while (it.moveToNext()) {
                val id = if (idIndex >= 0) it.getLong(idIndex) else -1
                val name = if (nameIndex >= 0) it.getString(nameIndex) else ""
                val assetAllocation = if (assetAllocationIndex >= 0) it.getString(assetAllocationIndex) else ""
                val riskLevel = if (riskLevelIndex >= 0) it.getString(riskLevelIndex) else ""

                val portfolio = Portfolio(id, name, assetAllocation, riskLevel)
                portfolioList.add(portfolio)
            }
        }
        db.close()
        return portfolioList
    }
    fun getAllPortfolios(): List<Portfolio> {
        val portfolioList = mutableListOf<Portfolio>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PORTFOLIOS, null, null, null, null, null, null)
        cursor.use {
            val idIndex = it.getColumnIndex(COLUMN_ID)
            val nameIndex = it.getColumnIndex(COLUMN_NAME)
            val assetAllocationIndex = it.getColumnIndex(COLUMN_ASSET_ALLOCATION)
            val riskLevelIndex = it.getColumnIndex(COLUMN_RISK_LEVEL)

            while (it.moveToNext()) {
                val id = if (idIndex >= 0) it.getLong(idIndex) else -1
                val name = if (nameIndex >= 0) it.getString(nameIndex) else ""
                val assetAllocation = if (assetAllocationIndex >= 0) it.getString(assetAllocationIndex) else ""
                val riskLevel = if (riskLevelIndex >= 0) it.getString(riskLevelIndex) else ""

                val portfolio = Portfolio(id, name, assetAllocation, riskLevel)
                portfolioList.add(portfolio)
            }
        }
        db.close()
        return portfolioList
    }
    fun clearAllPortfolios() {
        val db = this.writableDatabase
        db.delete(TABLE_PORTFOLIOS, null, null)
        db.close()
    }
    fun getRandomPortfolioByRiskLevel(riskLevel: String): Portfolio? {
        val portfolioList = mutableListOf<Portfolio>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_PORTFOLIOS, null, "$COLUMN_RISK_LEVEL = ?", arrayOf(riskLevel), null, null, null)
        cursor.use {
            val idIndex = it.getColumnIndex(COLUMN_ID)
            val nameIndex = it.getColumnIndex(COLUMN_NAME)
            val assetAllocationIndex = it.getColumnIndex(COLUMN_ASSET_ALLOCATION)
            val riskLevelIndex = it.getColumnIndex(COLUMN_RISK_LEVEL)

            while (it.moveToNext()) {
                val id = if (idIndex >= 0) it.getLong(idIndex) else -1
                val name = if (nameIndex >= 0) it.getString(nameIndex) else ""
                val assetAllocation = if (assetAllocationIndex >= 0) it.getString(assetAllocationIndex) else ""
                val riskLevel = if (riskLevelIndex >= 0) it.getString(riskLevelIndex) else ""

                val portfolio = Portfolio(id, name, assetAllocation, riskLevel)
                portfolioList.add(portfolio)
            }
        }
        db.close()
        return if (portfolioList.isNotEmpty()) {
            portfolioList[Random.nextInt(portfolioList.size)]
        } else {
            null
        }
    }

}

data class Portfolio(
    val id: Long,
    val name: String,
    val assetAllocation: String,
    val riskLevel: String
)

