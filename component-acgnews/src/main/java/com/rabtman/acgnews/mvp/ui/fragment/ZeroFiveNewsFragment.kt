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
import com.rabtman.acgnews.di.component.DaggerZeroFiveNewsItemComponent
import com.rabtman.acgnews.di.module.ZeroFiveNewsItemModule
import com.rabtman.acgnews.mvp.contract.ZeroFiveNewsContract
import com.rabtman.acgnews.mvp.model.jsoup.ZeroFiveNews
import com.rabtman.acgnews.mvp.presenter.ZeroFiveNewsItemPresenter
import com.rabtman.acgnews.mvp.ui.activity.ZeroFiveNewsDetailActivity
import com.rabtman.acgnews.mvp.ui.adapter.ZeroFiveNewsItemAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.base.widget.CommonItemDecoration
import com.rabtman.common.di.component.AppComponent

/**
 * @author Rabtman 羁绊资讯
 */
class ZeroFiveNewsFragment : BaseFragment<ZeroFiveNewsItemPresenter>(), ZeroFiveNewsContract.View {

    @BindView(R2.id.rcv_news_item)
    lateinit var rcvNewsItem: RecyclerView

    @BindView(R2.id.swipe_refresh_news)
    lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var mAdapter: ZeroFiveNewsItemAdapter
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerZeroFiveNewsItemComponent.builder()
                .appComponent(appComponent)
                .zeroFiveNewsItemModule(ZeroFiveNewsItemModule(this))
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
        mAdapter = ZeroFiveNewsItemAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val zeroFiveNews = adapter.data[position] as ZeroFiveNews
            val intent = Intent(context, ZeroFiveNewsDetailActivity::class.java)
            intent.putExtra(IntentConstant.ZERO_FIVE_NEWS_ITEM, zeroFiveNews)
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

    override fun getNewsUrl(pageNo: Int): String {
        return getString(R.string.acgnews_url_zero_five, pageNo)
    }

    override fun showAcgNews(zeroFiveNewsList: List<ZeroFiveNews>) {
        mAdapter.setList(zeroFiveNewsList)
    }

    override fun showMoreAcgNews(zeroFiveNewsList: List<ZeroFiveNews>?, canLoadMore: Boolean) {
        zeroFiveNewsList?.let { mAdapter.addData(it) }
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
        mAdapter!!.loadMoreModule.loadMoreFail()
    }
}