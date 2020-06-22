package com.rabtman.acgcomic.mvp.ui.adapter

import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R

/**
 * @author Rabtman
 */
class ComicMenuAdapter(data: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.acgcomic_item_menu_option, data) {

    private var checkItemPos: Int = 0;

    override fun convert(helper: BaseViewHolder, item: String) {
        helper?.setText(R.id.tv_option_name, item)
        if (checkItemPos == helper?.adapterPosition) {
            helper.setTextColor(R.id.tv_option_name, ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    .setBackgroundResource(R.id.tv_option_name, R.drawable.acgcomic_shape_menu_option_selected)
        } else {
            helper?.setTextColor(R.id.tv_option_name, ContextCompat.getColor(context, R.color.grey500))
                    ?.setBackgroundResource(R.id.tv_option_name, R.drawable.acgcomic_shape_menu_option_normal)
        }
    }

    fun setCheckItem(pos: Int) {
        checkItemPos = pos
        notifyDataSetChanged()
    }

}