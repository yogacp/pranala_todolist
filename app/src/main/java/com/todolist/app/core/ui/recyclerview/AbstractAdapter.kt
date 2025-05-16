package com.todolist.app.core.ui.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.todolist.app.core.extension.viewBinding
import com.todolist.app.core.ui.recyclerview.AbstractAdapter.Holder

@SuppressLint("NotifyDataSetChanged")
abstract class AbstractAdapter<T : ViewBinding, ITEM> constructor(
    protected var itemList: List<ITEM>,
    private val bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
) : RecyclerView.Adapter<Holder>() {

    var mSelectedItem = -1
    var binding: T? = null

    init {
        update(itemList)
        notifyItemRangeChanged(0, itemList.size)
    }

    override fun getItemCount(): Int = itemList.size
    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding = parent.viewBinding(bindingClass)
        this.binding = viewBinding

        val viewHolder = Holder(viewBinding.root)
        val itemView = viewHolder.itemView

        itemView.tag = viewHolder
        itemView.setOnClickListener {
            val adapterPosition = viewHolder.bindingAdapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                mSelectedItem = adapterPosition
                onItemClick(itemView, adapterPosition)
                notifyItemRangeChanged(0, itemList.size)
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.bind(item)
    }

    override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newItems: List<ITEM>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(DiffUtilCallback(itemList, newItems))
    }

    private fun update(items: List<ITEM>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun add(item: ITEM) {
        itemList.toMutableList().add(item)
        notifyItemInserted(itemList.size)
    }

    private fun remove(position: Int) {
        itemList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    protected open fun View.bind(item: ITEM) {}
    protected open fun onViewRecycled(itemView: View) {}
    protected open fun onItemClick(itemView: View, position: Int) {}
    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}