package com.randomanimals.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.randomanimals.MainActivity
import com.randomanimals.R
import com.randomanimals.data.api.ApiService
import com.randomanimals.data.api.RetrofitService
import com.randomanimals.data.model.Animal
import com.randomanimals.databinding.FragmentHomeBinding
import com.randomanimals.utils.NetworkUtils
import com.randomanimals.utils.Result

class HomeFragment : Fragment() {

    companion object {
        private val TAG = HomeFragment::class.java.name
    }

    private lateinit var binding: FragmentHomeBinding
    private var viewModel: AnimalsViewModel? = null
    private var adapter: AnimalsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        initViewModels()
        initObservers()
    }

    private fun initBindings() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvAnimalsList.run {
            this.layoutManager = layoutManager
            addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadAnimalsList()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initViewModels() {
        if (null == viewModel) {
            viewModel = ViewModelProvider(this@HomeFragment,
                ViewModelFactory(RetrofitService.createService(ApiService::class.java))).get(
                AnimalsViewModel::class.java)
            loadAnimalsList()
        }
    }

    private fun initObservers() {
        viewModel?.getAnimalsLiveData()?.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    renderList(result.data)
                    binding.homeAnimalsContainer.visibility = View.GONE
                    binding.rvAnimalsList.visibility = View.VISIBLE
                }
                is Result.InProgress -> {
                    binding.homeAnimalsContainer.visibility = View.VISIBLE
                    //binding.rvAnimalsList.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.homeAnimalsContainer.visibility = View.GONE
                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun loadAnimalsList() {
        if (NetworkUtils.isNetworkAvailable(requireActivity()))
            viewModel?.loadAnimalsData()
        else
            showSnackBarMessage(getString(R.string.no_internet_msg))
    }

    private fun renderList(animals: ArrayList<Animal>) {
        if (animals.isNotEmpty()) {
            if (adapter == null) {
                adapter = AnimalsAdapter(animals)
                binding.rvAnimalsList.adapter = adapter
                adapter!!.setListItemClickListener(object : AnimalsAdapter.ListItemClickListener {
                    override fun onItemClick(animal: Animal) {
                        Toast.makeText(activity, animal.name, Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                adapter?.setAnimalList(animals)
            }
        } else {
            showSnackBarMessage(getString(R.string.no_data_msg))
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        (activity as MainActivity).supportActionBar?.show()
        super.onResume()
    }
}
