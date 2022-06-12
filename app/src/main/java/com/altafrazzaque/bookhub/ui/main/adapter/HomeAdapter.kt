package com.altafrazzaque.bookhub.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.databinding.ItemHomeCategoryBinding
import com.altafrazzaque.bookhub.model.api.CategoryResult
import com.altafrazzaque.bookhub.utilities.Parcel
import timber.log.Timber

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var data = ArrayList<CategoryResult>()
    fun addData(result: CategoryResult) {
        val index = data.indexOfFirst { it.category == result.category }

        if(index>=0)
            data[index] = result
        else
            data.add(result)

        var data = ArrayList(data.sortedWith(compareBy { it.category.name }))

        notifyItemInserted(data.size)
    }

    inner class ViewHolder(var binding: ItemHomeCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val adapter = HomeBookListAdapter()

        fun bindData(result: CategoryResult) {

            binding.titleItemList.text = result.category.name
            binding.homeItemList.adapter = adapter
            adapter.addData(result.books.items)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCategoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_category, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun clearData() {
        Timber.i("Adapter Clear data")
        data.clear()
        notifyDataSetChanged()
    }
}