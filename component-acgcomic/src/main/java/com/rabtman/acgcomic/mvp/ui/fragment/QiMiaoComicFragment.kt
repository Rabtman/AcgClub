package com.rabtman.acgcomic.mvp.ui.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerQiMiaoComicComponent
import com.rabtman.acgcomic.di.QiMiaoComicModule
import com.rabtman.acgcomic.mvp.QiMiaoComicContract
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.acgcomic.mvp.presenter.QiMiaoComicPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.ComicMenuAdapter
import com.rabtman.acgcomic.mvp.ui.adapter.QiMiaoComicItemAdpater
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.base.widget.DropDownMenu
import com.rabtman.common.di.component.AppComponent
import com.rabtman.common.utils.ToastUtil
import com.rabtman.router.RouterConstants
import com.rabtman.router.RouterUtils


/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_QIMIAO)
class QiMiaoComicFragment : BaseFragment<QiMiaoComicPresenter>(), QiMiaoComicContract.View {
    @BindView(R2.id.ddm_comic_menu)
    lateinit var mMenuComicMain: DropDownMenu
    private var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mRcvComicMain: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private lateinit var mQiMiaoComicItemAdapter: QiMiaoComicItemAdpater
    private val headers = listOf("分类")

    //菜单选项
    private val type = arrayListOf(
            "全部", "热血", "恋爱", "青春", "彩虹",
            "冒险", "后宫", "悬疑", "玄幻",
            "穿越", "都士", "腹黑", "爆笑",
            "少年", "奇幻", "古风", "妖恋",
            "元气", "治愈", "励志", "日常", "百合")

    //菜单选项适配器
    private lateinit var typeAdapter: ComicMenuAdapter

    //弹出菜单视图集
    private lateinit var popupViews: List<View>

    //头部搜索框
    private lateinit var headerSearchView: View

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_view_comic_menu
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerQiMiaoComicComponent.builder()
                .appComponent(appComponent)
                .qiMiaoComicModule(QiMiaoComicModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        initDropDownMenu()
        initHeaderView()
        mMenuComicMain.setDropDownMenu(headers, popupViews, initContentView())

        mPresenter.getComicInfos()
    }

    override fun useLoadSir(): Boolean {
        return true
    }

    override fun onPageRetry(v: View?) {
        mPresenter.getComicInfos()
    }

    override fun hideLoading() {
        mSwipeRefresh?.let { it ->
            if (it.isRefreshing) {
                it.isRefreshing = false
            }
        }
        super.hideLoading()
    }

    //初始化菜单布局
    private fun initDropDownMenu() {
        //分类
        val typeView = RecyclerView(mContext)
        typeView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey200))
        typeAdapter = ComicMenuAdapter(type).apply {
            this.setOnItemClickListener { adapter, _, position ->
                if (adapter is ComicMenuAdapter) {
                    adapter.setCheckItem(position)
                    mMenuComicMain.setTabText(if (position == 0) headers[0] else type[position])
                    mMenuComicMain.closeMenu()
                    mPresenter.getComicInfosByMenuSelected(if (position == 0) "" else (position + 6).toString())
                }
            }
        }
        typeView.layoutManager = GridLayoutManager(mContext, 4)
        typeView.adapter = typeAdapter
        popupViews = listOf<View>(typeView)
    }

    //初始化内容布局
    private fun initContentView(): View {
        val contentView = layoutInflater.inflate(R.layout.acgcomic_view_qimiao_comic_content, null)
        mSwipeRefresh = contentView.findViewById(R.id.swipe_refresh_qimiao_comic)
        mRcvComicMain = contentView.findViewById(R.id.rcv_qimiao_comic)
        mQiMiaoComicItemAdapter = QiMiaoComicItemAdpater().apply {
            this.setOnItemClickListener { adpater, _, pos ->
                val comicItem: QiMiaoComicItem = adpater.getItem(pos) as QiMiaoComicItem
                if (comicItem.comicLink.isNotEmpty()) {
                    RouterUtils.instance
                            .build(RouterConstants.PATH_COMIC_QIMIAO_DETAIL)
                            .withParcelable(IntentConstant.QIMIAO_COMIC_ITEM, comicItem)
                            .navigation()
                } else {
                    showMsg("找不到该漫画/(ㄒoㄒ)/~~")
                }
            }
        }
        mQiMiaoComicItemAdapter.setHeaderView(headerSearchView)
        //加载更多
        mQiMiaoComicItemAdapter.loadMoreModule.setOnLoadMoreListener { mPresenter.getMoreComicInfos() }
        mLayoutManager = LinearLayoutManager(mContext)
        mRcvComicMain?.layoutManager = mLayoutManager
        mRcvComicMain?.adapter = mQiMiaoComicItemAdapter

        //下拉刷新
        mSwipeRefresh?.setOnRefreshListener { mPresenter.getComicInfos() }
        return contentView
    }

    private fun initHeaderView(): View {
        headerSearchView = layoutInflater.inflate(R.layout.acgcomic_view_qimiao_comic_search, null)
        val etKeyword: AppCompatEditText = headerSearchView.findViewById(R.id.et_qimiao_comic_keyword)
        val btnClear: AppCompatImageButton = headerSearchView.findViewById(R.id.btn_qimiao_comic_close)
        val btnSearch: AppCompatImageButton = headerSearchView.findViewById(R.id.btn_qimiao_comic_search)

        //监听搜索框输入情况
        etKeyword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //控制清除按钮的状态
                if (s.toString().isEmpty()) {
                    btnClear.visibility = View.INVISIBLE
                } else {
                    btnClear.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
        //清除事件
        btnClear.setOnClickListener { etKeyword.setText("") }
        //搜索事件
        btnSearch.setOnClickListener {
            val keyword = etKeyword.text.toString()
            if (keyword.isEmpty()) {
                ToastUtil.show(context, getString(R.string.acgcomic_msg_empty_comic_search))
            } else {
                etKeyword.setText("")
                mPresenter.searchComicInfos(keyword)
            }
        }
        return headerSearchView
    }

    override fun resetComicMenu() {
        typeAdapter.setCheckItem(0)
        mMenuComicMain.setTabPosition(0)
        mMenuComicMain.setTabText(headers[0])
        mMenuComicMain.setTabPosition(-1)
    }

    override fun showSearchComicInfo(comicInfos: List<QiMiaoComicItem>?, canLoadMore: Boolean) {
        mRcvComicMain?.scrollToPosition(0)
        mQiMiaoComicItemAdapter.setList(comicInfos)
        mRcvComicMain?.scrollBy(0, 0)
        if (!canLoadMore) {
            mQiMiaoComicItemAdapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun showComicInfo(comicInfos: List<QiMiaoComicItem>?) {
        mRcvComicMain?.scrollToPosition(0)
        mQiMiaoComicItemAdapter.setList(comicInfos)
        mRcvComicMain?.scrollBy(0, 0)
    }

    override fun showMoreComicInfo(comicInfos: List<QiMiaoComicItem>?, canLoadMore: Boolean) {
        comicInfos?.let { mQiMiaoComicItemAdapter.addData(it) }
        mQiMiaoComicItemAdapter.loadMoreModule.loadMoreComplete()
        if (!canLoadMore) {
            mQiMiaoComicItemAdapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun onLoadMoreFail() {
        mQiMiaoComicItemAdapter.loadMoreModule.loadMoreFail()
    }

}