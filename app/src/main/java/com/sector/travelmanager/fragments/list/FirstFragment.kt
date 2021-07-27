package com.sector.travelmanager.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.adapters.RvStatesAdapter
import com.sector.travelmanager.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RvStatesAdapter
    private var statesList = ArrayList<State>()
    private var databaseReferenceStates: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.rvAttractions.layoutManager = LinearLayoutManager(requireContext())
        //binding.rvAttractions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        initDatabase()
        getStates()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getStates() {
        databaseReferenceStates?.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (stateSnapshot in snapshot.children) {
                        val state = stateSnapshot.getValue(State::class.java)

                        statesList.add(state!!)
                    }

                    rvAdapter = RvStatesAdapter(statesList)
                    binding.rvAttractions.adapter = rvAdapter

                    rvAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun initDatabase() {
        databaseReferenceStates = FirebaseDatabase.getInstance().getReference("States")
    }
}