package com.example.cryptrox.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cryptrox.R
import com.example.cryptrox.databinding.FragmentDetailsInfoBottomsheetBinding
import com.example.cryptrox.model.Data
import com.example.cryptrox.util.Constants
import com.example.cryptrox.util.Constants.BASE_URL_IMAGE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class DetailsInfoBottomsheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailsInfoBottomsheetBinding
    private lateinit var myObjectData:Data


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentDetailsInfoBottomsheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCanceledOnTouchOutside(true)
        Glide.with(this).load(R.raw.newgif).into(binding.ivNew)
        initClickListner()

        val gson = Gson()
        val dataJson = arguments?.getString("MY_DATA")
        val dataType = object : TypeToken<Data>() {}.type
        myObjectData = gson.fromJson(dataJson, dataType)

        setViews(myObjectData)

    }

    private fun initClickListner() {
        binding.ivCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(myData: Data) = DetailsInfoBottomsheet().apply {
            val gson = Gson()
            val dataJson = gson.toJson(myData)
            val bundle = Bundle()
            bundle.putString("MY_DATA", dataJson)
            this.arguments = bundle
        }
    }

    private fun setViews(dataToSet:Data){

        val imgUrl=BASE_URL_IMAGE+myObjectData.id+".png"
        Glide.with(requireContext()).load(imgUrl).into(binding.ivCoinImg)

        //Parent data
        binding.tvSymbol.text=myObjectData.symbol
        binding.tvName.text=myObjectData.name


        //Price
        val price: Double? = dataToSet.quote?.uSD?.price
        val decimalFormat = NumberFormat.getNumberInstance(Locale.US) as DecimalFormat
        decimalFormat.applyPattern("#,##0.0000000")
        val formattedPrice = decimalFormat.format(price)
        binding.tvPrice.text= "$$formattedPrice"


        //Update
        val update:String=Constants.formatTimestamp(dataToSet.lastUpdated!!)
        binding.tvUpdate.text="Updated at: $update"


        //MaxSupply
        val temp=myObjectData.maxSupply?.toInt()
        if (myObjectData.maxSupply!=null && temp!=0){
            binding.tvMaxSupVAl.text=myObjectData.maxSupply.toString()
        }
        else{
            binding.tvMaxSupVAl.text=R.string.not_available.toString()
        }

        //TotalSupply
        if (myObjectData.totalSupply!=null){
            val number = myObjectData.totalSupply
            val formatter = DecimalFormat("#.##")
            val formattedString = formatter.format(number)
            binding.tvTotalVal.text=formattedString
        }
        else{
            binding.tvTotalVal.text=R.string.not_available.toString()
        }

        //Market Entered
        if (myObjectData.dateAdded!=null){
            val date:String=Constants.formatTimeStampWithDateOnly(myObjectData.dateAdded!!)
            binding.tvOriginData.text="Added in Market: $date"
        }
        else{
            binding.tvOriginData.text=R.string.not_available.toString()
        }
    }

}