package com.easyinc.tasking.presentation.home

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.easyinc.tasking.R
import com.easyinc.tasking.common.extentions.inflate
import com.easyinc.tasking.data.model.DayModel
import kotlinx.android.synthetic.main.day_item.view.*
import java.util.*
import javax.inject.Inject

class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(),Filterable {

    private var daysList: List<DayModel>? = listOf()
    private var itemsFinal: List<DayModel>? = listOf()

    internal var clickListener: (DayModel) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeViewHolder(parent.inflate(R.layout.day_item))

    override fun getItemCount() = itemsFinal!!.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) = holder.bind(itemsFinal!![position],clickListener)

    fun setList(list: List<DayModel>?){
        daysList = list
        itemsFinal = daysList
        notifyDataSetChanged()
    }

 inner class HomeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
     fun bind(day: DayModel, clickListener: (DayModel) -> Unit){
         itemView.day_date.text = day.date
         itemView.percent.text = day.progressPercent
         itemView.setOnClickListener { clickListener(day) }
     }

 }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint : CharSequence?): FilterResults {
                val searchKeyWord = constraint.toString().toLowerCase(Locale.ROOT)
                val daysFilter: List<DayModel>?
                if (searchKeyWord.isEmpty()){
                    itemsFinal = daysList
                }else{
                    daysFilter = daysList?.filter { it.date.toLowerCase(Locale.ROOT).contains(searchKeyWord) }
                    itemsFinal = daysFilter
                }


                val filterResult = FilterResults()
                filterResult.values = itemsFinal

                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, result: FilterResults?) {
                     itemsFinal = (result?.values as List<DayModel>)

                notifyDataSetChanged()
            }
        }
    }

}