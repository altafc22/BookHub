package com.altafrazzaque.bookhub.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.base.BaseActivity
import com.altafrazzaque.bookhub.databinding.ActivityDetailsBinding
import com.altafrazzaque.bookhub.model.BookData
import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.model.api.Item
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.ui.bookmark.BookmarkViewModel
import com.altafrazzaque.bookhub.ui.main.MainViewModel
import com.altafrazzaque.bookhub.utilities.AppUtils
import com.altafrazzaque.bookhub.utilities.GlideApp
import com.altafrazzaque.bookhub.utilities.Parcel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import timber.log.Timber

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val viewModel : BookmarkViewModel by viewModels()

    lateinit var book : VolumeInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        setViews()
    }

    private fun setViews() {
        val data = intent.getParcelableExtra<VolumeInfo>(Parcel.BOOK)
        if(data!=null){
            book = data
            binding.tvTitle.text = book.title
            binding.tvSubtitle.text = if(book.subtitle!=null && book.subtitle!="null") book.subtitle else ""
            binding.tvAuthor.text = book.authors?.let { AppUtils.getAuthors(it) }
            binding.tvDescription.text = book.description

            GlideApp
                .with(binding.ivImage.context)
                .load(book.imageLinks?.thumbnail)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .into(binding.ivImage)

            Glide.with(this)
                .load(book.imageLinks?.thumbnail)
                .transform(BlurTransformation(40))
                .into(binding.ivBg)

        }
        else {
            showSnackBar("No Data Found")
        }
    }

    private fun setListeners() {
        binding.btnBookmark.setOnClickListener {
            Timber.i("BOOK ID ${book.id}")
            var item = BookData(
                id = book.id,
                bookSmallThumbnail = book.imageLinks?.smallThumbnail,
                title = book.title,
                publisher = book.publisher,
                author = book.authors?.let { it1 -> AppUtils.getAuthors(it1) },
                description = book.description,
                previewLink = book.previewLink,
                bookThumbnail= book.imageLinks?.thumbnail,
                isFavourite = true
            )
            viewModel.addBook(item)
            showSnackBar("Bookmark added successfully")
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun setObservers() {
    }
}