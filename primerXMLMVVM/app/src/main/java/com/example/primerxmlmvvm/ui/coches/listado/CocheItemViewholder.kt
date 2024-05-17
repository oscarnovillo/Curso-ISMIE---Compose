package com.example.primerxmlmvvm.ui.coches.listado

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.primerxmlmvvm.databinding.CocheViewBinding
import com.example.primerxmlmvvm.domain.modelo.Coche

class CocheItemViewholder (itemView: View, val actions: CochesAdapter.CochesActions) : RecyclerView.ViewHolder(itemView) {

        private val binding = CocheViewBinding.bind(itemView)

        fun bind(item: Coche) {
            with(binding) {
                textMatricula.text = item.matricula
                textModelo.text = item.modelo
                textMarca.text = item.marca
                itemView.setOnClickListener {
                    actions.onItemClick(item)
                }
            }
        }
    }
