package com.ginyolith.androidsandbox

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class HogeFragment : Fragment() {

    companion object {
        fun newInstance() = HogeFragment()
    }

    private lateinit var viewModel: HogeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hoge_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HogeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
