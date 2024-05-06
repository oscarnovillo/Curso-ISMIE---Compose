package com.example.primerxmlmvvm.ui.coches.listado

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.primerxmlmvvm.databinding.FragmentListadoCochesBinding
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [ListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListadoFragment : Fragment() {

    private var _binding: FragmentListadoCochesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoViewModel by viewModels()

    private lateinit var cocheAdapter: CochesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListadoCochesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {


            val layoutManager = LinearLayoutManager(requireView().context)
            listado.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(
                listado.context,
                layoutManager.orientation
            )
            listado.addItemDecoration(dividerItemDecoration)

            cocheAdapter = CochesAdapter(actions = object : CochesAdapter.CochesActions {
                override fun onItemClick(coche: Coche) {
                    val action = ListadoFragmentDirections.actionListadoFragmentToDetalleFragment(coche.matricula)
                    findNavController().navigate(action)
                }
            })
            listado.adapter = cocheAdapter

            viewModel.handleEvent(ListadoEvent.getCoches)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { mainState ->
                        cocheAdapter.submitList(mainState.coches)
                        mainState.error?.let {
                            Timber.d("error mostrado")
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                            viewModel.handleEvent(ListadoEvent.ErrorMostrado)
                        }
                    }
                }
            }
        }
    }

}