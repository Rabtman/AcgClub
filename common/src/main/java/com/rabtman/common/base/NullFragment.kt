package com.rabtman.common.base

import com.rabtman.common.R

/**
 * @author Rabtman
 */
class NullFragment : SimpleFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_null
    }

    override fun initData() {}
}