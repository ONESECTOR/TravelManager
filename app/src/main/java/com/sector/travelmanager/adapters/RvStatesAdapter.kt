package com.sector.travelmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.databinding.ItemStateBinding
import com.sector.travelmanager.fragments.states.StatesFragmentDirections
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_state.view.*

class RvStatesAdapter(private var stateList: List<State>): RecyclerView.Adapter<RvStatesAdapter.MyViewHolder>() {
    constructor(): this(emptyList())

    inner class MyViewHolder(val binding: ItemStateBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemStateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = stateList[position]

        with(holder) {
            with(stateList[position]) {
                binding.tvState.text = this.name
                binding.tvTerrain.text = this.terrain

                Picasso.with(itemView.context)
                    .load(this.image)
                    .fit()
                    .into(binding.ibState, object: Callback {
                        override fun onSuccess() {
                            binding.progressBar.visibility = View.GONE
                        }

                        override fun onError() {

                        }
                    })

                itemView.ibState.setOnClickListener {
                    val action = StatesFragmentDirections.actionListFragmentToAttractionsFragment(currentItem)
                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}