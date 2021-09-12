package com.sector.travelmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.Attraction
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.fragments.attractions.AttractionsFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_attraction.view.*

class RvAttractionsAdapter(private var attractionsList: List<Attraction>): RecyclerView.Adapter<RvAttractionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_attraction, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = attractionsList[position]

        with(holder) {
            with(attractionsList[position]) {
                itemView.tvNameOfAttraction.text = this.name

                Picasso.with(itemView.context)
                    .load(this.image)
                    .fit()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(itemView.ibAttraction, object: Callback {
                        override fun onSuccess() {
                            itemView.progressBar.visibility = View.GONE
                        }

                        override fun onError() {

                        }
                    })

                itemView.ibAttraction.setOnClickListener {
                    val extras = FragmentNavigatorExtras(
                        itemView.ibAttraction to currentItem.image,
                        itemView.tvNameOfAttraction to currentItem.name)

                    val action = AttractionsFragmentDirections.actionAttractionsFragmentToDescriptionFragment(currentItem)
                    itemView.findNavController().navigate(action, extras)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return attractionsList.size
    }
}