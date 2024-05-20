package com.example.primerxmlmvvm.ui.users.listado

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
import com.example.primerxmlmvvm.databinding.FragmentListadoUsersBinding
import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.domain.modelo.User
import com.example.primerxmlmvvm.ui.users.listado.ListadoContract.*


import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ListadoFragment : Fragment() {

    private var _binding: FragmentListadoUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoViewModel by viewModels()

    private lateinit var usersAdapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListadoUsersBinding.inflate(inflater, container, false)
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

            usersAdapter = UsersAdapter(actions = object : UsersAdapter.UsersActions {
                override fun onItemClick(user: User) {

                    val action = ListadoFragmentDirections.actionListadoUsersFragmentToDetalleUsersFragment(
                        user.id.toLong()
                    )
                    findNavController().navigate(action)
                }
            })
            listado.adapter = usersAdapter

            viewModel.handleEvent(ListadoEvent.getUsers)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { mainState ->
                        binding.progressBar.visibility = if (mainState.isLoading) View.VISIBLE else View.GONE
                        usersAdapter.submitList(mainState.users)
                        mainState.error?.let {
                            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                            viewModel.handleEvent(ListadoEvent.ErrorMostrado)
                        }
                    }
                }
            }
        }
    }

}