package com.altafrazzaque.bookhub.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.databinding.ItemHomeCardBinding
import com.altafrazzaque.bookhub.model.api.Item
import com.altafrazzaque.bookhub.ui.details.DetailsActivity
import com.altafrazzaque.bookhub.utilities.AppUtils
import com.altafrazzaque.bookhub.utilities.GlideApp
import com.altafrazzaque.bookhub.utilities.Parcel
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class HomeBookListAdapter : RecyclerView.Adapter<HomeBookListAdapter.ViewHolder>() {

    private var data = ArrayList<Item>()
    fun addData(result: List<Item>) {
        data.clear()
        data.addAll(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemHomeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(result: Item) {
            binding.tvTitle.text = result.volumeInfo!!.title
            binding.tvAuthor.text = result.volumeInfo.authors?.let { AppUtils.getAuthors(it) }
            result.volumeInfo.id = result.id
            //val renderScriptProvider = RenderScriptProvider(binding.shadowView.context)
            GlideApp
                .with(binding.ivImage.context)
                .load(result.volumeInfo.imageLinks?.smallThumbnail)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(binding.ivImage)

                binding.root.setOnClickListener {
                val intent = Intent(binding.ivImage.context, DetailsActivity::class.java)
                intent.putExtra(Parcel.BOOK, result.volumeInfo)
                binding.root.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeCardBinding.bind(LayoutInflater.from(parent.context).inflate(R.layout.item_home_card, parent, false))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}