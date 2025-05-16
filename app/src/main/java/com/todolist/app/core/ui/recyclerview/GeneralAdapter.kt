package com.todolist.app.core.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.viewbinding.ViewBinding

class GeneralAdapter<T : ViewBinding, ITEM>(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    private val bindHolder: View.(T?, ITEM, Int) -> Unit,
) : AbstractAdapter<T, ITEM>(items, bindingClass) {

    private var itemClick: View.(ITEM, Int) -> Unit = { _, _ -> }
    private var isRadioButton: Boolean = false
    var viewBinding: T? = null

    constructor(
        items: List<ITEM>,
        bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
        bindHolder: View.(T?, ITEM, Int) -> Unit,
        itemViewClick: View.(ITEM, Int) -> Unit = { _, _ -> },
        isRadioButton: Boolean = false,
        selectedPosition: Int = -1
    ) : this(items, bindingClass, bindHolder) {
        this.itemClick = itemViewClick
        this.isRadioButton = isRadioButton
        mSelectedItem = selectedPosition
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (position == holder.bindingAdapterPosition) {
            this.viewBinding = binding
            holder.itemView.bindHolder(binding, itemList[position], position)
        }

        if(isRadioButton) {
            (holder.itemView as RadioButton).isChecked = (position == mSelectedItem)
        }
    }

    override fun onItemClick(itemView: View, position: Int) {
        mSelectedItem = position
        itemView.itemClick(itemList[position], mSelectedItem)
    }
}