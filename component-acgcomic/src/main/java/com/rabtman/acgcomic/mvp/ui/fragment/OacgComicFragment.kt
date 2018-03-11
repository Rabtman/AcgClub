package com.rabtman.acgcomic.mvp.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.R2
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
import es.dmoral.toasty.Toasty

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_OACG)
class OacgComicFragment : BaseFragment<OacgComicPresenter>(), OacgComicContract.View {
    @BindView(R2.id.ddm_comic_menu)
    lateinit var mMenuComicMain: DropDownMenu
    private var mSwipeRefresh: SwipeRefreshLayout? = null
    private var mRcvComicMain: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
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
    //头部搜索框
    private lateinit var headerSearchView: View

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
        initHeaderView()
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
        mOacgComicItemAdapter?.setHeaderView(headerSearchView)
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
        mLayoutManager = LinearLayoutManager(this.context)
        mRcvComicMain?.layoutManager = mLayoutManager
        mRcvComicMain?.adapter = mOacgComicItemAdapter
        mRcvComicMain?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("onscroll", "recyclerview------>x:$dx ,y:$dy")
            }
        })

        //下拉刷新
        mSwipeRefresh?.setOnRefreshListener({ mPresenter.getComicInfos() })
        setSwipeRefreshLayout(mSwipeRefresh)
        return contentView
    }

    private fun initHeaderView(): View {
        headerSearchView = layoutInflater.inflate(R.layout.acgcomic_view_oacg_comic_search, null)
        val etKeyword: AppCompatEditText = headerSearchView.findViewById(R.id.et_oacg_comic_keyword)
        val btnClear: AppCompatImageButton = headerSearchView.findViewById(R.id.btn_oacg_comic_close)
        val btnSearch: AppCompatImageButton = headerSearchView.findViewById(R.id.btn_oacg_comic_search)

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
                Toasty.info(context, getString(R.string.acgcomic_msg_empty_comic_search))
            } else {
                mPresenter.searchComicInfos(keyword)
            }
        }
        return headerSearchView
    }

    override fun showSearchComicInfos(comicInfos: List<OacgComicItem>?) {
        mOacgComicItemAdapter?.setNewData(comicInfos)
        mOacgComicItemAdapter?.loadMoreEnd()
    }

    override fun showComicInfos(comicInfos: List<OacgComicItem>?) {
        mRcvComicMain?.scrollBy(0, headerSearchView.height)
        mOacgComicItemAdapter?.setNewData(comicInfos)
        //mRcvComicMain?.scrollBy(0, 0)
    }

    override fun showMoreComicInfos(comicInfos: List<OacgComicItem>?, canLoadMore: Boolean?) {
        if (canLoadMore == null || !canLoadMore) {
            mOacgComicItemAdapter?.loadMoreEnd()
        } else {
            comicInfos?.let { mOacgComicItemAdapter?.addData(it) }
            mOacgComicItemAdapter?.loadMoreComplete()
        }
    }

    override fun onLoadMoreFail() {
        mOacgComicItemAdapter?.loadMoreFail()
    }

}