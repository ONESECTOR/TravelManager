package com.sector.travelmanager.fragments.attractions

import androidx.recyclerview.widget.DiffUtil
import com.sector.travelmanager.`object`.Attraction

class AttractionsDiffUtil(
    private val oldList: List<Attraction>,
    private val newList: List<Attraction>
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
                && oldList[oldItemPosition].image == newList[newItemPosition].image
    }
}