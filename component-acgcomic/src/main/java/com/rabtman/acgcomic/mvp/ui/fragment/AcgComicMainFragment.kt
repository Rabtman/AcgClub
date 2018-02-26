package com.rabtman.acgcomic.mvp.ui.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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
    private var headers = arrayOf("题材", "读者群", "进度", "地域", "排序")
    //菜单选项
    private var topic = arrayListOf(
            "全部", "冒险", "欢乐向", "格斗",
            "科幻", "爱情", "竞技", "魔法",
            "校园", "悬疑", "恐怖", "生活亲情",
            "百合", "伪娘", "耽美", "后宫",
            "萌系", "治愈", "武侠", "职场",
            "奇幻", "节操", "轻小说", "搞笑")
    private var groups = arrayListOf("全部", "少年", "少女", "青年")
    private var status = arrayListOf("全部", "连载", "完结")
    private var area = arrayListOf("全部", "日本", "内地", "欧美", "港台", "韩国", "其他")
    private var sort = arrayListOf("人气", "更新")
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
        topicAdapter = ComicMenuAdapter(topic)
        topicAdapter!!.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            if (adapter is ComicMenuAdapter) {
                adapter.setCheckItem(position)
                mMenuAcgComic.setTabText(if (position == 0) headers[0] else topic[position])
            }
        })
        var topicView = RecyclerView(this.context)
        topicView.layoutManager = GridLayoutManager(this.context, 4)
        topicView.adapter = topicAdapter

        var groupView = RecyclerView(this.context)
        var statusView = RecyclerView(this.context)
        var areaView = RecyclerView(this.context)
        var sortView = RecyclerView(this.context)
    }

    override fun showComicInfos(comicInfos: List<AcgComicItem>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}