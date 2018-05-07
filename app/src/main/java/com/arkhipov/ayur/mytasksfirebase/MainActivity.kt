package com.arkhipov.ayur.mytasksfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toast.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        btn_signIn.setOnClickListener(View.OnClickListener {
            signIn(et_email.text.toString(), et_password.text.toString())
        })

        tv_registration.setOnClickListener(View.OnClickListener {
            createAccount(et_email.text.toString(), et_password.text.toString())
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        //updateUI(currentUser)
    }

    fun createAccount(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Main Activity", "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                        registrationSuccesfull()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Main Activity", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }
    }

    fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(this::class.java.simpleName, "signInWithEmail:success")
                        val user = mAuth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(javaClass.simpleName, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }
    }

    fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            showToast(true)
        } else if (user == null){
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
        }
        else
        {
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

}
