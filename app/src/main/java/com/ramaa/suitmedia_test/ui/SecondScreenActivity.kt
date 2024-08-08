package com.ramaa.suitmedia_test.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ramaa.suitmedia_test.R
import com.ramaa.suitmedia_test.databinding.ActivitySecondScreenBinding

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    private val chooseUserLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("SELECTED_USER_NAME")
            selectedUserName?.let {
                binding.tvUsername.text = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userName = intent.getStringExtra("USER_NAME") ?: "User Name"
        binding.tvName.text = userName

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChoose.setOnClickListener {
            val intent = Intent(this@SecondScreenActivity, ThirdScreenActivity::class.java)
            chooseUserLauncher.launch(intent)
        }
    }
}
