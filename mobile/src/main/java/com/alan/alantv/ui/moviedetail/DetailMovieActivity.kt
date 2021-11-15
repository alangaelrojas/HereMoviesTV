package com.alan.alantv.ui.moviedetail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.alantv.databinding.ActivityDetailMovieBinding
import com.alan.alantv.ui.movies.MovieAdapterGrid
import com.alan.alantv.ui.movies.OnClickMovieListener
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.utils.getProgressDrawable
import com.alan.core.utils.loadImage
import com.google.android.material.snackbar.Snackbar


class DetailMovieActivity : AppCompatActivity(), OnClickMovieListener {

    private lateinit var binding: ActivityDetailMovieBinding
    private val viewModel by viewModels<DetailMovieViewModel>()

    private lateinit var moviesAdapter: MovieAdapterGrid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        moviesAdapter = MovieAdapterGrid(this, this)

        setObservers()

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        initViews()
    }

    private fun setObservers(){
        viewModel.recommendations.observe(this) { movies -> run {
            moviesAdapter.addMovies(movies)
            loadedMovies()
        }}
        viewModel.errorRecommendations.observe(this) { error -> run {
            emptyMovies()
        }}
        viewModel.movie.observe(this) { movie ->

            movie.posterPath?.let { binding.imageView.loadImage(it, getProgressDrawable(this)) }
            movie.image?.let { binding.detailMovieImg.loadImage(it, getProgressDrawable(this)) }

            binding.detailMovieTitle.text = movie?.title
            binding.detailMovieDesc.text = movie?.overview

            supportActionBar?.title = movie.title

            binding.lyRecommendations.rvRecommendations.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
                setHasFixedSize(true)
            }

            movie?.id?.let { id ->
                viewModel.getRecommendations(id)
                viewModel.getVideo(id)
                loadingMovies()
            }
        }

        viewModel.video.observe(this) { video ->
            binding.imageView.visibility = View.GONE
        }

        viewModel.errorVideo.observe(this) { error ->
            Snackbar.make(binding.root, error.message, Snackbar.LENGTH_LONG).show()
            binding.imageView.visibility = View.VISIBLE
        }
    }

    private fun initViews(){


        val movie: MovieEntity? = intent.extras?.getSerializable("movie") as MovieEntity?
        movie?.let { viewModel.setMovie(it) }


    }

    override fun onClickMovie(movie: MovieEntity, movieImageView: ImageView) {
        viewModel.setMovie(movie)
    }

    private fun loadingMovies(){
        binding.lyRecommendations.shimmerMovies.visibility = View.VISIBLE
        binding.lyRecommendations.rvRecommendations.visibility = View.GONE
    }

    private fun loadedMovies(){
        binding.lyRecommendations.shimmerMovies.visibility = View.GONE
        binding.lyRecommendations.rvRecommendations.visibility = View.VISIBLE
    }

    private fun emptyMovies(){
        binding.lyRecommendations.root.visibility = View.GONE
    }
}