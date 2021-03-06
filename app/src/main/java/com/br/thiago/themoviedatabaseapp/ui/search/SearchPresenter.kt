package com.br.thiago.themoviedatabaseapp.ui.search

import android.net.ConnectivityManager
import com.br.thiago.themoviedatabaseapp.api.MovieService
import com.br.thiago.themoviedatabaseapp.model.Movie
import com.br.thiago.themoviedatabaseapp.util.hasInternetConnection
import com.br.thiago.themoviedatabaseapp.util.toMovies
import kotlinx.coroutines.*

class SearchPresenter(private var view: SearchContract.View?) : SearchContract.Presenter {

    private var searchJob: Job? = null

    override fun searchMovies(query: String, connectivityManager: ConnectivityManager) {
        if (hasInternetConnection(connectivityManager)) {
            safeSearchMovies(query)
        } else {
            view?.showNoInternetConnectionWarning()
        }
    }

    private fun safeSearchMovies(query: String) {
        searchJob?.cancel()
        searchJob = CoroutineScope(Dispatchers.IO).launch {
            var movies = emptyList<Movie>()
            val moviesRequest = MovieService.create().searchMovies(query)
            if (moviesRequest.isSuccessful) {
                moviesRequest.body()?.toMovies()?.let {
                    movies = it
                }
                withContext(Dispatchers.Main) {
                    view?.hideLoadingScreen()
                    if (movies.isEmpty()) {
                        view?.showEmptySearch()
                    } else {
                        view?.showSearchedMovies(movies)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    view?.showEmptySearch()
                    view?.hideLoadingScreen()
                }
            }
        }
    }

    override fun destroyView() {
        view = null
    }

}