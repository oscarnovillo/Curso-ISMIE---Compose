package com.example.primerxmlmvvm.ui.coches.listado



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.CocheViewBinding

import com.example.primerxmlmvvm.domain.modelo.Coche

class CochesAdapter(
    val actions: CochesActions,

) : ListAdapter<Coche, CochesAdapter.ItemViewholder>(DiffCallback()) {


    interface CochesActions {
        fun onItemClick(coche: Coche)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {

        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.coche_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }


    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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


    class DiffCallback : DiffUtil.ItemCallback<Coche>() {
        override fun areItemsTheSame(oldItem: Coche, newItem: Coche): Boolean {
            return oldItem.matricula == newItem.matricula
        }

        override fun areContentsTheSame(oldItem: Coche, newItem: Coche): Boolean {
            return oldItem == newItem
        }
    }
}