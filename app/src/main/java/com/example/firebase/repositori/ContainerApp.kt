package com.example.firebase.repositori

import android.app.Application


interface ContainerApp {
    val repositoriSiswa: RepositoriSiswa
}

class DefaultContainerApp : ContainerApp {
    override val repositoriSiswa: RepositoriSiswa by lazy {
        FirebaseRepositoriSiswa()
    }

}

class AplikasiDataSiswa : Application() {
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        this.container = DefaultContainerApp()
    }

}