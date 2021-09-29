package net.simplifiedcoding.multiviewlist.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import net.simplifiedcoding.multiviewlist.data.network.Resource
import net.simplifiedcoding.multiviewlist.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val homeRecyclerViewAdapter = HomeRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = homeRecyclerViewAdapter
        }

        homeRecyclerViewAdapter.itemClickListener = {view, item, position ->
            val msg = when(item){
                is HomeRecyclerViewItem.Director -> "Diretor ${item.name} clicado"
                is HomeRecyclerViewItem.Movie -> "Filme ${item.title} clicado"
                is HomeRecyclerViewItem.Title -> "Titulo     ${item.title} clicado"
            }
            snackbar(msg)
        }

        viewModel.homeListItemsLiveData.observe(this) { result ->
            when (result) {
                is Resource.Failure -> {
                    binding.progressBar.hide()
                }
                Resource.Loading -> binding.progressBar.show()
                is Resource.Success -> {
                    binding.progressBar.hide()
                    homeRecyclerViewAdapter.items = result.value
                }
            }
        }
    }
}