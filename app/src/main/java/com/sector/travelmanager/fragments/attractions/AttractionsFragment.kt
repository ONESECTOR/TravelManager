package com.sector.travelmanager.fragments.attractions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.Attraction
import com.sector.travelmanager.databinding.FragmentAttractionsBinding
import java.util.*
import kotlin.collections.ArrayList

class AttractionsFragment : Fragment() {
    private var _binding: FragmentAttractionsBinding? = null
    private val binding get() = _binding!!

    private var dbRefAttraction: DatabaseReference? = null

    private val args by navArgs<AttractionsFragmentArgs>()
    private var reference: String? = null

    private lateinit var adapter: AttractionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttractionsBinding.inflate(inflater, container, false)

        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        initDatabase()
        getArgs()
        readFromDatabase()

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        adapter = AttractionsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun getArgs() {
        reference = args.currentState.name
    }

    private fun initDatabase() {
        FirebaseApp.initializeApp(requireContext())
    }

    private fun readFromDatabase() {
        when (Locale.getDefault().language) {
            "ru" -> {
                dbRefAttraction = FirebaseDatabase.getInstance()
                    .getReference("Attractions")
                    .child("ru")
                    .child(reference!!)
            }
            "en" -> {
                dbRefAttraction = FirebaseDatabase.getInstance()
                    .getReference("Attractions")
                    .child("en")
                    .child(reference!!)
            }
        }

        dbRefAttraction?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val list = ArrayList<Attraction>()

                    for (attractionSnapshot in snapshot.children) {
                        val attraction = attractionSnapshot.getValue(Attraction::class.java)

                        list.add(attraction!!)
                    }

                    adapter.submitList(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}