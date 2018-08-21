package com.rabtman.acgpicture.mvp.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.di.APictureModule
import com.rabtman.acgpicture.di.DaggerAPictureComponent
import com.rabtman.acgpicture.mvp.APictureContract
import com.rabtman.acgpicture.mvp.model.entity.APictureItem
import com.rabtman.acgpicture.mvp.presenter.APicturePresenter
import com.rabtman.acgpicture.mvp.ui.adapter.APictureItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent

/**
 * @author Rabtman
 */
class APictureFragment : BaseFragment<APicturePresenter>(), APictureContract.View {

    @BindView(R2.id.rcv_animate_item)
    lateinit var rcvAnimateItem: RecyclerView
    @BindView(R2.id.swipe_refresh_animate)
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mAdapter: APictureItemAdapter

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAPictureComponent.builder()
                .appComponent(appComponent)
                .aPictureModule(APictureModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_fragment_animate
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getAcgPicList()
    }

    override fun initData() {
        mAdapter = APictureItemAdapter(appComponent.imageLoader())
        mAdapter.setOnItemClickListener(OnItemClickListener { adapter, view, position ->
            /*val picInfo = adapter.data[position] as PicInfo
            val intent = Intent(context, APicDetailActivity::class.java)
            intent.putExtra(IntentConstant.APIC_DETAIL_TITLE, picInfo.getTitle())
            intent.putExtra(IntentConstant.APIC_DETAIL_URL, picInfo.getContentLink())
            startActivity(intent)*/
        })

        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        rcvAnimateItem.layoutManager = layoutManager
        mAdapter.setOnLoadMoreListener(RequestLoadMoreListener { mPresenter.getMoreAcgPicList() }, rcvAnimateItem)
        rcvAnimateItem.adapter = mAdapter

        swipeRefresh.setOnRefreshListener { mPresenter.getAcgPicList() }
        setSwipeRefreshLayout(swipeRefresh)
        mPresenter.getAcgPicList()
    }

    override fun showPictures(picItems: List<APictureItem>) {
        mAdapter.setNewData(picItems)
    }

    override fun showMorePictures(picItems: List<APictureItem>, canLoadMore: Boolean) {
        mAdapter.addData(picItems)
        mAdapter.loadMoreComplete()
        if (!canLoadMore) {
            mAdapter.loadMoreEnd()
        }
    }

    override fun showError(message: String) {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
        super.showError(message)
    }

    override fun onLoadMoreFail() {
        mAdapter.loadMoreFail()
    }
}
