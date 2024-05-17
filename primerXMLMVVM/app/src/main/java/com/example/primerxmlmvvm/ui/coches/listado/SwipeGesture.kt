package com.example.primerxmlmvvm.ui.coches.listado

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

import android.content.Context

import androidx.core.content.ContextCompat
import com.example.primerxmlmvvm.R
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


// Clase para configurar todos los swipe gestures.
abstract class SwipeGesture(context: Context) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){

   val deleteColor = ContextCompat.getColor(context, R.color.md_theme_error)

    // metodo para el drag and drop
    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {return false}

    // metodo para repintar en el swipe
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        // esta clase facilita el pintado de la celda que hace el swipe
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addSwipeLeftBackgroundColor(deleteColor)
            .addSwipeLeftActionIcon(R.drawable.baseline_delete_sweep_24)
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}