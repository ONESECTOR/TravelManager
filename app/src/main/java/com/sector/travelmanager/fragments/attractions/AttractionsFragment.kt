package com.sector.travelmanager.fragments.attractions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.sector.travelmanager.fragments.attractions.AttractionsFragmentDirections
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

    private lateinit var recyclerViewAdapter: RvAttractionsAdapter

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    val attractionsListTemp = ArrayList<Attraction>()
                    for (attractionSnapshot in snapshot.children) {
                        val attraction = attractionSnapshot.getValue(Attraction::class.java)

                        attractionsListTemp.add(attraction!!)
                    }

                    attractionsList = attractionsListTemp //It is made so that the elements in RecyclerView are not duplicated

                    rvAdapter = RvAttractionsAdapter(attractionsList)
                    /*rvAdapter.listener =  object : RvAttractionsAdapter.ItemClickListener {
                        override fun onItemClickListener(item: Attraction, imageView: ImageView) {
                            val extras = FragmentNavigatorExtras(imageView to item.image)

                            val action = AttractionsFragmentDirections.actionAttractionsFragmentToDescriptionFragment()
                        }
                    }*/
                    binding.rvAttractions.adapter = rvAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}