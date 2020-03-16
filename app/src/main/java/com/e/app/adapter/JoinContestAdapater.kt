package com.e.app.adapter

import com.e.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.app.databinding.ItemJoincontestBinding
import com.e.app.model.ContestModel
import com.e.app.ui.contest.JoinContestNavigator


class JoinContestAdapater(val context: Context, var mListener: JoinContestNavigator, var titleList: List<ContestModel>) : RecyclerView.Adapter<JoinContestAdapater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemJoincontestBinding = DataBindingUtil.inflate<ItemJoincontestBinding>(
            LayoutInflater.from(parent.context), R.layout.item_joincontest, parent, false
        )
        return ViewHolder(itemJoincontestBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(position)
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder internal constructor(private val itemJoincontestBinding: ItemJoincontestBinding) : RecyclerView.ViewHolder(itemJoincontestBinding.root) {

        fun bind(position: Int) {

            itemJoincontestBinding.txtDate.text= titleList[position].dateTime
            itemJoincontestBinding.txtMapName.text= titleList[position].map
            itemJoincontestBinding.txtTypeName.text= titleList[position].type
            itemJoincontestBinding.txtWinFee.text= titleList[position].winAmount
            itemJoincontestBinding.txtEntryFee.text= titleList[position].price

        }

    }
}