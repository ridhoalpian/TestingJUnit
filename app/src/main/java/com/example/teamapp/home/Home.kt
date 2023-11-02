package com.example.teamapp.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.teamapp.utils.Result
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamapp.R
import com.example.teamapp.databinding.FragmentHomeBinding
import com.example.teamapp.detail.Detaill
import com.example.teamapp.model.ResponseUserGithub

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userList: MutableList<ResponseUserGithub.Item>

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Set up RecyclerView
        adapter = UserAdapter(mutableListOf()) { user ->
            val detailFragment = Detaill()
            val bundle = Bundle()
            bundle.putString("item", user.login)
            detailFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction() // Menggunakan parentFragmentManager di dalam Fragment
            transaction.replace(R.id.frame_layout, detailFragment) // Ganti R.id.fragment_container dengan ID wadah fragmen Anda
            transaction.addToBackStack(null) // Opsional, untuk menambahkan transaksi ke back stack
            transaction.commit()
        }
        
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.adapter = adapter

        viewModel.resultUser.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success<*> -> {
                    userList = result.data as MutableList<ResponseUserGithub.Item>
                    adapter.setData(userList)
                    setupSearchView()
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireContext(),
                        result.exception.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = result.isLoading
                }
            }
        }

        viewModel.getUser()

        return binding.root
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }
}