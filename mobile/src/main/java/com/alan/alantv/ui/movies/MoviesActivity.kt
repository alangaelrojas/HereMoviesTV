package com.alan.alantv.ui.movies

import android.app.ActivityOptions
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build.*
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.alantv.R
import com.alan.alantv.databinding.ActivityMoviesBinding
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.utils.broadcast_receiver.NetworkStateReceiver
import com.alan.core.utils.broadcast_receiver.OnNetworkChange
import com.alan.alantv.ui.moviedetail.DetailMovieActivity
import com.alan.alantv.ui.searchmovies.DialogSearchFragment
import com.google.android.material.snackbar.Snackbar


class MoviesActivity : AppCompatActivity(), OnClickMovieListener, View.OnClickListener,
    OnNetworkChange {

    private lateinit var binding: ActivityMoviesBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var moviesAdapter: MovieAdapter

    private var animationExplosion: Animation? = null

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcastReceiver = NetworkStateReceiver(this)
        registerBroadCastReceiver()

        moviesAdapter = MovieAdapter(this, this)

        setObservers()

        initViews()

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadCastReceiver()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.popular -> {
                viewModel.getPopularMovies()
                loadingMovies()

            }
            R.id.topRated -> {
                viewModel.getTopRatedMovies()
                loadingMovies()
            }
            R.id.fab_search -> {
                supportFragmentManager.let { fg ->
                    DialogSearchFragment().show(fg, "")
                }
                /*
                binding.circle.isVisible = true
                binding.circle.initAnimation(animationExplosion) {
                    // display your fragment
                    binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.rate))
                    binding.circle.isVisible = false
                    }
                }
               */
            }
        }
    }

    override fun onClickMovie(movie: MovieEntity, movieImageView: ImageView) {

        val intent = Intent(this, DetailMovieActivity::class.java).apply {
            putExtra("movie", movie)
        }
        val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName")

        startActivity(intent, options.toBundle())

    }

    private fun setObservers(){

        viewModel.popular.observe(this, this::popularMovies)
        viewModel.topRated.observe(this, this::topRatedMovies)
        viewModel.errorTopRated.observe(this) { error ->
            run {
                when(error.code){
                    100 -> {
                        emptyMovies(error.message, false)
                    }
                    401 -> {
                        emptyMovies(error.message, false)
                    }
                    404 -> {
                        emptyMovies(error.message, true)
                    }
                }
            }
        }

        viewModel.errorPopular.observe(this){ error -> run {
                when(error.code){
                    100 -> {
                        emptyMovies(error.message, false)
                    }
                    401 -> {
                        emptyMovies(error.message, false)
                    }
                    404 -> {
                        emptyMovies(error.message, true)
                    }
                }
            }
        }
    }

    private fun initViews(){

        animationExplosion = AnimationUtils.loadAnimation(this, R.anim.circle_explosion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }
        //RecyclerView
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }

        binding.popular.isCheckable = true
        binding.topRated.isCheckable = true

        binding.popular.setOnClickListener(this)
        binding.topRated.setOnClickListener(this)
        binding.fabSearch.setOnClickListener(this)

        viewModel.getPopularMovies()

    }

    //region :: REFERENCES METHODS
    private fun popularMovies(movies: List<MovieEntity>){
        binding.topRated.isChecked = false
        binding.popular.isChecked = true

        moviesAdapter.addPopularMovies(movies)

        loadedMovies()
    }

    private fun topRatedMovies(movies: List<MovieEntity>){
        binding.popular.isChecked = false
        binding.topRated.isChecked = true

        moviesAdapter.addTopRated(movies)

        loadedMovies()
    }
    //endregion

    //region :: SET VIEWS
    private fun loadingMovies(){
        binding.shimmerMovies.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
        binding.lyEmpty.root.visibility = View.GONE
    }

    private fun loadedMovies(){
        binding.shimmerMovies.visibility = View.GONE
        binding.rvMovies.visibility = View.VISIBLE
        binding.lyEmpty.root.visibility = View.GONE
    }

    private fun emptyMovies(message: String, empty: Boolean){
        binding.lyEmpty.root.visibility = View.VISIBLE
        binding.lyEmpty.tvEmpty.text = message

        if (empty){
            binding.lyEmpty.lottieView.setAnimation("not_found_animation.json")
        } else {
            binding.lyEmpty.lottieView.setAnimation("error_screen.json")
        }
        binding.lyEmpty.lottieView.playAnimation()


        binding.shimmerMovies.visibility = View.GONE
        binding.rvMovies.visibility = View.GONE
    }
    //endregion

    //region :: PRIVATE METHODS
    private fun registerBroadCastReceiver(){
        if (VERSION.SDK_INT >= VERSION_CODES.N){
            registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }
    private fun unregisterBroadCastReceiver(){
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (i: IllegalArgumentException){
            i.printStackTrace()
        }
    }
    //endregion

    override fun hasInternet(hasConnection: Boolean) {
        if (hasConnection) {
            Snackbar.make(binding.root, "Yeih!! We are connected", Snackbar.LENGTH_LONG).show()
            viewModel.getPopularMovies()
        }
    }

}