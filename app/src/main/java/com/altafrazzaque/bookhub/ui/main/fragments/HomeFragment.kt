package com.altafrazzaque.bookhub.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.altafrazzaque.bookhub.ui.main.MainViewModel
import androidx.fragment.app.activityViewModels
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.base.BaseFragment
import com.altafrazzaque.bookhub.databinding.FragmentHomeBinding
import com.altafrazzaque.bookhub.model.*
import com.altafrazzaque.bookhub.model.api.CategoryResult
import com.altafrazzaque.bookhub.ui.main.MainActivity
import com.altafrazzaque.bookhub.ui.main.adapter.CategoryChipAdapter
import com.altafrazzaque.bookhub.ui.main.adapter.HomeAdapter
import com.altafrazzaque.bookhub.ui.main.adapter.OnChipClickListener
import timber.log.Timber

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, OnChipClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var chipsAdapter = CategoryChipAdapter(this)
    private var adapter = HomeAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Timber.i("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        Timber.i("onCreate view")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("VIew Created")
        setListeners()
        setObservers()
        loadCategoryChips()
        loadData()
    }

    private fun setListeners(){
        binding.refreshLayout.setOnRefreshListener(this)
        binding.rvCategory.adapter = adapter
        binding.rvCategoryChips.adapter = chipsAdapter

        val btnRetry = binding.noInternetView.findViewById<View>(R.id.btnRetry)
        btnRetry.setOnClickListener {
            loadData()
        }
    }

    override fun setObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = it!!
            if(it)
                showLoadingView(true)
            else
                showLoadingView(false)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            if(it!="")
                showErrorView(true)
            else
                showErrorView(false)
        }

        viewModel.categoryChips.observe(viewLifecycleOwner) {
            val result = it
            Timber.i("Categories found ${it.size}")
            chipsAdapter.addData(result)
        }

        viewModel.travelBooks.observe(viewLifecycleOwner) {
            val result = CategoryResult(categoryTravel(),it!!)
            adapter.addData(result)
        }

        viewModel.businessBooks.observe(viewLifecycleOwner) {
            val result = CategoryResult(categoryBusiness(),it!!)
            adapter.addData(result)
        }

        viewModel.educationBooks.observe(viewLifecycleOwner) {
            val result = CategoryResult(categoryEducation(),it!!)
            adapter.addData(result)
        }

        viewModel.comicBooks.observe(viewLifecycleOwner) {
            val result = CategoryResult(categoryComic(),it!!)
            adapter.addData(result)
        }
    }

    override fun onRefresh() {
        Timber.i("Loading new data")
        loadData()
    }

    private fun loadCategoryChips(){
        chipsAdapter.clearData()
        viewModel.getCategoryChips()
    }

    private fun loadData(){
        adapter.clearData()
        viewModel.getBusinessBooks()
        viewModel.getTravelBooks()
        viewModel.getEducationBooks()
        viewModel.getComicBooks()
    }

    override fun onChipClick(item: Category, position: Int) {
        viewModel.selectedCategory = item
        (activity as MainActivity).replaceFragments(R.id.nav_search)
       /* val intent = Intent(requireContext(),SearchActivity::class.java)
        intent.putExtra(Parcel.CATEGORY,item)
        requireContext().startActivity(intent)*/
    }

    private fun showErrorView(visible:Boolean){
        if(visible)
            binding.noInternetView.visibility = View.VISIBLE
        else
            binding.noInternetView.visibility = View.GONE
    }

    private fun showLoadingView(visible:Boolean){
        if(visible)
            binding.loadingLayout.visibility = View.VISIBLE
        else
            binding.loadingLayout.visibility = View.GONE
    }
}