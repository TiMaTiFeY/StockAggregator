package dev.timatifey.stockaggregator.fragments.main.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.timatifey.stockaggregator.R

class ListFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private fun initViews(view: View) {
        viewPager = view.findViewById(R.id.fragment_list__view_pager)
        tabLayout = view.findViewById(R.id.fragment_list__tabs)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        initViews(view)
        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_name__stocks)
                1 -> getString(R.string.tab_name__favourite)
                else -> "Unknown"
            }
        }.attach()

        return view
    }
}