package com.example.asteroid_radar_app.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.asteroid_radar_app.R
import com.example.asteroid_radar_app.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater)
        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid
        binding.asteroid = asteroid
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }
        return binding.root
    }
    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }

}