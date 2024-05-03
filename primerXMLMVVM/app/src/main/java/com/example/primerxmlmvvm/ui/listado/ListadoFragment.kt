package com.example.primerxmlmvvm.ui.listado

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.FragmentListadoBinding
import com.example.primerxmlmvvm.databinding.FragmentSumaBinding
import com.example.primerxmlmvvm.ui.suma.SumaViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [ListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListadoFragment : Fragment() {

    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListadoBinding.inflate(inflater, container, false)
        return binding.root
    }

}