package dev.timatifey.stockaggregator.fragments.main.list

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.timatifey.stockaggregator.fragments.main.list.favourite.FavouriteFragment
import dev.timatifey.stockaggregator.fragments.main.list.stocks.StocksFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> StocksFragment()
            else -> FavouriteFragment()
        }
    }
}