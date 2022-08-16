package com.example.asteroid_radar_app.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.asteroid_radar_app.Asteroid
import com.example.asteroid_radar_app.R
import com.example.asteroid_radar_app.database.AsteroidDatabase
import com.example.asteroid_radar_app.databinding.FragmentMainBinding
import com.example.asteroid_radar_app.repository.AsteroidRepo


class MainFragment : Fragment() {
private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val mainViewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this ,mainViewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val adapter = AsteroidListAdapter(AsteroidListListener { asteroid ->
            viewModel.onNavigateDetail(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter
        viewModel.asteroid.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Log.i("MainFragment", "${it.size.toString()}")
            }
        })


        viewModel.selectedAsteroidNavigation.observe(viewLifecycleOwner, Observer {
            if (it != null){
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(selectedAsteroid = it))
                viewModel.doneNavigation()
            }

        })
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.show_today_menu->{
                viewModel.showToday()
                true
            }
            R.id.show_week_menu->{
                viewModel.showWeek()
                true
            }
            R.id.show_saved_menu->{
                viewModel.showSaved()
                true
            }
            else ->{
                viewModel.showToday()
                true
            }

        }
    }

}