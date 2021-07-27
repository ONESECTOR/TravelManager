package com.sector.travelmanager.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sector.travelmanager.R
import com.sector.travelmanager.`object`.State
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_state_list.view.*

class RvStatesAdapter(private var stateList: List<State>): RecyclerView.Adapter<RvStatesAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_state_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(stateList[position]) {
                Picasso.with(itemView.context)
                    .load(this.image)
                    .into(itemView.ibState, object: Callback {
                        override fun onSuccess() {
                            holder.itemView.progressBar.setBackgroundColor(Color.BLACK)
                            holder.itemView.progressBar.visibility = View.GONE
                        }

                        override fun onError() {

                        }
                    })

                itemView.tvState.text = this.name
            }
        }
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}