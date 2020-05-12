package com.easyinc.tasking.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyinc.tasking.R
import com.easyinc.tasking.common.base.BaseFragment
import com.easyinc.tasking.common.extentions.androidLazy
import com.easyinc.tasking.common.extentions.replaceAnimatedFragment
import com.easyinc.tasking.common.extentions.snacks
import com.easyinc.tasking.common.utils.Resource
import com.easyinc.tasking.common.viewmodel.ViewModelFactory
import com.easyinc.tasking.common.viewmodel.getViewModel
import com.easyinc.tasking.data.model.DayModel
import com.easyinc.tasking.presentation.task.TasksFragment
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*
import javax.inject.Inject
import javax.inject.Named

class HomeFragment : BaseFragment() {

    @Inject
    @field:Named("date")
    lateinit var date: String

    @field:Named("day_id")
    @JvmField
    @Inject
    var dayId: Long = 0

    @Inject
    lateinit var homeAdapter: HomeAdapter

    lateinit var mView: View

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private val homeViewModel by androidLazy {
        getViewModel<HomeViewModel>(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_main, container, false)

        mView.main_recycler.adapter = homeAdapter
        mView.main_recycler.setHasFixedSize(true)
        mView.main_recycler.layoutManager = LinearLayoutManager(context)


        homeViewModel.getDays()

        homeViewModel.observeDaysList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> homeAdapter.setList(it.data)
                is Resource.Error -> mView.snacks(it.message!!)
            }
        })

        homeViewModel.getProgressPercent().observe(viewLifecycleOwner, Observer {
            mView.progress_percent.text = it
        })


        mView.add_day.setOnClickListener {
            homeViewModel.insertDay(DayModel(date, id = dayId))
        }

        homeAdapter.clickListener = {
            val tasksFragment = TasksFragment.newInstance(it.id)
            replaceAnimatedFragment(R.id.container, tasksFragment, true)
        }

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewOperation(view)
    }

    fun searchViewOperation(sView: View) {

        sView.search_day.setOnClickListener {
            sView.search_day_card.visibility = View.VISIBLE
        }

        sView.search_day_key_word.setOnCloseListener {
            sView.search_day_card.visibility = View.GONE
            return@setOnCloseListener true
        }

        sView.search_day_key_word.setOnQueryTextFocusChangeListener { _, b ->
            if (b)
                sView.search_day_card.visibility = View.VISIBLE
            else
                sView.search_day_card.visibility = View.GONE
        }

        sView.search_day_key_word.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                homeAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeAdapter.filter.filter(newText)
                return false
            }
        })
    }

}
