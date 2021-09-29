package com.sector.travelmanager.fragments.states

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sector.travelmanager.`object`.State
import com.sector.travelmanager.databinding.ItemStateBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_state.view.*

class StatesAdapter: ListAdapter<State, StatesAdapter.MyViewHolder>(ItemComparator()) {

    class MyViewHolder(private val binding: ItemStateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(state: State) = with(binding) {
            tvState.text = state.name
            tvTerrain.text = state.terrain
            Picasso.with(itemView.context)
                .load(state.image)
                .fit()
                .into(ibState, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError() {

                    }
                })
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStateBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    class ItemComparator: DiffUtil.ItemCallback<State>() {
        override fun areItemsTheSame(oldItem: State, newItem: State): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: State, newItem: State): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            bind(getItem(position))

            itemView.ibState.setOnClickListener {
                val action = StatesFragmentDirections.actionListFragmentToAttractionsFragment(getItem(position))
                itemView.findNavController().navigate(action)
            }
        }
    }
}