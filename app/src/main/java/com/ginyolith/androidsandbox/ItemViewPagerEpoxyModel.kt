package com.ginyolith.androidsandbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyModelClass
import com.ginyolith.androidsandbox.databinding.ItemViewpagerBinding
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


@EpoxyModelClass(layout = R.layout.item_viewpager)
abstract class ItemViewPagerEpoxyModel(private val supportFragmentManager: FragmentManager,
                                       private val inflater: LayoutInflater
) : DataBindingEpoxyModel() {

    private val horizontalLayoutManager = LinearLayoutManager(inflater.context, RecyclerView.HORIZONTAL, false)
    private val recyclerAdapter = RecyclerAdapter(inflater).apply { notifyDataSetChanged() }
    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        require(binding is ItemViewpagerBinding)

//        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
//        binding.viewPager.adapter = LayoutViewPagerAdapter(inflater)
        binding.tabLayout.run {
            addTab(newTab().also { it.text = "Hoge" })
            addTab(newTab().also { it.text = "Fuga" })
        }
        binding.recyclerView.run {
            adapter = recyclerAdapter
            layoutManager = horizontalLayoutManager
            PagerSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        binding.tabLayout.getTabAt(horizontalLayoutManager
                            .findLastCompletelyVisibleItemPosition())
                            ?.select()

                        recyclerAdapter.notifyDataSetChanged()
//                        binding.recyclerView.requestLayout()
                    }
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    class RecyclerAdapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return if (viewType == 0) {
                inflater.inflate(R.layout.hoge_fragment, parent, false)
            } else {
                inflater.inflate(R.layout.fuga_fragment, parent, false)
            }.let { ViewHolder(it) }
        }

        override fun getItemCount(): Int = 2

        override fun getItemViewType(position: Int): Int {
            return position
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {}
    }

    class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment =
                if (position == 0) {
                    HogeFragment.newInstance()
                } else {
                    FugaFragment.newInstance()
                }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? =
                if (position == 0) {
                    "Hoge"
                } else {
                    "Fuga"
                }
    }

    class LayoutViewPagerAdapter(private val inflater: LayoutInflater) : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object` as TextView
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return if (position == 0) {
                inflater.inflate(R.layout.hoge_fragment, container, false).also {
                    it.tag = "hoge"
                }
            } else {
                inflater.inflate(R.layout.fuga_fragment, container, false).also {
                    it.tag = "fuga"
                }
            }
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            (if (position == 0) "hoge" else "fuga")
                .let { container.findViewWithTag<View>(it) }
                .let { container.removeView(it) }
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence? =
                if (position == 0) {
                    "Hoge"
                } else {
                    "Fuga"
                }
    }
}