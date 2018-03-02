package com.rabtman.acgcomic.mvp.ui.activity

import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.IntentConstant
import com.rabtman.acgcomic.di.DaggerOacgComicEpisodeDetailComponent
import com.rabtman.acgcomic.di.OacgComicEpisodeDetailModule
import com.rabtman.acgcomic.mvp.OacgComicEpisodeDetailContract
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.acgcomic.mvp.presenter.OacgComicEpisodeDetailPresenter
import com.rabtman.acgcomic.mvp.ui.adapter.OacgComicReadAdapter
import com.rabtman.common.base.BaseActivity
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_COMIC_OACG_READ)
class OacgComicReadActivity : BaseActivity<OacgComicEpisodeDetailPresenter>(), OacgComicEpisodeDetailContract.View, View.OnClickListener {

    //漫画阅读控制栏
    @BindView(R.id.layout_comic_top)
    lateinit var layoutComicTop: RelativeLayout
    @BindView(R.id.btn_comic_back)
    lateinit var btnComicBack: ImageView
    @BindView(R.id.tv_comic_title)
    lateinit var tvComicTitle: TextView
    @BindView(R.id.layout_comic_bottom)
    lateinit var layoutComicBottom: RelativeLayout
    @BindView(R.id.btn_comic_before)
    lateinit var btnComicBefore: ImageView
    @BindView(R.id.btn_comic_next)
    lateinit var btnComicNext: ImageView
    @BindView(R.id.seekbar_comic_proc)
    lateinit var seekComicProc: AppCompatSeekBar
    @BindView(R.id.tv_comic_count)
    lateinit var tvComicCount: TextView
    @BindView(R.id.tv_comic_pos)
    lateinit var tvComicPos: TextView

    //漫画内容
    @BindView(R.id.rcv_oacg_comic_content)
    lateinit var rcvOacgComicContent: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var oacgComicReadAdapter: OacgComicReadAdapter
    //当前漫画页数
    private var currentPage = 0
    //当前漫画总页数
    private var maxPage = 0

    override fun setupActivityComponent(appComponent: AppComponent?) {
        DaggerOacgComicEpisodeDetailComponent.builder()
                .appComponent(appComponent)
                .oacgComicEpisodeDetailModule(OacgComicEpisodeDetailModule(this))
                .build()
                .inject(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.acgcomic_activity_oacg_comic_content
    }

    override fun setStatusBar() {}

    override fun initData() {
        oacgComicReadAdapter = OacgComicReadAdapter(appComponent.imageLoader())
        layoutManager = LinearLayoutManager(this)
        rcvOacgComicContent.layoutManager = layoutManager
        rcvOacgComicContent.adapter = oacgComicReadAdapter
        rcvOacgComicContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val pageNo = layoutManager.findFirstVisibleItemPosition()
                if (maxPage > 0 && maxPage == layoutManager.findLastCompletelyVisibleItemPosition() + 1) {
                    currentPage = maxPage - 1
                    seekComicProc.progress = maxPage - 1
                    tvComicPos.text = maxPage.toString()
                } else if (currentPage != pageNo) {
                    currentPage = pageNo
                    seekComicProc.progress = pageNo
                    tvComicPos.text = (pageNo + 1).toString()
                }
            }
        })

        mPresenter.getEpisodeDetail(intent.getStringExtra(IntentConstant.OACG_COMIC_ID), intent.getStringExtra(IntentConstant.OACG_COMIC_CHAPTERID))
    }

    @OnClick(R.id.btn_comic_back)
    override fun onClick(view: View?) {
        if (view == null) return
        if (view.id == R.id.btn_comic_back) {
            finish()
        }
    }

    override fun showEpisodeDetail(episodePage: OacgComicEpisodePage) {
        oacgComicReadAdapter.setNewData(episodePage.pageContent)
        maxPage = episodePage.pageContent.size
        tvComicTitle.text = intent.getStringExtra(IntentConstant.OACG_COMIC_TITLE)
        tvComicPos.text = "1"
        tvComicCount.text = maxPage.toString()
        seekComicProc.max = maxPage - 1
        seekComicProc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                rcvOacgComicContent.scrollToPosition(currentPage)
            }

            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    currentPage = progress
                    tvComicPos.text = (progress + 1).toString()
                }
            }

        })
    }

}