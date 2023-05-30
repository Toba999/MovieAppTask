package com.example.pop_flake.ui.home

import com.example.pop_flake.utils.NetworkChangeReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pop_flake.data.pojo.BoxOfficeMovie
import com.example.pop_flake.data.remote.ResponseState
import com.example.pop_flake.databinding.FragmentHomeBinding
import com.example.pop_flake.ui.home.boxOfficeAdapter.BoxOfficeMovieAdapter
import com.example.pop_flake.ui.home.comingSoonAdapter.ComingSoonAdapter
import com.example.pop_flake.ui.home.inTheaterAdapter.InTheaterAdapter
import com.example.pop_flake.ui.home.topMoviesAdapter.TopMovieAdapter
import com.example.pop_flake.ui.shimmering.ShimmerHorizontalAdapter
import com.example.pop_flake.ui.shimmering.ShimmerVerticalAdapter
import com.example.pop_flake.utils.Helper
import com.example.pop_flake.utils.Helper.showTopSnackBarWithColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var comingAdapter : ComingSoonAdapter
    private lateinit var boxOfficeAdapter : BoxOfficeMovieAdapter
    private lateinit var theaterAdapter : InTheaterAdapter
    private lateinit var topAdapter : TopMovieAdapter

    @Inject
    lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        comingAdapter = ComingSoonAdapter()
        boxOfficeAdapter = BoxOfficeMovieAdapter()
         theaterAdapter = InTheaterAdapter()
         topAdapter = TopMovieAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showShimmering()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(requireActivity(),networkChangeReceiver, filter,RECEIVER_NOT_EXPORTED)

        NetworkChangeReceiver.isNetworkAvailable.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected && Helper.isFirst==0 ) {
                // Network is available
                Helper.isFirst++
                showTopSnackBarWithColor(view, "Network is Back", true, requireActivity())
                initApiCall()
            }else if (isConnected && Helper.isFirst==1){
                initApiCall()
            } else if(!isConnected && Helper.isFirst!=0) {
                // Network is unavailable
                fetchFromLocalCall()
            }else{
                Helper.isFirst++
                showTopSnackBarWithColor(view, "No Network detected", false, requireActivity())
                fetchFromLocalCall()
            }
        }
        observeMovies()
    }

    override fun onPause() {
        super.onPause()
        activity?.let { LocalBroadcastManager.getInstance(it).unregisterReceiver(networkChangeReceiver) }
    }


    private fun initApiCall(){
        viewModel.getTopRatedMovies()
        viewModel.getComingSoonMovies()
        viewModel.getInTheaterMovies()
        viewModel.getBoxOfficeMovies()
        setVisibilityBoxOfficeSection(true)
    }
    private fun fetchFromLocalCall(){
        fetchTopMoviesFromCache(false)
        fetchComingSoonMoviesFromCache(false)
        fetchInTheaterMoviesFromCache(false)
        setVisibilityBoxOfficeSection(false)
    }

    private fun showShimmering(){
        fetchTopMoviesFromCache(true)
        fetchComingSoonMoviesFromCache(true)
        fetchInTheaterMoviesFromCache(true)
        setVisibilityBoxOfficeSection(true)
    }


    private fun setVisibilityBoxOfficeSection(visible: Boolean){
        if (visible){
            binding.boxOfficeRecyclerView.visibility = View.VISIBLE
            binding.txtBoxOffice.visibility = View.VISIBLE
        }else{
            binding.boxOfficeRecyclerView.visibility = View.GONE
            binding.txtBoxOffice.visibility = View.GONE
        }
    }


    private fun observeMovies() {

        viewModel.topRatedMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    // Show loading state
                    fetchTopMoviesFromCache(true)
                }
                is ResponseState.Success -> {
                    // Update the adapter with the list of movies
                    fetchTopMoviesFromCache(false)

                }
                is ResponseState.Error -> {
                    // Show error message
                    Timber.tag("Error").e(result.message)
                }
            }
        }
        viewModel.comingSoonMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    // Show loading state
                    fetchComingSoonMoviesFromCache(true)
                }
                is ResponseState.Success -> {
                    // Update the adapter with the list of movies
                    fetchComingSoonMoviesFromCache(false)
                }
                is ResponseState.Error -> {
                    // Show error message
                    Timber.tag("Error").e(result.message)
                }
            }
        }
        viewModel.inTheaterMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    // Show loading state
                    fetchInTheaterMoviesFromCache(true)
                }
                is ResponseState.Success -> {
                    // Update the adapter with the list of movies
                    fetchInTheaterMoviesFromCache(false)
                }
                is ResponseState.Error -> {
                    // Show error message
                    Timber.tag("Error").e(result.message)
                }
            }
        }
        viewModel.boxOfficeMoviesLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    // Show loading state
                    setBoxMoviesAdapter(loading = true)
                }
                is ResponseState.Success -> {
                    // Update the adapter with the list of movies
                    setBoxMoviesAdapter(result.data,false)
                }
                is ResponseState.Error -> {
                    // Show error message
                    Timber.tag("Error").e(result.message)
                }
            }
        }
    }

    private fun showShimmerHorizontalPlaceholders(recyclerView: RecyclerView, itemCount: Int) {
        val shimmerHorizontalAdapter = ShimmerHorizontalAdapter(itemCount)
        recyclerView.adapter = shimmerHorizontalAdapter
    }
    private fun showShimmerVerticalPlaceholders(recyclerView: RecyclerView, itemCount: Int) {
        val shimmerVerticalAdapter = ShimmerVerticalAdapter(itemCount)
        recyclerView.adapter = shimmerVerticalAdapter
    }

    private fun fetchTopMoviesFromCache(loading : Boolean){
        binding.topMoviesRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL, false)
        if(loading){
            showShimmerHorizontalPlaceholders(binding.topMoviesRecyclerView,5)
        }else{
            binding.topMoviesRecyclerView.adapter = topAdapter
            lifecycleScope.launch {
                viewModel.topMoviesFlow.collectLatest { pagingData ->
                    topAdapter.submitData(pagingData)
                }
            }
        }

    }

    private fun fetchComingSoonMoviesFromCache(loading : Boolean) {
        binding.comingSoonRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.HORIZONTAL, false)
       if (loading){
           showShimmerHorizontalPlaceholders(binding.comingSoonRecyclerView,5)
       }else{
           binding.comingSoonRecyclerView.adapter = comingAdapter
           lifecycleScope.launch {
               viewModel.comingSoonMoviesFlow.collectLatest { pagingData ->
                   comingAdapter.submitData(pagingData)
               }
           }
       }
    }

    private fun fetchInTheaterMoviesFromCache(loading : Boolean){
        binding.inTheaterRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        if(loading){
            showShimmerHorizontalPlaceholders(binding.inTheaterRecyclerView,5)
        }else{
            binding.inTheaterRecyclerView.adapter = theaterAdapter
            lifecycleScope.launch {
                viewModel.inTheaterMoviesFlow.collectLatest { pagingData ->
                    theaterAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun setBoxMoviesAdapter(movieList: List<BoxOfficeMovie> = listOf(), loading : Boolean){

        binding.boxOfficeRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL, false)
        binding.boxOfficeRecyclerView.adapter = boxOfficeAdapter
        if(!loading){
            boxOfficeAdapter.submitMovies(movieList)
        }else{
            showShimmerVerticalPlaceholders(binding.boxOfficeRecyclerView,5)
        }

    }




}