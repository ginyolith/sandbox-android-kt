package com.ginyolith.androidsandbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.airbnb.epoxy.EpoxyController
import java.util.zip.Inflater

class HogeEpoxyController(private val fm: FragmentManager,
                          private val inflater: LayoutInflater) : EpoxyController() {
    override fun buildModels() {
        (0..30)
            .forEach {
                if (it == 4) {
                    ItemViewPagerEpoxyModel_(fm, inflater)
                        .id(it)
                        .addTo(this)
                } else {
                    itemTextView { id(it) }
                }
            }
    }
}