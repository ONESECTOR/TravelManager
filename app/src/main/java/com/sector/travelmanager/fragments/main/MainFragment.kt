package com.sector.travelmanager.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.sector.travelmanager.R
import com.sector.travelmanager.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.ibMenu.setOnClickListener {
             binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_app -> Toast.makeText(context, "item clicked", Toast.LENGTH_SHORT).show()
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_firstFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}