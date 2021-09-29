package com.sector.travelmanager.fragments.attractions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sector.travelmanager.`object`.Attraction
import com.sector.travelmanager.databinding.ItemAttractionBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_attraction.view.*

class AttractionsAdapter: ListAdapter<Attraction, AttractionsAdapter.ViewHolder>(ItemComparator()) {

    class ViewHolder(private val binding: ItemAttractionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: Attraction) = with(binding) {
            tvNameOfAttraction.text = attraction.name
            Picasso.with(itemView.context)
                .load(attraction.image)
                .fit()
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .into(ibAttraction, object: Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError() {

                    }
                })
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAttractionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<Attraction>() {
        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))

            itemView.ibAttraction.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    itemView.ibAttraction to getItem(position).image,
                    itemView.tvNameOfAttraction to getItem(position).name)

                val action = AttractionsFragmentDirections.actionAttractionsFragmentToDetailFragment(getItem(position))
                itemView.findNavController().navigate(action, extras)
            }
        }
    }
}