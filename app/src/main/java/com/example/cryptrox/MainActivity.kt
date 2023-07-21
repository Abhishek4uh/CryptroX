package com.example.cryptrox


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptrox.callback.FilterListener
import com.example.cryptrox.databinding.ActivityMainBinding
import com.example.cryptrox.model.Data
import com.example.cryptrox.network.ApiService
import com.example.cryptrox.network.RetrofitHelper
import com.example.cryptrox.repository.CryptoRepository
import com.example.cryptrox.ui.AboutFragment
import com.example.cryptrox.ui.DetailsInfoBottomsheet
import com.example.cryptrox.viewmodel.CryptoViewModel
import com.example.cryptrox.viewmodel.CryptoViewModelFactory


class MainActivity : AppCompatActivity(),ItemClick,FilterListener {

    private lateinit var viewModel: CryptoViewModel
    private lateinit var cryptoAdapter: CryptoAdapter
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainFunction()
    }

    override fun onStart() {
        super.onStart()
        mainFunction()
    }

    override fun onResume() {
        super.onResume()
        mainFunction()
    }

    private fun mainFunction() {
        if (isNetworkConnected(this)) {
            binding.layoutNoInternet.visibility = View.GONE
            initRecyclerview()
            initViewmodelFactory()
            initClickListner()
            initObserver()
        } else {
            binding.layoutNoInternet.visibility = View.VISIBLE
            Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show()
        }
    }


    private fun initObserver() {
            viewModel.cryptoData.observe(this, Observer {
                cryptoAdapter= CryptoAdapter(it.data as List<Data>,this,this)
                binding.rvList.adapter=cryptoAdapter
                binding.rvList.visibility=View.VISIBLE
                binding.swipeToRefresh.isRefreshing=false
                binding.filledTextField.visibility=View.VISIBLE
            })
            viewModel.progress.observe(this, Observer {
                binding.progressBar.visibility=View.GONE
            })
    }

    private fun initClickListner() {
        binding.swipeToRefresh.setOnRefreshListener {
            initViewmodelFactory()
            initObserver()
            binding.editText.text?.clear()
        }
        binding.editText.doAfterTextChanged{
            val query = binding.editText.text.toString()
            cryptoAdapter.filter(query)
        }
    }

    private fun initViewmodelFactory() {
        val quoteServices= RetrofitHelper.getInstance().create(ApiService::class.java)
        val repository= CryptoRepository(quoteServices)
        viewModel = ViewModelProvider(this,CryptoViewModelFactory(repository))[CryptoViewModel::class.java]
    }

    private fun initRecyclerview() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager=layoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCellClickListener(myData: Data) {
        val detailsInfoBottomsheet = DetailsInfoBottomsheet.newInstance(myData)
        detailsInfoBottomsheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog_frament_theme)
        detailsInfoBottomsheet.showNow(supportFragmentManager, DetailsInfoBottomsheet::class.java.name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_res, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.action1 ->{
                //sort by Price...
                cryptoAdapter.sortByPrice()
            }

            R.id.action2 ->{
                //sort by Name...
                cryptoAdapter.sortByName()
            }

            R.id.action3->{
                //Open About Fragment
                binding.frameLayout.visibility=View.VISIBLE
                val fragmentManager: FragmentManager = supportFragmentManager
                val fragmentTag = "AboutFragment"
                val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)
                if (existingFragment != null) {
                    // Fragment is already added, no need to add it again
                } else {
                    val mFragment: Fragment = AboutFragment()
                    val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                    transaction.replace(binding.frameLayout.id, mFragment, fragmentTag)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                binding.layoutNoInternet.visibility=View.GONE
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun onRetryButtonClick(view: View) {
        mainFunction()
    }

    override fun onFilterResult(isEmpty: Boolean) {
        if(isEmpty){
            binding.clNoItem.visibility=View.VISIBLE
            binding.rvList.visibility=View.GONE
        }
        else{
            binding.clNoItem.visibility=View.GONE
            binding.rvList.visibility=View.VISIBLE
        }
    }
}
