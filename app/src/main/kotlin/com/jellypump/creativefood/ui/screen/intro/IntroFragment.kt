package com.jellypump.creativefood.ui.screen.intro

import com.jellypump.creativefood.R
import com.jellypump.creativefood.ui.core.BaseFragment
import kotlinx.android.synthetic.main.intro_fragment.*


class IntroFragment : BaseFragment() {

    override val layoutId: Int = R.layout.intro_fragment

    override fun initUi() {
        intro_start_button.setOnClickListener {
            navController.navigate(IntroFragmentDirections.actionHome())
        }
    }
}
