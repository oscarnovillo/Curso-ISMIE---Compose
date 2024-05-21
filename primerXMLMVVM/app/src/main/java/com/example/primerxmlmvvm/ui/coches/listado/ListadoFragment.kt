package com.example.primerxmlmvvm.ui.coches.listado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.FragmentListadoCochesBinding
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [ListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// MenuProvider para cambiar la Top appbar
@AndroidEntryPoint
class ListadoFragment : Fragment(), MenuProvider {

    // el binding en Fragments con estas dos lineas.
    private var _binding: FragmentListadoCochesBinding? = null
    private val binding get() = _binding!!


    private val viewModel: ListadoViewModel by viewModels()

    private lateinit var cocheAdapter: CochesAdapter

    private val touchHelper by lazy {
        ItemTouchHelper(cocheAdapter.swipeGesture)
    }


    private val callback by lazy {
        configContextBar()
    }

    private var actionMode: ActionMode? = null

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

        configRecyclerView()
        configAppBar()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { mainState ->
                    cocheAdapter.submitList(mainState.coches)

                    if (mainState.selectMode) {
                        // quita el swipe cuando estas en multiseleccion
                        touchHelper.attachToRecyclerView(null)
                        cocheAdapter.startSelectMode()
                        actionMode?.title = "${mainState.cochesSeleccionados.size} seleccionados"

                    } else {
                        // pone el swipe cuando no estas en multiseleccion
                        touchHelper.attachToRecyclerView(binding.listado)
                        cocheAdapter.resetSelectMode()
                        actionMode?.finish()
                    }
                    cocheAdapter.setSelectedItems(mainState.cochesSeleccionados)



                    mainState.event?.let { uiEvent ->

                        Timber.d("evento mostrado")
                        when (uiEvent) {

                            is UiEvent.ShowSnackbar -> {
                                val snackbar = Snackbar.make(
                                    requireView(),
                                    uiEvent.message,
                                    Snackbar.LENGTH_SHORT
                                )
                                uiEvent.action?.let {
                                    snackbar.setAction(uiEvent.action) {
                                        viewModel.handleEvent(ListadoEvent.UndoDeleteCoche)
                                    }
                                }
                                snackbar.show()
                                viewModel.handleEvent(ListadoEvent.UiEventDone)
                            }

                            else -> {
                                Timber.d("evento no manejado")
                            }
                        }

                        viewModel.handleEvent(ListadoEvent.UiEventDone)
                    }
                }
            }
        }

    }

    private fun configAppBar() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }


    private fun configRecyclerView() {
        with(binding) {
            // configura el layout, grid, linear, etc.
            val layoutManager = LinearLayoutManager(requireView().context)
            listado.layoutManager = layoutManager

            val dividerItemDecoration = DividerItemDecoration(
                listado.context,
                layoutManager.orientation
            )
            listado.addItemDecoration(dividerItemDecoration)

            // configura el adapter
            cocheAdapter = CochesAdapter(
                context = requireContext(),
                actions = object : CochesAdapter.CochesActions {
                    override fun onItemClick(coche: Coche) {
                        val action =
                            ListadoFragmentDirections.actionListadoFragmentToDetalleFragment(coche.matricula)
                        findNavController().navigate(action)
                    }

                    override fun onDelete(coche: Coche) {
                        this@ListadoFragment.onDelete(coche)
                    }

                    override fun onSelectCoche(coche: Coche) {
                        viewModel.handleEvent(ListadoEvent.SelectCoche(coche))
                    }

                    override fun onStartSelectMode(coche: Coche) {
                        startSelectMode()
                        viewModel.handleEvent(ListadoEvent.StartSelectMode(coche))
                    }
                })
            listado.adapter = cocheAdapter

            // configura los swipe gestures
            // se hace en el collect del estado porque depende del selectmode
//            val touchHelper = ItemTouchHelper(cocheAdapter.swipeGesture)
//            touchHelper.attachToRecyclerView(binding.listado)


        }
    }


    private fun startSelectMode() {
        (requireActivity() as AppCompatActivity).startSupportActionMode(callback)?.let {
            actionMode = it;
        }
    }

    private fun configContextBar() =
        object : ActionMode.Callback {

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                requireActivity().menuInflater.inflate(R.menu.context_bar, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return when (item?.itemId) {
                    R.id.borrar -> {
                        viewModel.handleEvent(ListadoEvent.DeleteCocheSeleccionados)
                        true
                    }

                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                viewModel.handleEvent(ListadoEvent.EndSelectMode)

            }

        }

    private fun onDelete(coche: Coche) {
        viewModel.handleEvent(ListadoEvent.DeleteCoche(coche))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        // añade opciones al top bar
        menuInflater.inflate(R.menu.menu_appbar_search, menu)


        // controla la busqueda en el top bar
        val actionSearch = menu.findItem(R.id.search).actionView as SearchView

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    Timber.d("Search $newText")
                    viewModel.handleEvent(ListadoEvent.ChangeFiltro(newText))
                }

                return false
            }


        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

}