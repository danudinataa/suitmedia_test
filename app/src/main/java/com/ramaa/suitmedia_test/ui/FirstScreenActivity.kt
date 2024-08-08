package com.ramaa.suitmedia_test.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ramaa.suitmedia_test.R
import com.ramaa.suitmedia_test.databinding.ActivityFirstScreenBinding
import java.util.Locale

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {

            binding.btnCheck.setOnClickListener {
                val sentence = binding.etPalindrome.text.toString()
                if (sentence.isEmpty()) {
                    Toast.makeText(this@FirstScreenActivity, "Please enter a sentence!", Toast.LENGTH_SHORT).show()
                } else {
                    if (isPalindrome(sentence)) {
                        showDialog("Palindrome", "The sentence is a palindrome!")
                    } else {
                        showDialog("Not Palindrome", "The sentence is not a palindrome!")
                    }
                }
            }

            btnNext.setOnClickListener {
                val name = binding.etName.text.toString()
                if (name.isNotEmpty()) {
                    val intent = Intent(this@FirstScreenActivity, SecondScreenActivity::class.java).apply {
                        putExtra("USER_NAME", name)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@FirstScreenActivity, "Please enter your name!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun isPalindrome(sentence: String): Boolean {
        val cleanedSentence = sentence.replace("\\s".toRegex(), "").lowercase(Locale.ROOT)
        return cleanedSentence == cleanedSentence.reversed()
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}