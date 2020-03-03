package com.e.app.adapter

import com.e.app.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.app.databinding.ItemDashboardBinding
import com.e.app.ui.dashboard.DashboardNavigator
import com.e.app.ui.titleboard.TitleBoardNavigator


class DashboarAdapater(val context: Context,  var mListener: TitleBoardNavigator) : RecyclerView.Adapter<DashboarAdapater.ViewHolder>() {

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
        return 5
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder internal constructor(private val itemDashboardBinding: ItemDashboardBinding) : RecyclerView.ViewHolder(itemDashboardBinding.root) {

        fun bind(position: Int) {

            itemDashboardBinding.executePendingBindings()
        }

    }
}