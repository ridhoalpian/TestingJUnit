package com.example.teamapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teamapp.databinding.FragmentHomeItemBinding
import com.example.teamapp.model.ResponseUserGithub
import java.util.*

class UserAdapter(
    private var fullData: MutableList<ResponseUserGithub.Item>,
    private val listener: (ResponseUserGithub.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {

    private var filteredData: MutableList<ResponseUserGithub.Item> = fullData.toMutableList()

    init {
        this.fullData = fullData.toMutableList()
        this.filteredData = fullData.toMutableList()
    }

    fun setData(data: MutableList<ResponseUserGithub.Item>) {
        this.fullData.clear()
        this.fullData.addAll(data)
        this.filteredData = fullData.toMutableList()
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: FragmentHomeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseUserGithub.Item) {
            // Menggunakan Glide untuk mengisi gambar pengguna
            Glide.with(binding.itemImage)
                .load(item.avatar_url)
                .circleCrop()
                .into(binding.itemImage)

            binding.itemContent.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = filteredData[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = filteredData.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val charSearch = constraint.toString().toLowerCase(Locale.ROOT)

                if (charSearch.isEmpty()) {
                    filteredData = fullData.toMutableList()
                } else {
                    val resultList = mutableListOf<ResponseUserGithub.Item>()
                    for (row in fullData) {
                        if (row.login.toLowerCase(Locale.ROOT).contains(charSearch)) {
                            resultList.add(row)
                        }
                    }
                    filteredData = resultList
                }

                val results = FilterResults()
                results.values = filteredData
                return results
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredData = results?.values as? MutableList<ResponseUserGithub.Item> ?: fullData
                notifyDataSetChanged()
            }
        }
    }
}
