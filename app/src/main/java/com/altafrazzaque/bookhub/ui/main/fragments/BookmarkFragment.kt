package com.altafrazzaque.bookhub.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.base.BaseFragment
import com.altafrazzaque.bookhub.databinding.FragmentBookmarkBinding

import com.altafrazzaque.bookhub.model.BookData
import com.altafrazzaque.bookhub.ui.bookmark.BookmarkViewModel
import com.altafrazzaque.bookhub.ui.bookmark.adapter.BookmarkListAdapter
import com.altafrazzaque.bookhub.ui.bookmark.adapter.OnBookmarkClickListener
import timber.log.Timber


class BookmarkFragment : BaseFragment(), OnBookmarkClickListener {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarkViewModel by activityViewModels()
    private val adapter = BookmarkListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        Timber.i("onCreate view")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setListeners(){
        binding.rvBookmarks.adapter = adapter
    }

    override fun setObservers() {
        viewModel.getAllBookmarks().observe(viewLifecycleOwner) { books ->
            Timber.i("BOOKMARKS ${books.size}")
            adapter.addData(books)
            if(books.isNotEmpty())
                showNoDataView(false)
            else
                showNoDataView(true)
        }
    }

    override fun onBookmarkClick(book: BookData) {}

    override fun onBookmarkRemoveClick(book: BookData) {
        book.let { viewModel.deleteBook(it) }
    }

    private fun showNoDataView(visible:Boolean){
        if(visible)
            binding.noDataLayout.visibility = View.VISIBLE
        else
            binding.noDataLayout.visibility = View.GONE
    }

}