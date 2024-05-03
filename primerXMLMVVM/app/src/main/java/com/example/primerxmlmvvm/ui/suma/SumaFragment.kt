package com.example.primerxmlmvvm.ui.suma

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.FragmentSumaBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [SumaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SumaFragment : Fragment() {


    private var _binding: FragmentSumaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SumaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSumaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            buttonSumar.setOnClickListener{
                viewModel.handleEvent(SumaEvent.Sumar(1))
            }
            buttonRestar.setOnClickListener{
                viewModel.handleEvent(SumaEvent.Restar(1))
            }



        }



        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { mainState ->
                    binding.textView.text = mainState.contador.toString()
                    mainState.error?.let {
                        Timber.d("error mostrado")
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                        viewModel.handleEvent(SumaEvent.ErrorMostrado)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate ) {
                    viewModel.uiError.collect {
                        Timber.d("error mostrado Channel")
                        Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

}