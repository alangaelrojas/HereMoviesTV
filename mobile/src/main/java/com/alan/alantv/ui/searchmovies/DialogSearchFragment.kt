package com.alan.alantv.ui.searchmovies

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alan.alantv.R
import com.alan.alantv.databinding.FragmentDialogSearchBinding
import com.alan.alantv.ui.moviedetail.DetailMovieActivity
import com.alan.alantv.ui.movies.MovieAdapter
import com.alan.alantv.ui.movies.MoviesViewModel
import com.alan.alantv.ui.movies.OnClickMovieListener
import com.alan.core.data.wrappers.MovieEntity


class DialogSearchFragment : DialogFragment(), OnClickMovieListener, TextWatcher {

    private lateinit var binding: FragmentDialogSearchBinding
    private val viewModel by viewModels<MoviesViewModel>()

    private lateinit var moviesAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)

        moviesAdapter = MovieAdapter(this, requireContext())

        setObservers()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDialogSearchBinding.inflate(inflater)
        binding.imgBack.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onClickMovie(movie: MovieEntity, movieImageView: ImageView) {
        activity?.apply {

            val intent = Intent(this, DetailMovieActivity::class.java).apply {
                putExtra("movie", movie)
            }
            val options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName")

            startActivity(intent, options.toBundle())
        }
    }

    private fun setObservers(){

        viewModel.searches.observe(this, this::movies)
        viewModel.errorSearches.observe(this){ error -> run {
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

    private fun movies(movies: List<MovieEntity>){

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
            //Buscar articulos mientras se escribe
            viewModel.getMoviesBySearch(binding.edtSearch.text.toString(), true)
            return
        }
    }
    //endregion
}