package dev.timatifey.stockaggregator.fragments.main.list

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.timatifey.stockaggregator.R


class ListFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var selectedColor: ColorStateList
    private lateinit var unselectedColor: ColorStateList

    private fun View.initViews() {
        viewPager = findViewById(R.id.fragment_list__view_pager)
        tabLayout = findViewById(R.id.fragment_list__tabs)

        selectedColor = ContextCompat.getColorStateList(context, R.color.text_primary)!!
        unselectedColor = ContextCompat.getColorStateList(context, R.color.text_gray)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main__list, container, false)
        view.initViews()
        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val tabContainer = LayoutInflater.from(context)
                .inflate(R.layout.custom_tab_item, tabLayout, false) as ViewGroup?
            if (tabContainer != null) {
                val textView =
                    tabContainer.findViewById<AppCompatTextView>(R.id.custom_tab_item__tv)
                when (position) {
                    0 -> {
                        textView.text = getString(R.string.tab_name__stocks)
                        textView.makeSelectedStyle()
                    }
                    1 -> {
                        textView.text = getString(R.string.tab_name__favourite)
                        textView.makeUnselectedStyle()
                    }
                    else -> {
                    }
                }
                tab.customView = tabContainer
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            private fun TabLayout.Tab.getTextView(): AppCompatTextView? {
                val customView: View = customView ?: return null
                return customView.findViewById(R.id.custom_tab_item__tv)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val textView = tab?.getTextView() ?: return
                textView.makeSelectedStyle()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val textView = tab?.getTextView() ?: return
                textView.makeUnselectedStyle()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        return view
    }

    private fun AppCompatTextView.makeSelectedStyle() {
        textSize = 28f
        setTextColor(selectedColor)
        setPadding(0, 0, 0, 0)
    }

    private fun AppCompatTextView.makeUnselectedStyle() {
        textSize = 18f
        setTextColor(unselectedColor)
        setPadding(0, 0, 0, 6)
    }
}