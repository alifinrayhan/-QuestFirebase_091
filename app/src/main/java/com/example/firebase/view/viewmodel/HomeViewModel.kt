package com.example.firebase.view.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebase.modeldata.Siswa
import com.example.firebase.repositori.RepositoriSiswa
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

sealed interface StatusUiSiswa {
    data class Success(val siswa: List<Siswa> = listOf()) : StatusUiSiswa
    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}
class HomeViewModel (private val repositoriSiswa: RepositoriSiswa): ViewModel() {
    var statusUiSiswa: StatusUiSiswa by mutableStateOf(StatusUiSiswa.Loading)
    private set

    init {
        loadSiswa()
    }

    fun loadSiswa(){
        viewModelScope.launch {
            statusUiSiswa = StatusUiSiswa.Loading
            statusUiSiswa = try {
                StatusUiSiswa.Success(repositoriSiswa.getDataSiswa())
            }catch (e: IOException){
                StatusUiSiswa.Error
            }
            catch (e: Exception){
                StatusUiSiswa.Error
            }
        }
    }
}