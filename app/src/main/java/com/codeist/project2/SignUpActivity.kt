package com.codeist.project2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.codeist.project2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener() {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailEt.error = "Invalid Email Address"
                binding.emailEt.requestFocus()
                binding.passEt.requestFocus()
            }

            if (pass.length < 8) {
                binding.passEt.error = "Minimum 8 Character Password"
            }
            if (!pass.matches(".*[A-Z].*".toRegex())) {
                binding.passEt.error = "Minimum 8 Character Password"
            }
            if (!pass.matches(".*[a-z].*".toRegex())) {
                binding.passEt.error = "Minimum 8 Character Password"
            }
            if (!pass.matches(".*[@#\$%^&+=].*".toRegex())) {
                binding.passEt.error = "Minimum 8 Character Password"
            }

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Signed In Successfully!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    binding.passEt.error = "Passwords not matching"
                    binding.confirmPassEt.error = "Passwords not matching"
                    binding.confirmPassEt.requestFocus()
                    binding.passEt.requestFocus()
                }
            }else{
                binding.emailEt.error = "Email cannot be Empty"
                binding.passEt.error = "Password cannot be Empty"
                binding.confirmPassEt.error = "Password cannot be Empty"
                binding.emailEt.requestFocus()
                binding.passEt.requestFocus()
            }
        }
    }
}