package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edtEmail: EditText;
    private lateinit var edtPassword: EditText;
    private lateinit var btnLogin: Button;
    private lateinit var btnSignUp: Button;

    private lateinit var mAuth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener {
            var intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            var email = edtEmail.text.toString();
            var password = edtPassword.text.toString();

            login(email, password);
        }

        if(mAuth.currentUser != null){
            val intent = Intent(this@Login, MainActivity::class.java);
            startActivity(intent);
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    val intent = Intent(this@Login, MainActivity::class.java);
                    startActivity(intent);
                }else {
                    Toast.makeText(
                        this@Login, "Invalid credentials",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }
    }
}