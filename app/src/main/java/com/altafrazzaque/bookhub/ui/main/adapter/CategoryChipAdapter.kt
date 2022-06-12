package com.altafrazzaque.bookhub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.databinding.ItemCategoryChipsBinding
import com.altafrazzaque.bookhub.model.Category


class CategoryChipAdapter(private val listener: OnChipClickListener): RecyclerView.Adapter<CategoryChipAdapter.ViewHolder>() {

    private var items: ArrayList<Category> = ArrayList()

    fun addData(items:List<Category>){
        this.items.clear()
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }

    fun clearData(){
        items.clear()
        notifyDataSetChanged()
    }

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Initialize view
        val view = ItemCategoryChipsBinding.bind(LayoutInflater.from(parent.context).inflate(com.altafrazzaque.bookhub.R.layout.item_category_chips, parent, false))
        // Pass holder view
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].name

        holder.textView.setOnClickListener {
            listener.onChipClick(items[position], position)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(var binding: ItemCategoryChipsBinding) : RecyclerView.ViewHolder(binding.root) {
        var textView = binding.tvTitle
    }
}

interface OnChipClickListener {
    fun onChipClick(item: Category, position: Int)
}