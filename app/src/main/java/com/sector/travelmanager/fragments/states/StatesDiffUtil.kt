package com.sector.travelmanager.fragments.states

import androidx.recyclerview.widget.DiffUtil
import com.sector.travelmanager.`object`.State

class StatesDiffUtil(
    private val oldList: List<State>,
    private val newList: List<State>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
                && oldList[oldItemPosition].terrain == newList[newItemPosition].terrain
                && oldList[oldItemPosition].image == newList[newItemPosition].image
    }
}