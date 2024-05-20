package com.example.primerxmlmvvm.ui.users.listado

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.primerxmlmvvm.R
import com.example.primerxmlmvvm.databinding.UserViewBinding
import com.example.primerxmlmvvm.domain.modelo.User


class UsersAdapter (

    val actions: UsersActions,

    ) : ListAdapter<User, UsersAdapter.ItemViewholder>(DiffCallback()) {


        interface UsersActions {
            fun onItemClick(user: User)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {

            return ItemViewholder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_view, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ItemViewholder, position: Int) = with(holder) {
            val item = getItem(position)
            bind(item)
        }


        inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val binding = UserViewBinding.bind(itemView)

            fun bind(item: User) {
                with(binding) {
                    textId.text = item.id.toString()
                    textNombre.text = item.name
                    textCompany.text = item.company

                    imageFoto.load(item.fotoUrl){
                        crossfade(true)

                        transformations(CircleCropTransformation())
                    }
                    val dimensionInDp = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        75F,
                        itemView.resources.displayMetrics
                    ).toInt()
                    imageFoto.layoutParams.height = dimensionInDp
                    imageFoto.layoutParams.width = dimensionInDp
                    imageFoto.requestLayout()
                    itemView.setOnClickListener {
                        actions.onItemClick(item)
                    }
                }
            }
        }


        class DiffCallback : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
}