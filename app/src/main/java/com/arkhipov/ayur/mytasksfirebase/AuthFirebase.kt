package com.arkhipov.ayur.mytasksfirebase

import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth

class AuthFirebase(val mAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    fun getCurrentUser() = mAuth.currentUser

    fun createAccount(email: String, password: String,
                      completeListener: CompleteListener) {
        if (!isNullEmailPassword(email, password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            completeListener.complete()
                        } else {
                            completeListener.notComplete(task.exception!!)
//                            completeListener.notComplete(task.exception!!)
                        }

                        // ...
                    }
        }

    }

    fun signIn(email: String, password: String,
               completeListener: CompleteListener) {
        if (!isNullEmailPassword(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            completeListener.complete()
                        } else {
                            completeListener.notComplete(task.exception!!)
//                            completeListener.notComplete(task.exception!!)
                        }

                        // ...
                    }
        }

    }

    fun isNullEmailPassword(email: String, password: String): Boolean {
        return TextUtils.isEmpty(email) && TextUtils.isEmpty(password)
    }

    interface CompleteListener {
        fun complete()
        fun notComplete(exception: Exception)
        /*interface Complete {
            fun complete()
        }

        interface NotComplete {
            fun notComplete(exception: Exception)
        }*/
    }

}