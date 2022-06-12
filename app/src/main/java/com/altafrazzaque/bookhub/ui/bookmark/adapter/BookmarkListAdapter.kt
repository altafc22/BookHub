package com.altafrazzaque.bookhub.ui.bookmark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.databinding.ItemBookCardWideBinding
import com.altafrazzaque.bookhub.model.BookData
import com.altafrazzaque.bookhub.utilities.GlideApp
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class BookmarkListAdapter(private val listener: OnBookmarkClickListener) : RecyclerView.Adapter<BookmarkListAdapter.ViewHolder>() {
    private var data = ArrayList<BookData>()
    fun addData(result: List<BookData>) {
        data.clear()
        data.addAll(result)
        notifyDataSetChanged()
    }

    fun addData(result: BookData) {
        data.add(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemBookCardWideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: BookData) {
            binding.tvTitle.text = result.title
            binding.tvAuthor.text = result.author?:""
            binding.tvDescription.text = result.description?:""
            if(result.author.isNullOrEmpty()) binding.tvAuthor.visibility = View.GONE
            binding.btnRemove.visibility = View.VISIBLE

            GlideApp
                .with(binding.ivImage.context)
                .load(result.bookThumbnail)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                listener.onBookmarkClick(result)
            }

            binding.btnRemove.setOnClickListener {
                listener.onBookmarkRemoveClick(result)
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

    fun sortByAuthor() {

    }

    fun sortAplphabetically(){

    }
}

interface OnBookmarkClickListener {
    fun onBookmarkClick(book: BookData)
    fun onBookmarkRemoveClick(book: BookData)
}