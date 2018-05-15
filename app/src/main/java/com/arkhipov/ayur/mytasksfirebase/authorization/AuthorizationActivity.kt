package com.arkhipov.ayur.mytasksfirebase.authorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.arkhipov.ayur.mytasksfirebase.App
import com.arkhipov.ayur.mytasksfirebase.AuthFirebase
import com.arkhipov.ayur.mytasksfirebase.AuthFirebase.CompleteListener
import com.arkhipov.ayur.mytasksfirebase.R
import com.arkhipov.ayur.mytasksfirebase.auth_profile.AuthProfileActivity
import com.arkhipov.ayur.mytasksfirebase.log.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.toast.view.*
import java.util.*


class AuthorizationActivity : AppCompatActivity() {

    private companion object {
        val RC_SIGN_IN: Int = 123
    }

    private lateinit var mAuth: AuthFirebase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        mAuth = App.getAuth()

        btn_signIn.setOnClickListener(View.OnClickListener {
            signIn(et_email.text.toString(), et_password.text.toString())
        })


        tv_registration.setOnClickListener {
            createAccount(et_email.text.toString(), et_password.text.toString())
        }

        btn_startSignIn.setOnClickListener {
            startSignInActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.getCurrentUser()
        //updateUI(currentUser)
    }

    var createAuth: CompleteListener? = null

    fun some() {

    }

    fun createAccount(email: String, password: String) {
        mAuth.createAccount(email, password, object : CompleteListener {
            override fun complete() {
                Log.d("createUserWithEmail:success")
                val user = mAuth.getCurrentUser()
                updateUI(user)
                registrationSuccesfull()
            }

            override fun notComplete(exception: Exception) {
                Log.w("createUserWithEmail:failure", exception)
                //Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                outToast("Authentication failed.")
                updateUI(null)
            }

        })
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//
//                    } else {
//                        // If sign in fails, display a message to the user.
//
//                    }
//
//                    // ...
//                }
    }

    fun outToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun signIn(email: String, password: String) {
        mAuth.signIn(email, password,
                object : CompleteListener {
            override fun complete() {
                /*Log.d("signInWithEmail:success")
                val user = mAuth.getCurrentUser()
                updateUI(user)*/
                completeSignIn()
            }

            override fun notComplete(exception: Exception) {
                /*Log.w("signInWithEmail:failure", exception)
                //Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                outToast("Authentication failed.")
                updateUI(null)*/
                Log.w("signInWithEmail:failure", exception)
                //Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                outToast("Authentication failed.")
                updateUI(null)
            }
        }

        /*        object : CompleteListener.Complete {
                    override fun complete() {
                        completeSignIn()
                    }

                }, object : CompleteListener.NotComplete {
                    override fun notComplete(exception: Exception) {
                        notCompleteSignIn(exception)
                    }
        }*/)
        /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signInWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }*/
    }

    fun completeSignIn(){
        Log.d("signInWithEmail:success")
        val user = mAuth.getCurrentUser()
        updateUI(user)
    }

    fun notCompleteSignIn(exception: Exception) {
        Log.w("signInWithEmail:failure", exception)
        //Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
        outToast("Authentication failed.")
        updateUI(null)
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            showToast(true)
            goToProfileActivity()
        } else if (user == null) {
            showToast(false)
        }
    }

    fun showToast(value: Boolean) {
        if (value) {
            val view = layoutInflater.inflate(R.layout.toast, findViewById(R.id.toast_layout))

            view.textToast.text = "Good"

            val toast = Toast(this)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = view
            toast.show()
        } else {
            val view = layoutInflater.inflate(R.layout.toast, findViewById(R.id.toast_layout))

            view.textToast.text = "Bad"

            val toast = Toast(this)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = view
            toast.show()
        }

    }

    fun registrationSuccesfull() {
        val view = layoutInflater.inflate(R.layout.toast, findViewById(R.id.toast_layout))

        view.textToast.text = "Registration Succesfull"

        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = Toast.LENGTH_LONG
        toast.view = view
        toast.show()
    }

    fun goToProfileActivity() {
        val intent = Intent(this, AuthProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun startSignInActivity() {
        val providers: List<AuthUI.IdpConfig> = Arrays.asList(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                showToast(true)
                registrationSuccesfull()
            } else {
                showToast(false)
            }
        }
    }
}
