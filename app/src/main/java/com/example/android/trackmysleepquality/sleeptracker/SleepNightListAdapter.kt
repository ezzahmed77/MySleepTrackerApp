
package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding


class SleepNightListAdapter(val clickListener: SleepNightListener) : ListAdapter<SleepNight, SleepNightListAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item!!)
    }


    class ViewHolder(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){
        val res = itemView.context.resources

        // Bind Function After converting into extension function
        fun bind(clickListener: SleepNightListener, item: SleepNight) {
            // After Using BindAdapters
            binding.sleepNight = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        // We want the ViewHolder to be responsible for Creating the Views and Making the Logic To bind them
        // If we have more views we can simply added them in this companion object to inflate more views
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding : ListItemSleepNightBinding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

// This class is used with listAdapter to calculate the difference when adding or removing items
// And for some animations
class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem.nightId == newItem.nightId
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        return oldItem == newItem
    }

}

// For Implementing ClickListener
class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
    fun onClick(night: SleepNight) = clickListener(night.nightId)
}