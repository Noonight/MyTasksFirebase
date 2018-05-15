package com.arkhipov.ayur.mytasksfirebase

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var mInstance: App
        fun getInstance() = mInstance

        lateinit var authFirebase: AuthFirebase
        fun getAuth() = authFirebase

        lateinit var fireStore: FirestoreFirebase
        fun getFirestore() = fireStore
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        authFirebase = AuthFirebase()
        fireStore = FirestoreFirebase()
    }
}