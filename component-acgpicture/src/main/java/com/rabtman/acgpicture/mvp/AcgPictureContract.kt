package com.rabtman.acgpicture.mvp

import com.rabtman.acgpicture.mvp.model.entity.*
import com.rabtman.common.base.mvp.IModel
import com.rabtman.common.base.mvp.IView
import io.reactivex.Flowable

/**
 * @author Rabtman
 * acg图片模块所有契约类
 */

interface AcgPictureMainContract {

    interface View : IView {

        fun showPictureType(types: List<AcgPictureType>)
    }

    interface Model : IModel {

        fun getAcgPictureType(): Flowable<List<AcgPictureType>>
    }
}

interface AcgPictureContract {

    interface View : IView {

        fun showPictures(picItems: List<AcgPictureItem>)

        fun showMorePictures(picItems: List<AcgPictureItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {

        fun getAcgPictures(pageNo: Int, type: String): Flowable<List<AcgPictureItem>>
    }
}

interface AnimatePictureContract {

    interface View : IView {

        fun showAnimatePictures(animatePictureItems: List<AnimatePictureItem>?)

        fun showMoreAnimatePictures(animatePictureItems: List<AnimatePictureItem>?, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {

        fun getAnimatePictures(pageIndex: Int): Flowable<AnimatePicturePage>
    }
}

interface APictureContract {

    interface View : IView {

        fun showPictures(picItems: List<APictureItem>)

        fun showMorePictures(picItems: List<APictureItem>, canLoadMore: Boolean)

        fun onLoadMoreFail()
    }

    interface Model : IModel {

        fun getAPictures(url: String): Flowable<APicturePage>
    }
}


