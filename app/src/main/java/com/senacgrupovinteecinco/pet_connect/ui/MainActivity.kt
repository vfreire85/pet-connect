package com.senacgrupovinteecinco.pet_connect.ui


import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senacgrupovinteecinco.pet_connect.R
import com.senacgrupovinteecinco.pet_connect.model.PetsAdapter
import com.senacgrupovinteecinco.pet_connect.ui.viewmodel.PetViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: PetViewModel by viewModels()

    private lateinit var petsAdapter: PetsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Substitua pelo seu layout

        // Inicializa views
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        petsAdapter = PetsAdapter() // Use o construtor adequado
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = petsAdapter
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pets.collect { pets ->
                    petsAdapter.submitList(pets)
                }
            }
        }

        // Se for LiveData:
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}