package com.e.app.adapter

import com.e.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.app.databinding.ItemJoincontestBinding
import com.e.app.model.ContestModel
import com.e.app.ui.contest.JoinContestNavigator


class JoinContestAdapater(val context: Context, var mListener: JoinContestNavigator, var joinContestList: List<ContestModel>,var amountPay:Int) : RecyclerView.Adapter<JoinContestAdapater.ViewHolder>() {

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
        return joinContestList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder internal constructor(private val itemJoincontestBinding: ItemJoincontestBinding) : RecyclerView.ViewHolder(itemJoincontestBinding.root) {

        fun bind(position: Int) {

            itemJoincontestBinding.txtDate.text= joinContestList[position].dateTime
            itemJoincontestBinding.txtMapName.text= joinContestList[position].map
            itemJoincontestBinding.txtTypeName.text= joinContestList[position].type
            itemJoincontestBinding.txtWinFee.text= "₹"+joinContestList[position].winAmount
            itemJoincontestBinding.txtEntryFee.text= "₹"+joinContestList[position].price

            if(amountPay==1)
            {
                itemJoincontestBinding.layoutRoomId.visibility= View.VISIBLE
                itemJoincontestBinding.btnJoinContest.visibility= View.GONE
                itemJoincontestBinding.btnJoinContest.isEnabled=false
                itemJoincontestBinding.btnJoinContest.isClickable=false
                itemJoincontestBinding.txtRoomPassword.text= joinContestList[position].roomPassword
                itemJoincontestBinding.txtRoomId.text= joinContestList[position].roomID
            }

            itemJoincontestBinding.btnJoinContest.setOnClickListener {
                mListener.onItemClick(joinContestList[position])
            }
        }

    }

}