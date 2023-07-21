package com.example.cryptrox


import android.content.Context
import java.text.NumberFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptrox.callback.FilterListener
import com.example.cryptrox.databinding.ItemCryptoLayoutBinding
import com.example.cryptrox.model.Data
import com.example.cryptrox.util.Constants
import com.example.cryptrox.util.Constants.BASE_URL_IMAGE
import java.text.DecimalFormat
import java.util.*


class CryptoAdapter(private var dataList:List<Data>,
                    private val itemClick : ItemClick,
                    private val filterListener: FilterListener):RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private var context: Context? = null
    private var filteredList: List<Data> = dataList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {

        context=parent.context
        val view = ItemCryptoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(view)
    }


    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val currentItem = filteredList[holder.absoluteAdapterPosition]



        //setViews
        Glide.with(context!!).load(BASE_URL_IMAGE+currentItem.id + ".png").into(holder.binding.ivCoinImg)
        holder.binding.tvTile.text=currentItem.name


        val price: Double? = currentItem.quote?.uSD?.price

        val decimalFormat = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
        decimalFormat.applyPattern("#,##0.0000000")
        val formattedPrice = decimalFormat.format(price)

        holder.binding.tvPrice.text= "$$formattedPrice"

        holder.binding.tvCmcRank.text="Cmc Rank"+currentItem.cmcRank.toString()

        val date:String=Constants.formatTimestamp(currentItem.lastUpdated!!)
        holder.binding.tvLastUpdated.text="Updated at $date"


        holder.itemView.setOnClickListener {
            itemClick.onCellClickListener(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun sortByName() {
        filteredList = filteredList.sortedBy { it.name }
        notifyDataSetChanged()
    }

    fun sortByPrice(){
        filteredList=filteredList.sortedBy { it.quote?.uSD?.price }
        notifyDataSetChanged()
    }

    fun filter(query: String){
        filteredList = if (query.isNotEmpty()) {
            dataList.filter { data ->
                data.name!!.contains(query, ignoreCase = true)
            }
        } else {
            dataList
        }
        notifyDataSetChanged()
        filterListener.onFilterResult(filteredList.isEmpty())
    }

    inner class CryptoViewHolder(val binding: ItemCryptoLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}