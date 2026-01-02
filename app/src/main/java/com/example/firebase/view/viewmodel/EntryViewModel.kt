package com.example.firebase.view.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.firebase.modeldata.UiStateSiswa
import com.example.firebase.repositori.RepositoriSiswa
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.firebase.modeldata.DetailSiswa
import com.example.firebase.modeldata.toDataSiswa

class EntryViewModel (private val repositoriSiswa: RepositoriSiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UiStateSiswa())
    private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa): Boolean
    {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UiStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    /* Fungsi untuk menyimpan data yang di entry*/
    suspend fun addSiswa() {
        if (validasiInput()) {
            repositoriSiswa.postDataSiswa((uiStateSiswa.detailSiswa.toDataSiswa()))
        }
    }
}