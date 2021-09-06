package com.sector.travelmanager.description

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sector.travelmanager.databinding.FragmentDescriptionBinding
import com.squareup.picasso.Picasso

class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding

    private val args by navArgs<DescriptionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(layoutInflater, container, false)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move)

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs()
    }

    private fun getArgs() {
        binding.apply {
            tvAttraction.text = args.currentAttraction.name
            Picasso.with(requireContext())
                .load(args.currentAttraction.image)
                .into(ivAttraction)
        }
    }
}