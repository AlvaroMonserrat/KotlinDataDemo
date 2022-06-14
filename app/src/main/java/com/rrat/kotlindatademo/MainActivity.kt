package com.rrat.kotlindatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rrat.kotlindatademo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLiveData.setOnClickListener {
            viewModel.triggerLiveData()
        }

        binding.btnStateFlow.setOnClickListener {
            viewModel.triggerStateFlow()
        }

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch{
                viewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }

        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }

        subscribeToObservables()
    }

    private fun subscribeToObservables()
    {
        viewModel.liveData.observe(this){
            data-> binding.tvLiveData.text = data
        }

        lifecycleScope.launchWhenStarted {
            viewModel.sharedFlow.collectLatest {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

}