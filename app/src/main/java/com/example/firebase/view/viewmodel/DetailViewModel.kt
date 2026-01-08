@file:OptIn(InternalSerializationApi::class)
package com.example.firebase.view.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import com.example.firebase.modeldata.Siswa
import com.example.firebase.repositori.RepositoriSiswa
import com.example.firebase.view.route.DestinasiDetail
import kotlinx.serialization.InternalSerializationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface StatusUIDetail {
    data class Sucess(val satusiswa: Siswa?) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}
class DetailViewModel(savedStateHandle: SavedStateHandle,private val repositoriSiswa:
RepositoriSiswa): ViewModel() {

    private val idSiswa: Long =
        savedStateHandle.get<String>(DestinasiDetail.itemIdArg)?.toLong()
            ?: error("idSiswa tidak ditemukan di SavedStateHandle")
    var statusUIDetail: StatusUIDetail by mutableStateOf(StatusUIDetail.Loading)
        private set

    init {
        getSatuSiswa()
    }

    fun getSatuSiswa(){
        viewModelScope.launch {
            statusUIDetail = StatusUIDetail.Loading
            statusUIDetail = try {
                StatusUIDetail.Sucess(satusiswa = repositoriSiswa.getSatuSiswa(idSiswa))
            }
            catch (e: IOException){
                StatusUIDetail.Error
            }
            catch (e: Exception){
                StatusUIDetail.Error
            }
        }
    }

    suspend fun hapusSatuSiswa() {
        try {
            repositoriSiswa.hapusSatuSiswa(idSiswa)
            println("Suksess Hapus Data: $idSiswa")
        }catch (e: Exception) {
            println("Gagal Hapus Data: ${e.message}")
        }
    }
}



