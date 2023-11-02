package com.example.teamapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.teamapp.databinding.LoginActivityBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : ComponentActivity() {
    private lateinit var binding: LoginActivityBinding
    lateinit var auth: FirebaseAuth
//    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

//        db = AppDatabase.getDatabase(this)

        binding.txtDaftardulu.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            val email = binding.edtUser.text.toString()
            val password = binding.edtPass.text.toString()

            //Validasi email
            if (email.isEmpty()) {
                binding.edtUser.error = "Email Harus Diisi"
                binding.edtUser.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edtPass.error = "Email Tidak Valid"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()) {
                binding.edtPass.error = "Password Harus Diisi"
                binding.edtPass.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email, password)
        }
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    saveLoginStatus()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


// Simpan SharedPreferences saat login
private fun saveLoginStatus() {
    val sharedPreferences: SharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putBoolean("is_logged_in", true)
    editor.apply()
}
}
//
//    // Fungsi untuk menyimpan data pengguna ke SharedPreferences
//    private fun saveUserData(userId: Int, username: String?, email: String?) {
//        val sharedPreferences = getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        // Simpan data pengguna
//        editor.putInt("user_id", userId)
//        editor.putString("username", username)
//        editor.putString("email", email)
//        editor.apply()
//    }


