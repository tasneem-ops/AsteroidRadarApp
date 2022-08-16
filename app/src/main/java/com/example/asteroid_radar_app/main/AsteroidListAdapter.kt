package com.example.asteroid_radar_app.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroid_radar_app.Asteroid
import com.example.asteroid_radar_app.databinding.AsteroidsListBinding

class AsteroidListAdapter(val clickListener : AsteroidListListener) : ListAdapter<Asteroid, AsteroidListAdapter.AsteroidViewHolder>(DiffCallback){
    companion object DiffCallback: DiffUtil.ItemCallback <Asteroid>(){
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class AsteroidViewHolder(private var binding : AsteroidsListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidListListener, asteroid: Asteroid){
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidsListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid =  getItem(position)
        holder.bind(clickListener,asteroid)
    }
}

class AsteroidListListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)

}