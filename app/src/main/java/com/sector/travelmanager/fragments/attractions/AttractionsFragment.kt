package com.sector.travelmanager.fragments.attractions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.Attraction
import com.sector.travelmanager.adapters.RvAttractionsAdapter
import com.sector.travelmanager.databinding.FragmentAttractionsBinding
import kotlinx.android.synthetic.main.fragment_attractions.*

class AttractionsFragment : Fragment() {
    private var _binding: FragmentAttractionsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RvAttractionsAdapter
    private var attractionsList = ArrayList<Attraction>()
    private lateinit var dbRefAttraction: DatabaseReference

    private val args by navArgs<AttractionsFragmentArgs>()
    private  var reference: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttractionsBinding.inflate(inflater, container, false)
        binding.rvAttractions.layoutManager = LinearLayoutManager(requireContext())

        initDatabase()
        getArgs(reference)
        getCurrentState()

        binding.btnBack.setOnClickListener {
            showListFragment()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showListFragment() {
        findNavController().navigate(R.id.action_attractionsFragment_to_listFragment)
    }

    private fun getArgs(reference: String) {
        this.reference = args.currentState.name
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext())
    }

    private fun getCurrentState() {
        dbRefAttraction = FirebaseDatabase.getInstance().getReference("Attractions").child(reference)

        dbRefAttraction.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (attractionSnapshot in snapshot.children) {
                        val attraction = attractionSnapshot.getValue(Attraction::class.java)

                        attractionsList.add(attraction!!)
                    }

                    rvAdapter = RvAttractionsAdapter(attractionsList)
                    binding.rvAttractions.adapter = rvAdapter

                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}