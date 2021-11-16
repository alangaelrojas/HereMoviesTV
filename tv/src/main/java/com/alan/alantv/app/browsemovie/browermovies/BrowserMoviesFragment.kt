package com.alan.alantv.app.browsemovie.browermovies

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.alantv.app.dashboard.lifecycle.MoviesViewModel
import com.alan.alantv.app.detailmovie.DetailsActivity
import com.alan.alantv.databinding.FragmentBrowserMoviesBinding
import com.alan.core.data.wrappers.MovieEntity

class BrowserMoviesFragment : Fragment(), OnClickMovieListener, TextWatcher {

    private lateinit var binding: FragmentBrowserMoviesBinding

    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MovieAdapter(this, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBrowserMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
            setHasFixedSize(true)
        }

        emptyMovies("Not movies yet", false)

        binding.edtSearch.addTextChangedListener(this)
        binding.edtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if (binding.edtSearch.text.toString().isEmpty()){
                    emptyMovies("Not movies yet", false)
                    return@setOnEditorActionListener false
                }

                viewModel.getMoviesBySearch(binding.edtSearch.text.toString(), true)
                loadingMovies()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setObservers(){

        viewModel.searches.observe(viewLifecycleOwner, ::onMovies)
        viewModel.errorSearches.observe(viewLifecycleOwner){ error ->
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

    private fun onMovies(movies: List<MovieEntity>){
        moviesAdapter.addPopularMovies(movies)
        loadedMovies()
    }

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

    //region :: addTextChangedListener
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {
        if (binding.edtSearch.text.toString().isEmpty()){
            emptyMovies("Not movies yet", false)
        }
        while (binding.edtSearch.text.toString().isNotEmpty()){
            loadingMovies()
            viewModel.getMoviesBySearch(binding.edtSearch.text.toString(), true)
            return
        }
    }
    //endregion

    override fun onClickMovie(movie: MovieEntity, movieImageView: ImageView) {
        activity?.apply {

            val intent = Intent(requireActivity(), DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.MOVIE, movie)

            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                movieImageView,
                DetailsActivity.SHARED_ELEMENT_NAME
            ).toBundle()
            startActivity(intent, bundle)
        }
    }
}