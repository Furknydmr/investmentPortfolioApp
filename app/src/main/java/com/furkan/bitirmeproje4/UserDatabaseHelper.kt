package com.furkan.bitirmeproje4

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 4
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_TOLERANS = "tolerans"
        private const val COLUMN_PORTFOLIO_ID = "portfolio_id"
        private const val TABLE_USER_PORTFOLIO = "user_portfolio"
        private const val COLUMN_USER_ID = "user_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTableQuery = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_USERNAME TEXT, "
                + "$COLUMN_PASSWORD TEXT, "
                + "$COLUMN_PHONE TEXT, "
                + "$COLUMN_TOLERANS TEXT)")
        db.execSQL(createUsersTableQuery)

        // Yeni tabloyu oluştur
        val createUserPortfolioTableQuery = ("CREATE TABLE $TABLE_USER_PORTFOLIO ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_USER_ID INTEGER, "
                + "$COLUMN_PORTFOLIO_ID INTEGER)")
        db.execSQL(createUserPortfolioTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER_PORTFOLIO")
        onCreate(db)
    }

    fun addUser(username: String, password: String, phone: String, tolerans: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_PHONE, phone)
        values.put(COLUMN_TOLERANS, tolerans)
        return try {
            val id = db.insert(TABLE_USERS, null, values)
            db.close()
            id
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Failed to add user: ${e.message}")
            -1
        }
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_USERNAME)
        val selection = "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)
        val cursor: Cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val tolerans = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOLERANS))
                userList.add(User(id, username, password, phone, tolerans))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        Log.d("DatabaseHelper1", "getAllUsers: Retrieved ${userList.size} users from the database.")
        return userList
    }

    fun deleteAllUsers() {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, null, null)
        db.close()
    }

    @SuppressLint("Range")
    fun getUserByUsername(username: String): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null
        )

        var user: User? = null

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val foundUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            val phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
            val tolerance = cursor.getString(cursor.getColumnIndex(COLUMN_TOLERANS))
            user = User(id, foundUsername, password, phone, tolerance)
        }

        cursor?.close()
        db.close()

        return user
    }

    fun checkPhoneNumberExists(phone: String): Boolean {
        val db = this.readableDatabase
        val columns = arrayOf(COLUMN_PHONE)
        val selection = "$COLUMN_PHONE = ?"
        val selectionArgs = arrayOf(phone)
        val cursor: Cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    fun addUserPortfolio(userId: Long, portfolioId: Long): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, userId) // Sütun adı değiştirildi
            put(COLUMN_PORTFOLIO_ID, portfolioId)
        }
        val id = db.insert(TABLE_USER_PORTFOLIO, null, values)
        db.close()
        return id
    }
}

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val phone: String,
    val tolerans: String? = null
)
