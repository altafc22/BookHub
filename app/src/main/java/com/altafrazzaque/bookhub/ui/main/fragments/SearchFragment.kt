package com.altafrazzaque.bookhub.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.altafrazzaque.bookhub.base.BaseFragment
import com.altafrazzaque.bookhub.databinding.FragmentSearchBinding
import com.altafrazzaque.bookhub.model.Category
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.ui.details.DetailsActivity
import com.altafrazzaque.bookhub.ui.main.MainViewModel
import com.altafrazzaque.bookhub.ui.main.adapter.OnBookClickListener
import com.altafrazzaque.bookhub.ui.main.adapter.WideCardListAdapter
import com.altafrazzaque.bookhub.utilities.Parcel
import timber.log.Timber

class SearchFragment : BaseFragment(), OnBookClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val adapter = WideCardListAdapter(this,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Timber.i("onCreate")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        Timber.i("onCreate view")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
        loadData()
    }

    override fun setObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            val loading = it!!
            if(loading)
                showLoadingView(true)
            else
                showLoadingView(false)
        }

        viewModel.latestBooks.observe(viewLifecycleOwner) {
            val result = it!!.items
            Timber.i("LatestBOoks: ${result.size}")
            adapter.addDataItem(result)
        }
    }

    private var isAsc = true
    private var isAuthSort = true
    private fun setListeners(){
        binding.rvBooks.adapter = adapter

        if(viewModel.selectedCategory!=null){
            showSearchView(false)
            loadData()
        }
        else {
            showSearchView(true)
        }

        binding.btnClear.setOnClickListener {
            binding.etSearch.setText("")
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val text = binding.etSearch.text.toString()
                if(text.isNotEmpty()){
                    val category = Category(text,text)
                    viewModel.selectedCategory = category
                    loadData()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        binding.btnOpenSearch.setOnClickListener {
            showSearchView(true)
        }

        binding.btnAlphabetically.setOnClickListener {
            isAsc = !isAsc
            adapter.sortAlphabetically(isAsc)
        }

        binding.btnAuthor.setOnClickListener {
            isAuthSort = !isAuthSort
            Timber.i("SORT AUTHOR $isAuthSort")
            adapter.sortByAuthor(isAuthSort)
        }
    }

    override fun onBookClick(book: VolumeInfo) {
        val intent = Intent(requireContext(),DetailsActivity::class.java)
        intent.putExtra(Parcel.BOOK,book)
        requireContext().startActivity(intent)
    }

    override fun onBookRemoveClick(book: VolumeInfo) {

    }
    private fun loadData(){
        if(viewModel.selectedCategory!=null){
            adapter.clearData()
            viewModel.getLatestBooks(viewModel.selectedCategory!!)
        }
    }


    private fun showSearchView(visible:Boolean){
        if(visible){
            binding.searchView.visibility = View.VISIBLE
            binding.btnOpenSearch.visibility = View.GONE
            binding.tvTitle.visibility = View.GONE
        }
        else {
            binding.searchView.visibility = View.GONE
            binding.btnOpenSearch.visibility = View.VISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvTitle.text = viewModel.selectedCategory?.name
        }
    }

    private fun showLoadingView(visible:Boolean){
        if(visible)
            binding.loadingLayout.visibility = View.VISIBLE
        else
            binding.loadingLayout.visibility = View.GONE
    }
}