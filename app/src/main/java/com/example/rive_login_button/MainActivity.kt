package com.example.rive_login_button

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import app.rive.runtime.kotlin.core.Rive
import com.example.rive_login_button.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var username = ""
    private var password = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Rive.init(this)
        binding.loginButtonTrigger.bringToFront()

        setListeners()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setListeners() {
        binding.loginButtonTrigger.setOnClickListener {
            hideKeyboard(this)
            binding.tiPassword.clearFocus()
            binding.username.clearFocus()
            if (username.isEmpty() || password.isEmpty()) {
                binding.robot.setBooleanState("LoginRobot", "error", true)
            } else {
                binding.robot.setBooleanState("LoginRobot", "error", false)
                binding.loginButton.fireState("State Machine 1", "clicked")
                binding.robot.setBooleanState("LoginRobot", "loging", true)
            }
        }

        binding.username.setOnFocusChangeListener { view, boolean ->
            if (boolean) {
                binding.robot.setBooleanState("LoginRobot", "error", false)
                binding.robot.controller.setBooleanState("LoginRobot", "username", true)
            } else {
                binding.robot.controller.setBooleanState("LoginRobot", "username", false)
            }
        }

        binding.tiPassword.setOnFocusChangeListener { view, boolean ->
            if (boolean) {
                binding.robot.setBooleanState("LoginRobot", "error", false)
                binding.robot.controller.setBooleanState("LoginRobot", "password", true)
            } else {
                binding.robot.controller.setBooleanState("LoginRobot", "password", false)
            }
        }

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                username = p0.toString()
            }
        })

        binding.tiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                password = p0.toString()
            }
        })
    }
}

