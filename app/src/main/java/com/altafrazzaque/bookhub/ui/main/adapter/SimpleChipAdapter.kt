package com.altafrazzaque.bookhub.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.databinding.ItemCategoryChipsBinding
import com.altafrazzaque.bookhub.model.Category


class SimpleChipAdapter: RecyclerView.Adapter<SimpleChipAdapter.ViewHolder>() {

    var selectedPosition = -1


    private var arrayList: ArrayList<Category> = ArrayList()

    fun addData(items:List<Category>){
        arrayList.clear()
        arrayList = ArrayList(items)
        notifyDataSetChanged()
    }

    fun clearData(){
        arrayList.clear()
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

    override fun onBindViewHolder(
        @NonNull holder: ViewHolder,
        position: Int
    ) {
        // Set text on radio button
        holder.radioButton.text = arrayList[position].name

        // Checked selected radio button
        holder.radioButton.isChecked = (position == selectedPosition)

        // set listener on radio button
        holder.radioButton.setOnCheckedChangeListener { _, b ->
            // check condition
            if (b) {
                selectedPosition = holder.adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        // pass position
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        // pass position
        return position
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(var binding: ItemCategoryChipsBinding) : RecyclerView.ViewHolder(binding.root) {
        // Initialize variable
        var radioButton: RadioButton = binding.rbCategory

        init {

            // Assign variable
        }
    }
}