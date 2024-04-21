package com.example.primerxmlmvvm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.primerxmlmvvm.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

private const val TEXT_VIEW = "text_view"

class MainActivity : AppCompatActivity() {


    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    private val viewModel: MainViewModel by viewModels()
    {
        MainViewModelFactory()
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


//            savedInstanceState?.let {
//                textView.text = savedInstanceState.getString(TEXT_VIEW)
//            }


            buttonSumar.setOnClickListener{
                viewModel.sumar()
            }
            buttonRestar.setOnClickListener{
                viewModel.restar()
            }



        }



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { mainState ->
                    binding.textView.text = mainState.contador.toString()
                }
            }
        }

    }

    //save instance state
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putString(TEXT_VIEW, binding.textView.text.toString())
//    }
}