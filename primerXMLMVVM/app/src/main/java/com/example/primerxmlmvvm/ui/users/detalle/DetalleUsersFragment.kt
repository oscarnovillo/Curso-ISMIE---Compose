package com.example.primerxmlmvvm.ui.users.detalle


import android.os.Bundle
import android.util.TypedValue
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
import coil.load
import coil.transform.CircleCropTransformation
import com.example.primerxmlmvvm.databinding.FragmentDetalleUsersBinding
import com.example.primerxmlmvvm.ui.common.UiEvent
import com.example.primerxmlmvvm.ui.users.detalle.DetalleUsersContract.DetalleUsersEvent
import com.example.primerxmlmvvm.ui.users.detalle.DetalleUsersContract.DetalleUsersState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetalleUsersFragment : Fragment() {

    private var _binding: FragmentDetalleUsersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetalleUsersViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetalleUsersBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetalleUsersFragmentArgs by navArgs()
        viewModel.handleEvent(DetalleUsersEvent.GetUser(args.id.toInt()))



        with(binding) {

            binding.buttonDelete.setOnClickListener {

                viewModel.handleEvent(DetalleUsersEvent.DelUser(args.id.toInt()))
            }


            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collect { mainState: DetalleUsersState ->
                        mainState.user?.let {
                            textId.text = it.id.toString()
                            textName.text = it.name
                            textUsername.text = it.username
                            textEmail.text = it.email
                            textPhone.text = it.phone
                            textWebsite.text = it.website
                            textCompanyName.text = it.company
                        }

                        imagePhoto.load(mainState.user?.fotoUrl) {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                        val dimensionInDp = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            150F,
                            view.resources.displayMetrics
                        ).toInt()
                        imagePhoto.layoutParams.height = dimensionInDp
                        imagePhoto.layoutParams.width = dimensionInDp
                        imagePhoto.requestLayout()

                        mainState.event?.let {
                            when (it) {
                                UiEvent.PopBackStack -> findNavController().popBackStack()
                                is UiEvent.ShowSnackbar -> Snackbar.make(
                                    requireView(),
                                    it.message,
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                is UiEvent.Navigate -> TODO()
                            }
                            viewModel.handleEvent(DetalleUsersEvent.UiEventDone)
                        }
                    }
                }
            }
        }
    }


}


