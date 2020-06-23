package com.rabtman.acgpicture.mvp.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.base.constant.IntentConstant
import com.rabtman.acgpicture.di.AcgPictureModule
import com.rabtman.acgpicture.di.DaggerAcgPictureComponent
import com.rabtman.acgpicture.mvp.AcgPictureContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.presenter.AcgPictureItemPresenter
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_ITEM)
class AcgPictureItemFragment : BaseFragment<AcgPictureItemPresenter>(), AcgPictureContract.View {

    @BindView(R2.id.rcv_animate_item)
    lateinit var rcvAcgItem: RecyclerView
    @BindView(R2.id.swipe_refresh_animate)
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mAdapter: AcgPictureItemAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAcgPictureComponent.builder()
                .appComponent(appComponent)
                .acgPictureModule(AcgPictureModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_fragment_animate
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: android.view.View?) {
        mPresenter.getAcgPictures()
    }

    override fun initData() {
        mPresenter.setAcgPictureType(arguments?.getString(IntentConstant.ACGPICTURE_TYPE, "moeimg")
                ?: "moeimg")
        mAdapter = AcgPictureItemAdapter(mAppComponent.imageLoader())
        mAdapter.setOnItemClickListener { adapter, view, position ->
            RouterUtils.getInstance()
                    .build(RouterConstants.PATH_PICTURE_ITEM_DETAIL)
                    .withParcelable(IntentConstant.ACGPICTURE_ITEM,
                            adapter.data[position] as AcgPictureItem)
                    .navigation()
        }

        val layoutManager = GridLayoutManager(context, 2)
        rcvAcgItem.layoutManager = layoutManager

        mAdapter.loadMoreModule.setOnLoadMoreListener { mPresenter.getMoreAcgPictures() }
        rcvAcgItem.adapter = mAdapter

        swipeRefresh.setOnRefreshListener { mPresenter.getAcgPictures() }
        setSwipeRefreshLayout(swipeRefresh)
        mPresenter.getAcgPictures()
    }

    override fun showPictures(picItems: List<AcgPictureItem>) {
        mAdapter.setList(picItems)
    }

    override fun showMorePictures(picItems: List<AcgPictureItem>, canLoadMore: Boolean) {
        if (!canLoadMore) {
            mAdapter.loadMoreModule.loadMoreEnd()
        } else {
            mAdapter.addData(picItems)
            mAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun onLoadMoreFail() {
        mAdapter.loadMoreModule.loadMoreFail()
    }

}