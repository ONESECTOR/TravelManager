package com.sector.travelmanager.fragments.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sector.travelmanager.R
import com.sector.travelmanager.databinding.FragmentAboutAppBinding

class AboutAppFragment : Fragment() {
    private var _binding: FragmentAboutAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutAppBinding.inflate(inflater, container, false)

        binding.btnIGotIt.setOnClickListener {
            navigateToListFragment()
        }

        return binding.root
    }

    private fun navigateToListFragment() {
        findNavController().navigate(R.id.action_aboutAppFragment_to_listFragment)
    }
}