package com.rabtman.business.base

import com.rabtman.business.R
import com.rabtman.common.base.SimpleFragment

/**
 * @author Rabtman
 */
class NullFragment : SimpleFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_null
    }

    override fun initData() {}
}