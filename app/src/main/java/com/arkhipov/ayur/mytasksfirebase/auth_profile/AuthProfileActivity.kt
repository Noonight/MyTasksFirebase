package com.arkhipov.ayur.mytasksfirebase.auth_profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.arkhipov.ayur.mytasksfirebase.App
import com.arkhipov.ayur.mytasksfirebase.R
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_auth_profile.*


class AuthProfileActivity : AppCompatActivity() {

    lateinit var tvProfile: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_profile)
        //tv_profile.autoLinkMask

//        tvProfile = findViewById(R.id.tv_profile) as TextView

        val firebaseUser: FirebaseUser = App.getAuth().getCurrentUser()!!

        tv_profile.text = "${firebaseUser.email},\n" +
                "Display name: ${firebaseUser.displayName},\n" +
                "Верифицирован?: ${firebaseUser.isEmailVerified}"

        btn_task_submit.setOnClickListener {

        }

    }

    fun submitDataFireStore() {

    }


}