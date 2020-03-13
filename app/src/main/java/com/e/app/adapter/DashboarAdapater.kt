package com.e.app.adapter

import com.e.app.R
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.app.databinding.ItemDashboardBinding
import com.e.app.model.TitleModel
import com.e.app.ui.dashboard.DashboardNavigator
import com.e.app.ui.titleboard.TitleBoardNavigator


class DashboarAdapater(val context: Context,  var mListener: TitleBoardNavigator,var titleList: List<TitleModel>) : RecyclerView.Adapter<DashboarAdapater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val profileSelectBinding = DataBindingUtil.inflate<ItemDashboardBinding>(
            LayoutInflater.from(parent.context), R.layout.item_dashboard, parent, false
        )
        return ViewHolder(profileSelectBinding)
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

    inner class ViewHolder internal constructor(private val itemDashboardBinding: ItemDashboardBinding) : RecyclerView.ViewHolder(itemDashboardBinding.root) {

        fun bind(position: Int) {

            itemDashboardBinding.txtTitle.text=titleList[position].name
            var colorString="#D81B60"
            Log.e("Colocr",""+titleList[position].color)
            itemDashboardBinding.txtTitle.setTextColor(Color.parseColor(titleList[position].color.toString()))
            itemDashboardBinding.executePendingBindings()
            itemDashboardBinding.layoutTitle.setOnClickListener {
                mListener.onItemClick(titleList[position])
              //  itemDashboardBinding.viewModel?.onItemClick(titleList[position])

            }
        }

    }
}