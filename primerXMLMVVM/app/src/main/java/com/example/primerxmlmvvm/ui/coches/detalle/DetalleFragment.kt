package com.example.primerxmlmvvm.ui.coches.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.primerxmlmvvm.databinding.FragmentDetalleCochesBinding
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.ui.coches.detalle.DetalleContract.*
import com.example.primerxmlmvvm.ui.coches.listado.CochesAdapter
import com.example.primerxmlmvvm.ui.coches.listado.ListadoEvent
import com.example.primerxmlmvvm.ui.coches.listado.ListadoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetalleFragment : Fragment() {

    private var _binding: FragmentDetalleCochesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetalleCochesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : DetalleFragmentArgs by navArgs()
        viewModel.handleEvent(DetalleEvent.GetCoche(args.matricula))



        with(binding) {
            binding.buttonDelete.setOnClickListener {

                viewModel.handleEvent(DetalleEvent.DelCoche)
            }


            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { mainState ->
                        textColor.text = mainState.coche?.color
                        textMarca.text = mainState.coche?.marca
                        textMatricula.text = mainState.coche?.matricula
                        textModelo.text = mainState.coche?.modelo
                        mainState.error?.let {
                            Timber.d("error mostrado")
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                            viewModel.handleEvent(DetalleEvent.MensajeMostrado)
                        }
                        if (mainState.borrado)
                        {
                            //findNavController().navigateUp()
                             val action = DetalleFragmentDirections.actionDetalleFragmentToListadoFragment()
                            findNavController().navigate(action)
                            //viewModel.handleEvent(DetalleEvent.BorradoMostrado)
                        }
                    }
                }
            }
        }
    }


}