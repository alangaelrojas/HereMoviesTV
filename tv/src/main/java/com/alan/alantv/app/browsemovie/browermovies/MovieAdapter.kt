package com.alan.alantv.app.browsemovie.browermovies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alan.alantv.R
import com.alan.core.data.wrappers.MovieEntity
import com.alan.core.utils.DiffCallback
import com.alan.core.utils.getProgressDrawable
import com.alan.core.utils.loadImage

class MovieAdapter(
    private val movieListener: OnClickMovieListener,
    private val context: Context
) : RecyclerView.Adapter<MovieAdapter.HolderMovie>() {

    private val mMovies = ArrayList<MovieEntity>()

    fun addPopularMovies(movies: List<MovieEntity>){
        val diffCallbck = DiffCallback(mMovies, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallbck)
        mMovies.clear()
        mMovies.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addTopRated(movies: List<MovieEntity>){
        val diffCallbck = DiffCallback(mMovies, movies)
        val diffResult = DiffUtil.calculateDiff(diffCallbck)
        mMovies.clear()
        mMovies.addAll(movies)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMovie {
        val v = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return HolderMovie(v)
    }

    override fun onBindViewHolder(holder: HolderMovie, position: Int) {

        holder.card.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
        holder.cover.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)

        mMovies[position].image?.let { holder.cover.loadImage(it, getProgressDrawable(context)) }
        holder.title.text = mMovies[position].title
        holder.description.text = mMovies[position].overview
        holder.average.text = mMovies[position].voteAverage.toString()

    }

    override fun getItemCount(): Int = mMovies.size

    inner class HolderMovie(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: CardView = itemView.findViewById(R.id.movieCard)
        val title: TextView = itemView.findViewById(R.id.title)
        val cover: ImageView = itemView.findViewById(R.id.cover)
        val description: TextView = itemView.findViewById(R.id.description)
        val average: TextView = itemView.findViewById(R.id.average)

        init {
            card.setOnClickListener{
                movieListener.onClickMovie(mMovies[adapterPosition], cover)
            }
        }
    }
}