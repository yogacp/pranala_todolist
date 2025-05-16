package com.todolist.app.presentation.homepage.view

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import com.todolist.app.core.extension.setup
import com.todolist.app.core.extension.toast
import com.todolist.app.databinding.ActivityHomepageBinding
import com.todolist.app.databinding.ItemTodosBinding
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.presentation.homepage.viewmodel.HomepageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomepageActivity : ComponentActivity() {

    private val viewModel by viewModel<HomepageViewModel>()
    private val binding by viewBinding<ActivityHomepageBinding>()

    val onBackPressed : OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }

    val linearManager = object : LinearLayoutManager(this) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressed)
        observeTodos()
        enableEdgeToEdge()
    }

    private fun observeTodos() {
        viewModel.todolist.observe(this) { todos ->
            loadTodos(todos)
        }
    }

    private fun loadTodos(todos: List<Todos>) = with(binding) {
        rvTodos.setup(
            items = todos,
            bindingClass = ItemTodosBinding::inflate,
            bindHolder = { binding, item, position ->
                binding?.tvTitle?.text = item.title
                binding?.checkbox?.isChecked = item.completed

                binding?.checkbox?.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked) {
                        toast("${item.title} is checked")
                    } else {
                        toast("${item.title} is unchecked")
                    }
                }
            },
            manager = linearManager
        )
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TodolistTheme {
//        Greeting("Android")
//    }
//}