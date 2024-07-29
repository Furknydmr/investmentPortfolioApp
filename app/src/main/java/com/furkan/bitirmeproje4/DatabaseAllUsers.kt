package com.furkan.bitirmeproje4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkan.bitirmeproje4.databinding.ActivityDatabaseAllUsersBinding

class DatabaseAllUsers : AppCompatActivity() {
    private lateinit var binding: ActivityDatabaseAllUsersBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatabaseAllUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        userList = dbHelper.getAllUsers()

        userAdapter = UserAdapter(userList)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DatabaseAllUsers)
            adapter = userAdapter
        }
    }
}