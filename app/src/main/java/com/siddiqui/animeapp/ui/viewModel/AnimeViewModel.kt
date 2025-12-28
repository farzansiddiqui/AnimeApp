package com.siddiqui.animeapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siddiqui.animeapp.data.db.AnimeDetailEntity
import com.siddiqui.animeapp.data.db.AnimeEntity
import com.siddiqui.animeapp.data.repository.AnimeRepository
import com.siddiqui.animeapp.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AnimeViewModel(private val repo: AnimeRepository) : ViewModel() {

    val animeList = MutableStateFlow<UiState<List<AnimeEntity>>>(UiState.Loading)
    val animeDetail =
        MutableStateFlow<UiState<AnimeDetailEntity>>(UiState.Loading)



    fun fetchAnimeList() {
        viewModelScope.launch {
            animeList.value = UiState.Loading
            animeList.value = UiState.Success(repo.getAnimeList())
        }
    }

    fun fetchAnimeDetail(animeId: Int) {
        viewModelScope.launch {
            animeDetail.value = UiState.Loading
            try {
                val detail: AnimeDetailEntity =
                    repo.getAnimeDetail(animeId)

                animeDetail.value = UiState.Success(detail)
            } catch (e: Exception) {
                animeDetail.value =
                    UiState.Error("Details unavailable offline")
            }
        }
    }


}
