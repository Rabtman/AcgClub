package com.rabtman.acgcomic.mvp.ui.fragment

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.di.ComicMainModule
import com.rabtman.acgcomic.di.DaggerComicMainComponent
import com.rabtman.acgcomic.mvp.ComicMainContract
import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import com.rabtman.acgcomic.mvp.presenter.ComicMainPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.ComicMenuAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.base.widget.DropDownMenu
import com.rabtman.common.di.component.AppComponent


/**
 * @author Rabtman
 */
class AcgComicMainFragment : BaseFragment<ComicMainPresenter>(), ComicMainContract.View {

    @BindView(R.id.ddm_acg_comic_menu)
    lateinit var mMenuAcgComic: DropDownMenu
    private val headers = listOf("题材", "读者群", "进度", "地域", "排序")
    //菜单选项
    private val topic = arrayListOf(
            "全部", "冒险", "欢乐向", "格斗",
            "科幻", "爱情", "竞技", "魔法",
            "校园", "悬疑", "恐怖", "生活亲情",
            "百合", "伪娘", "耽美", "后宫",
            "萌系", "治愈", "武侠", "职场",
            "奇幻", "节操", "轻小说", "搞笑")
    private val groups = arrayListOf("全部", "少年", "少女", "青年")
    private val status = arrayListOf("全部", "连载", "完结")
    private val area = arrayListOf("全部", "日本", "内地", "欧美", "港台", "韩国", "其他")
    private val sort = arrayListOf("人气", "更新")
    //当前菜单的选择结果
    private var selected = arrayOf(0, 0, 0, 0, 0)
    //菜单选项适配器
    private var topicAdapter: ComicMenuAdapter? = null
    private var groupAdapter: ComicMenuAdapter? = null
    private var statusAdapter: ComicMenuAdapter? = null
    private var areaAdapter: ComicMenuAdapter? = null
    private var sortAdapter: ComicMenuAdapter? = null
    //弹出菜单视图集
    private var popupViews: List<View>? = null

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_fragment_comic_main
    }

    override fun setupFragmentComponent(appComponent: AppComponent?) {
        DaggerComicMainComponent.builder()
                .appComponent(appComponent)
                .comicMainModule(ComicMainModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        //题材
        val topicView = RecyclerView(this.context)
        topicView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        topicAdapter = ComicMenuAdapter(topic)
        topicAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[0] else topic[position])
                mMenuAcgComic.closeMenu()
            }
        }
        topicView.layoutManager = GridLayoutManager(this.context, 4)
        topicView.adapter = topicAdapter
        //读者群
        val groupView = RecyclerView(this.context)
        groupView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        groupAdapter = ComicMenuAdapter(groups)
        groupAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[1] else groups[position])
                mMenuAcgComic.closeMenu()
            }
        }
        groupView.layoutManager = GridLayoutManager(this.context, 4)
        groupView.adapter = groupAdapter
        //进度
        val statusView = RecyclerView(this.context)
        statusView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        statusAdapter = ComicMenuAdapter(status)
        statusAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[2] else status[position])
                mMenuAcgComic.closeMenu()
            }
        }
        statusView.layoutManager = GridLayoutManager(this.context, 4)
        statusView.adapter = statusAdapter
        //地域
        val areaView = RecyclerView(this.context)
        areaView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        areaAdapter = ComicMenuAdapter(area)
        areaAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[3] else area[position])
                mMenuAcgComic.closeMenu()
            }
        }
        areaView.layoutManager = GridLayoutManager(this.context, 4)
        areaView.adapter = areaAdapter
        //排序
        /*val sortView = RecyclerView(this.context)
        sortView.setBackgroundColor(ContextCompat.getColor(this.context, R.color.grey200))
        sortAdapter = ComicMenuAdapter(sort)
        sortAdapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[4] else sort[position])
                mMenuAcgComic.closeMenu()
            }
        }
        sortView.layoutManager = GridLayoutManager(this.context, 4)
        sortView.adapter = sortAdapter*/

        //init context view
        val contentView = TextView(this.context)
        contentView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        contentView.text = "内容显示区域"
        contentView.gravity = Gravity.CENTER
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        popupViews = listOf<View>(topicView, groupView, statusView, areaView)
        mMenuAcgComic.setDropDownMenu(headers, popupViews!!, contentView)
    }

    override fun showComicInfos(comicInfos: List<AcgComicItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}