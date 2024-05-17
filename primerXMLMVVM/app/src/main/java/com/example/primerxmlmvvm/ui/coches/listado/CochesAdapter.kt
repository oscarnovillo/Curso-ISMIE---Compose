package com.example.primerxmlmvvm.ui.coches.listado



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.CocheViewBinding

import com.example.primerxmlmvvm.domain.modelo.Coche
import com.example.primerxmlmvvm.ui.users.listado.UsersAdapter

class CochesAdapter(
    val context : Context,
    val actions: CochesActions,

    ) : ListAdapter<Coche, CocheItemViewholder>(DiffCallback()) {


    interface CochesActions {
        fun onItemClick(coche: Coche)
        abstract fun onDelete(coche: Coche)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocheItemViewholder {

        return CocheItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.coche_view, parent, false),
            actions,

        )
    }

    override fun onBindViewHolder(holder: CocheItemViewholder, position: Int) = with(holder) {
        val item = getItem(position)
        bind(item)
    }





    class DiffCallback : DiffUtil.ItemCallback<Coche>() {
        override fun areItemsTheSame(oldItem: Coche, newItem: Coche): Boolean {
            return oldItem.matricula == newItem.matricula
        }

        override fun areContentsTheSame(oldItem: Coche, newItem: Coche): Boolean {
            return oldItem == newItem
        }
    }


    val swipeGesture = object : SwipeGesture(context) {


        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //if (!selectedMode) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    //selectedPersonas.remove(currentList[viewHolder.adapterPosition])
                    actions.onDelete(currentList[viewHolder.absoluteAdapterPosition])
//                    if (selectedMode)
//                        actions.itemHasClicked(currentList[viewHolder.adapterPosition])
                }
            }
            //}
        }
    }
}