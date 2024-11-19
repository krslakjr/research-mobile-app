package ba.etf.rma22.projekat.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ba.etf.rma22.projekat.pagger


class ViewPagerAdapter(var punkten : MutableList<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(pozition: Int): Fragment {
        return punkten[pozition]
    }
    override fun getItemId(pozition: Int): Long {
        return punkten[pozition].hashCode().toLong()
    }
    override fun containsItem(punkt: Long): Boolean {
        return punkten.find { it.hashCode().toLong() == punkt } != null
    }
    override fun getItemCount(): Int {
        return punkten.size
    }
    fun refreshFragment(register: Int, fragmenten: Fragment) {
        punkten[register] = fragmenten
        notifyItemChanged(register)
    }
    fun refreshAll(fragmenten : List<Fragment>, pozition : Int) {
        punkten.removeAll(punkten)
        punkten.addAll(fragmenten)
        notifyDataSetChanged()
        pagger.currentItem = pozition
    }
    fun add(register: Int, fragmenten: Fragment) {
        punkten.add(register, fragmenten)
        notifyItemChanged(register)
    }


}