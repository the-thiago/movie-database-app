package com.br.thiago.themoviedatabaseapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.br.thiago.themoviedatabaseapp.adapter.MovieAdapter
import com.br.thiago.themoviedatabaseapp.databinding.FragmentFavoritesBinding
import com.br.thiago.themoviedatabaseapp.model.Movie

class FavoritesFragment : Fragment(), FavoritesContract.View {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { MovieAdapter(::clickItem) }
    private var presenter: FavoritesPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = FavoritesPresenter(this)
        binding.recyclerView.adapter = adapter
        presenter?.getAllMovies()
    }

    private fun clickItem(movie: Movie) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(
                movieId = movie.movieId,
                isFromDatabase = true
            )
        )
    }

    override fun showMovieList(movies: List<Movie>) {
        binding.tvNoFavoriteMovieText.visibility = View.GONE
        adapter.setItems(movies)
    }

    override fun showNoFavoriteMovieText() {
        binding.tvNoFavoriteMovieText.visibility = View.VISIBLE
    }

}