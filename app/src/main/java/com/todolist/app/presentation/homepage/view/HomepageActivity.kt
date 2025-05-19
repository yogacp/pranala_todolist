package com.todolist.app.presentation.homepage.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.viewbinding.library.activity.viewBinding
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.todolist.app.R
import com.todolist.app.core.extension.safeCollect
import com.todolist.app.core.extension.setup
import com.todolist.app.core.utils.Resource
import com.todolist.app.core.utils.onSuccess
import com.todolist.app.databinding.ActivityHomepageBinding
import com.todolist.app.databinding.ItemTodosBinding
import com.todolist.app.domain.model.todos.Todos
import com.todolist.app.presentation.homepage.viewmodel.HomepageViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomepageActivity : ComponentActivity() {

    private val viewModel by viewModel<HomepageViewModel>()
    private val binding by viewBinding<ActivityHomepageBinding>()

    val onBackPressed: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finishAffinity()
        }
    }

    val gridLayoutManager = object : GridLayoutManager(this, 2) {
        override fun canScrollVertically(): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressed)
        fetchTodos()
        setupSwipeRefresh()
        setupAddButton()
        enableEdgeToEdge()
    }

    private fun fetchTodos() {
        lifecycleScope.launch {
            viewModel.getAllTodos().safeCollect {
                when (it) {
                    is Resource.Loading -> {
                        binding.swipeRefreshLayout.isRefreshing = true
                    }

                    is Resource.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this@HomepageActivity, it.error?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Resource.Success -> {
                        it.onSuccess { todos ->
                            binding.swipeRefreshLayout.isRefreshing = false
                            loadTodos(todos ?: emptyList())
                        }
                    }
                }
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchTodos()
        }
    }

    private fun setupAddButton() {
        binding.btnAdd.setOnClickListener {
            showAddTodosDialog()
        }
    }

    private fun loadTodos(todos: List<Todos>) = with(binding) {
        rvTodos.setup(
            items = todos,
            bindingClass = ItemTodosBinding::inflate,
            bindHolder = { binding, item, position ->
                binding?.tvTitle?.text = item.title
                binding?.checkbox?.isChecked = item.completed

                if (binding?.checkbox?.isChecked == true) {
                    changeBackground(binding.root, R.drawable.cardview_teal)
                } else {
                    changeBackground(binding?.root, R.drawable.cardview)
                }

                binding?.root?.setOnClickListener {
                    val isChecked = binding.checkbox.isChecked
                    binding.checkbox.isChecked = !isChecked

                    if (binding.checkbox.isChecked) {
                        changeBackground(binding.root, R.drawable.cardview_teal)
                    } else {
                        changeBackground(binding.root, R.drawable.cardview)
                    }
                }
            },
            manager = gridLayoutManager
        )
    }

    private fun changeBackground(view: View?, drawable: Int) {
        view?.background = ContextCompat.getDrawable(this, drawable)
    }

    private fun showAddTodosDialog() {
        val addDialog = AlertDialog.Builder(this)
        val inputText = EditText(this)

        addDialog.apply {
            setCancelable(false)
            setTitle("Tambah Todo")
            setView(inputText)

            val layout = LinearLayout(this@HomepageActivity)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(inputText)
            setView(layout)

            setPositiveButton("Simpan", DialogInterface.OnClickListener { dialog, which ->
                lifecycleScope.launch {
                    val name = inputText.text.toString()
                    val todos = Todos(
                        id = 99,
                        userId = 155,
                        title = name,
                        completed = false
                    )

                    viewModel.addTodos(todos)
                    fetchTodos()
                    dialog.cancel()
                }
            })

            setNegativeButton("Batal", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        }

        addDialog.show()
    }
}