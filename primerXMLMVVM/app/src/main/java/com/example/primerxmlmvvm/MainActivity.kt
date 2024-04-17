package com.example.primerxmlmvvm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.primerxmlmvvm.databinding.ActivityMainBinding

private const val TEXT_VIEW = "text_view"

class MainActivity : AppCompatActivity() {


    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        with(binding) {
            setContentView(root)
            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }


            savedInstanceState?.let {
                textView.text = savedInstanceState.getString(TEXT_VIEW)
            }


            buttonSumar.setOnClickListener {
                val value = textView.text.toString().toInt()
                textView.text = (value + 1).toString()
            }

            buttonRestar.setOnClickListener {
                val value = textView.text.toString().toInt()
                textView.text = (value - 1).toString()
            }
        }

    }

    //save instance state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_VIEW, binding.textView.text.toString())
    }
}