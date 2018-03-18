package com.rabtman.acgcomic.mvp.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R

/**
 * @author Rabtman
 */
class ComicMenuAdapter(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.acgcomic_item_menu_option, data) {

    private var checkItemPos: Int = 0;

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_option_name, item)
        if (checkItemPos == helper?.adapterPosition) {
            helper.setTextColor(R.id.tv_option_name, ContextCompat.getColor(mContext, R.color.colorPrimaryDark))
                    .setBackgroundRes(R.id.tv_option_name, R.drawable.acgcomic_shape_menu_option_selected)
        } else {
            helper?.setTextColor(R.id.tv_option_name, ContextCompat.getColor(mContext, R.color.grey500))
                    ?.setBackgroundRes(R.id.tv_option_name, R.drawable.acgcomic_shape_menu_option_normal)
        }
    }

    fun setCheckItem(pos: Int) {
        checkItemPos = pos
        notifyDataSetChanged()
    }

}