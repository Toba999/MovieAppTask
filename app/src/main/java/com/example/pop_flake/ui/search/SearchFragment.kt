package com.example.pop_flake.ui.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pop_flake.data.pojo.MovieResult
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.databinding.FragmentSearchBinding
import com.example.pop_flake.ui.shimmering.ShimmerVerticalAdapter
import com.example.pop_flake.utils.Helper
import com.example.pop_flake.utils.NetworkChangeReceiver
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    lateinit var searchAdapter:SearchAdapter
    @Inject
    lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchAdapter = SearchAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovies()

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchMovies(query)
                hideKeyboard(requireContext())
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

    }

    private fun observeMovies() {

        viewModel.searchMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    // Show loading state
                    setSearchResultMoviesAdapter(loading = true)
                }
                is ResponseState.Success -> {
                    // Update the adapter with the list of movies
                    showError(false, noNetwork = false)
                    setSearchResultMoviesAdapter(result.data,false)

                }
                is ResponseState.Error -> {
                    // Show error message
                    if (Helper.isNetworkAvailable(requireContext())){
                        showError(true, noNetwork = false)
                    }else{
                        showError(true, noNetwork = true)
                    }
                    Timber.tag("Error").e(result.message)
                }
            }
        }
    }

    private fun setSearchResultMoviesAdapter(movieList: List<MovieResult> = listOf(), loading : Boolean){

        binding.searchMoviesRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
        binding.searchMoviesRecyclerView.adapter = searchAdapter
        if(!loading){
            searchAdapter.submitMovies(movieList)
        }else{
            showShimmerVerticalPlaceholders(binding.searchMoviesRecyclerView,5)
        }

    }

    private fun showError(isError : Boolean, noNetwork: Boolean){
        if (isError){
            if (noNetwork) {
                binding.errorText.text = "No Network Found"
            }
            binding.errorLayout.visibility = View.VISIBLE
            binding.searchMoviesRecyclerView.visibility = View.GONE
        }else{
            binding.errorLayout.visibility = View.GONE
            binding.searchMoviesRecyclerView.visibility = View.VISIBLE
        }
    }
    private fun showShimmerVerticalPlaceholders(recyclerView: RecyclerView, itemCount: Int) {
        val shimmerVerticalAdapter = ShimmerVerticalAdapter(itemCount)
        recyclerView.adapter = shimmerVerticalAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideKeyboard(context: Context) {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = activity?.currentFocus
        if (currentFocusView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }
    }
}

