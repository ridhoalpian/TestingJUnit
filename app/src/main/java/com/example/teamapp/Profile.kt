package com.example.teamapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.teamapp.databinding.FragmentProfileBinding
import com.example.teamapp.ui.LoginActivity

class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mendapatkan SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("user_data", AppCompatActivity.MODE_PRIVATE)

        // Cek apakah pengguna sudah login
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        if (isLoggedIn) {
            // Mengambil data pengguna dari SharedPreferences
            val username = sharedPreferences.getString("username", "")
            val email = sharedPreferences.getString("email", "")

            val firstChar = username?.take(1)
            binding.title.text = firstChar

            // Menampilkan informasi pengguna
            binding.usernameTextView.text = username
            binding.emailTextView.text = email
        }

        // Mengatur listener untuk tombol logout
        binding.logoutButton.setOnClickListener {
            // Menghapus status login dari SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            // Navigasi kembali ke LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Menutup aktivitas saat logout
        }
        return root
    }
}