package com.todolist.app.core.extension

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.todolist.app.BuildConfig
import com.todolist.app.core.ui.recyclerview.GeneralAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun debugMode(function: () -> Unit) {
    if (BuildConfig.DEBUG) {
        function()
    }
}

fun releaseMode(function: () -> Unit) {
    if (BuildConfig.BUILD_TYPE.equals("release", ignoreCase = true)) {
        function()
    }
}

inline fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

inline fun String?.notNullOrEmpty(f: (it: String) -> Unit): String? {
    return if (this != null && this.trim().isNotEmpty()) {
        f(this)
        this
    } else null
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>
fun <T> MutableStateFlow<T>.asStateFlow() = this as StateFlow<T>

inline fun <T : ViewBinding> ViewGroup.viewBinding(binding: (LayoutInflater, ViewGroup, Boolean) -> T): T {
    return binding(LayoutInflater.from(context), this, false)
}

@SuppressLint("NotifyDataSetChanged")
fun <T : ViewBinding, ITEM> RecyclerView.setup(
    items: List<ITEM>,
    bindingClass: (LayoutInflater, ViewGroup, Boolean) -> T,
    bindHolder: View.(T?, ITEM,Int) -> Unit,
    itemClick: View.(ITEM) -> Unit = {},
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): GeneralAdapter<T, ITEM> {
    val generalAdapter by lazy {
        GeneralAdapter(items, bindingClass,
            { binding: T?, item: ITEM, position:Int ->
                bindHolder(binding, item, position)
            }, { data, position ->
                itemClick(data)
            }
        )
    }

    layoutManager = manager
    adapter = generalAdapter
    (adapter as GeneralAdapter<*, *>).notifyDataSetChanged()

    return generalAdapter
}

fun View.toast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}