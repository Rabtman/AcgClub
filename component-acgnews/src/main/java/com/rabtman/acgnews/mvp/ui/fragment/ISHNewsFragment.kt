package com.rabtman.acgnews.mvp.ui.fragment

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.rabtman.acgnews.R
import com.rabtman.acgnews.R2
import com.rabtman.acgnews.base.constant.IntentConstant
import com.rabtman.acgnews.di.component.DaggerISHNewsItemComponent
import com.rabtman.acgnews.di.module.ISHNewsItemModule
import com.rabtman.acgnews.mvp.contract.ISHNewsContract
import com.rabtman.acgnews.mvp.model.entity.SHPostItem
import com.rabtman.acgnews.mvp.presenter.ISHNewsItemPresenter
import com.rabtman.acgnews.mvp.ui.activity.ISHNewsDetailActivity
import com.rabtman.acgnews.mvp.ui.adapter.ISHNewsItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.base.widget.CommonItemDecoration
import com.rabtman.common.di.component.AppComponent

/**
 * @author Rabtman 鼠绘资讯
 */
class ISHNewsFragment : BaseFragment<ISHNewsItemPresenter>(), ISHNewsContract.View {
    @BindView(R2.id.rcv_news_item)
    lateinit var rcvNewsItem: RecyclerView

    @BindView(R2.id.swipe_refresh_news)
    lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var mAdapter: ISHNewsItemAdapter
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerISHNewsItemComponent.builder()
                .appComponent(appComponent)
                .iSHNewsItemModule(ISHNewsItemModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgnews_fragment_news_item
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getAcgNewsList()
    }

    override fun initData() {
        mAdapter = ISHNewsItemAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val shPostItem = adapter.data[position] as SHPostItem
            val intent = Intent(context, ISHNewsDetailActivity::class.java)
            intent.putExtra(IntentConstant.ISH_NEWS_ITEM, shPostItem)
            startActivity(intent)
        }
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcvNewsItem!!.addItemDecoration(CommonItemDecoration(2, CommonItemDecoration.UNIT_DP))
        rcvNewsItem!!.layoutManager = layoutManager
        mAdapter.loadMoreModule.setOnLoadMoreListener { mPresenter.getMoreAcgNewsList() }
        rcvNewsItem!!.adapter = mAdapter
        swipeRefresh!!.setOnRefreshListener { mPresenter.getAcgNewsList() }
        setSwipeRefreshLayout(swipeRefresh)
        mPresenter.getAcgNewsList()
    }

    override fun showAcgNews(ISHNewsList: List<SHPostItem>) {
        mAdapter.setList(ISHNewsList)
    }

    override fun showMoreAcgNews(ISHNewsList: List<SHPostItem>?, canLoadMore: Boolean) {
        ISHNewsList?.let { mAdapter.addData(it) }
        mAdapter.loadMoreModule.loadMoreComplete()
        if (!canLoadMore) {
            mAdapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun showError(message: String) {
        if (swipeRefresh!!.isRefreshing) {
            swipeRefresh!!.isRefreshing = false
        }
        super.showError(message)
    }

    override fun onLoadMoreFail() {
        mAdapter.loadMoreModule.loadMoreFail()
    }
}