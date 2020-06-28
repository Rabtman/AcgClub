package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleRecommend
import com.rabtman.common.utils.DimenUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Rabtman
 */
class ScheduleRecommandAdapter(data: MutableList<ScheduleRecommend>?) : BaseQuickAdapter<ScheduleRecommend, BaseViewHolder>(R.layout.acgschedule_item_schedule_recommand, data) {

    override fun convert(helper: BaseViewHolder, item: ScheduleRecommend) {
        helper.setText(R.id.tv_schedule_recommand, item.name)
        helper.getView<ImageView>(R.id.img_schedule_recommand)
                .loadImage(context, item.getImgUrl(),
                        CropTransformation(0, 0), RoundedCornersTransformation(DimenUtils.dpToPx(context, 20f), 0))
    }

}