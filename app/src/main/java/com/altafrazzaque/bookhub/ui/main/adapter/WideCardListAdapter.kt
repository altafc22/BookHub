package com.altafrazzaque.bookhub.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.databinding.ItemBookCardWideBinding
import com.altafrazzaque.bookhub.model.api.Item
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.ui.main.adapter.comaparators.AuthorComparator
import com.altafrazzaque.bookhub.ui.main.adapter.comaparators.TitleComparator
import com.altafrazzaque.bookhub.utilities.AppUtils
import com.altafrazzaque.bookhub.utilities.GlideApp
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.util.*
import kotlin.collections.ArrayList

class WideCardListAdapter(private val listener: OnBookClickListener, private var showDelete: Boolean = false) : RecyclerView.Adapter<WideCardListAdapter.ViewHolder>() {

    private var data = ArrayList<VolumeInfo>()
    fun addData(result: List<VolumeInfo>) {
        data.clear()
        data.addAll(result)
        sortAlphabetically(true)
    }

    fun addDataItem(result: List<Item>) {
        data.clear()
        val list = arrayListOf<VolumeInfo>()
        for(book in result) {
            book.volumeInfo?.let {
                var volumeInfo = it
                volumeInfo.id = book.id
                list.add(volumeInfo)
            }
        }
        addData(list)
        sortAlphabetically(true)
    }

    fun addData(result: VolumeInfo) {
        data.add(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemBookCardWideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: VolumeInfo) {
            binding.tvTitle.text = result.title
            binding.tvAuthor.text = result.authors?.let { AppUtils.getAuthors(it) }
            binding.tvDescription.text = result.description?:""

            if(showDelete)
                binding.btnRemove.visibility = View.VISIBLE
            else
                binding.btnRemove.visibility = View.GONE

            GlideApp
                .with(binding.ivImage.context)
                .load(result.imageLinks?.smallThumbnail)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                listener.onBookClick(result)
            }

            binding.btnRemove.setOnClickListener {
                listener.onBookRemoveClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBookCardWideBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_book_card_wide, parent, false))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun sortByAuthor(isAsc: Boolean) {
        Collections.sort(data, AuthorComparator(isAsc))
        notifyDataSetChanged()
    }

    fun sortAlphabetically(isAsc:Boolean){
        Collections.sort(data, TitleComparator(isAsc))
        notifyDataSetChanged()
    }

    fun clearData(){
        data.clear()
        notifyDataSetChanged()
    }
}



interface OnBookClickListener {
    fun onBookClick(book: VolumeInfo)
    fun onBookRemoveClick(book: VolumeInfo)
}