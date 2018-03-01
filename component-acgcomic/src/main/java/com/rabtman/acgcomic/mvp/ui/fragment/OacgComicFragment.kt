package com.rabtman.acgcomic.mvp.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerOacgComicComponent
import com.rabtman.acgcomic.di.OacgComicModule
import com.rabtman.acgcomic.mvp.OacgComicContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.acgcomic.mvp.presenter.OacgComicPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.ComicMenuAdapter
import com.rabtman.acgcomic.mvp.ui.adapter.OacgComicItemAdpater
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.base.widget.DropDownMenu
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_OACG)
class OacgComicFragment : BaseFragment<OacgComicPresenter>(), OacgComicContract.View {
    @BindView(R.id.ddm_comic_menu)
    lateinit var mMenuComicMain: DropDownMenu
    private var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mRcvComicMain: RecyclerView? = null
    private var mOacgComicItemAdapter: OacgComicItemAdpater? = null
    private val headers = listOf("分类")
    //菜单选项
    private val type = arrayListOf(
            "全部", "恋爱", "搞笑", "魔幻",
            "动作", "科幻", "生活", "后宫",
            "治愈", "推理", "恐怖", "古风",
            "耽美百合", "少年", "少女", "校园")
    //菜单选项适配器
    private var typeAdapter: ComicMenuAdapter? = null
    //弹出菜单视图集
    private var popupViews: List<View>? = null

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_view_comic_menu
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerOacgComicComponent.builder()
                .appComponent(appComponent)
                .oacgComicModule(OacgComicModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        initDropDownMenu()
        mMenuComicMain.setDropDownMenu(headers, popupViews!!, initContentView())

        mPresenter.getComicInfos()
    }

    //初始化菜单布局
    private fun initDropDownMenu() {
        //分类
        val typeView = RecyclerView(this.context)
        typeView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        typeAdapter = ComicMenuAdapter(type)
        typeAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuComicMain.setTabText(if (position == 0) headers[0] else type[position])
                mMenuComicMain.closeMenu()
                mPresenter.getComicInfosByMenuSelected(if (position == 0) -1 else position)
            }
        }
        typeView.layoutManager = GridLayoutManager(this.context, 4)
        typeView.adapter = typeAdapter
        popupViews = listOf<View>(typeView)
    }

    //初始化内容布局
    private fun initContentView(): View {
        val contentView = layoutInflater.inflate(R.layout.acgcomic_view_oacg_comic_content, null)
        mSwipeRefresh = contentView.findViewById(R.id.swipe_refresh_oacg_comic)
        mRcvComicMain = contentView.findViewById(R.id.rcv_oacg_comic)
        mOacgComicItemAdapter = OacgComicItemAdpater(appComponent.imageLoader())
        //加载更多
        mOacgComicItemAdapter?.setOnLoadMoreListener({ mPresenter.getMoreComicInfos() }, mRcvComicMain)
        //点击跳转到漫画详情
        mOacgComicItemAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adpater, _, pos ->
            val oacgComicItem: OacgComicItem = adpater.getItem(pos) as OacgComicItem
            RouterUtils.getInstance()
                    .build(RouterConstants.PATH_COMIC_OACG_DETAIL)
                    .withParcelable(IntentConstant.OACG_COMIC_ITEM, oacgComicItem)
                    .navigation()
        }
        mRcvComicMain?.layoutManager = LinearLayoutManager(this.context)
        mRcvComicMain?.adapter = mOacgComicItemAdapter

        //下拉刷新
        mSwipeRefresh?.setOnRefreshListener({ mPresenter.getComicInfos() })
        setSwipeRefreshLayout(mSwipeRefresh)
        return contentView
    }

    override fun showComicInfos(comicInfos: List<OacgComicItem>) {
        mRcvComicMain?.scrollToPosition(0)
        mOacgComicItemAdapter?.setNewData(comicInfos)
    }

    override fun showMoreComicInfos(comicInfos: List<OacgComicItem>, canLoadMore: Boolean) {
        mOacgComicItemAdapter?.addData(comicInfos)
        mOacgComicItemAdapter?.loadMoreComplete()
        if (!canLoadMore) {
            mOacgComicItemAdapter?.loadMoreEnd()
        }
    }

    override fun onLoadMoreFail() {
        mOacgComicItemAdapter?.loadMoreFail()
    }

}