package com.rabtman.acgclub.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.common.base.SimpleActivity;
import com.zzhoujay.richtext.RichText;

/**
 * @author Rabtman
 */
public class AcgInfoDetailActivity extends SimpleActivity {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.tv_acg_detail_content)
  TextView tvAcgDetailContent;

  @Override
  protected int getLayout() {
    return R.layout.activity_acginfo_detail;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, "浏览");

    String testHtml = "<div class=\"articleContent\" style=\"color:black;font-size:16px;\">\n"
        + "\t\t\t\n"
        + "\t\t\t<p style=\"text-align: center;\">\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F60510310E11.jpg\"></p>\n"
        + "<p>\n"
        + "\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;声优小野早稀今年靠着《动物朋友》的浣熊一角大放异彩，不过她另一方面也不忘继续磨练《动物朋友》剧中缺少的领域……那就是耻力！最近小野早稀替漫画《变女~奇怪女子高中生甘栗千子~》的广播剧配音第一次说出“勃起”两个字，配完这部作品耻力上升100倍以后，肯定能够在声优之路拓展更多的发展性吧！</p>\n"
        + "<h2>\n"
        + "\t“出生以来第一次说出勃起两个字，请大家听听我生涩的勃起……”</h2>\n"
        + "<div style=\"text-align: center;\">\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F605103116449.jpg\" style=\"width: 550px; height: 137px;\"><br>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;声优小野早稀最近替有病漫画《变女~奇怪女子高中生甘栗千子~》的附录广播剧配音，她担任的角色桃木莉莉（桃木りり）就是个对勃起很有兴趣的古怪傢伙，更由于经常将这两个字挂在嘴边因此还有个勃起酱（勃起ちゃん）的暱称……</p>\n"
        + "\t<h3 style=\"text-align: left;\">\n"
        + "\t\t《变女》女性角色群</h3>\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F60510314EY.jpg\" style=\"width: 550px; height: 733px;\"><br>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;漫画作者此ノ木よしる说……</p>\n"
        + "\t<h2 style=\"text-align: left;\">\n"
        + "\t\t“小野早稀配广播剧最棒的一幕就是连唿勃起！”</h2>\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F6051032213V.jpg\" style=\"width: 550px; height: 549px;\"><br>\n"
        + "\t<h2 style=\"text-align: left;\">\n"
        + "\t\t勃起酱的声音视听“大家的胯下有精神吗？”</h2>\n"
        + "\t<blockquote data-lang=\"ja\">\n"
        + "\t\t<p dir=\"ltr\" style=\"text-align: left;\">\n"
        + "\t\t\tCV：小野早稀　　<br>\n"
        + "\t\t\t桃木りり「あだ名は勃起ちゃんです！！！！！」　</p>\n"
        + "\t</blockquote>\n"
        + "\t<blockquote data-lang=\"ja\" style=\"text-align: left;\">\n"
        + "\t\t— 此ノ木よしる@変女ドラマCD第二弾决定！</blockquote>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;在作者替小野早稀宣传以后，她似乎也从首次讲勃起的兴奋当中平復下来了……</p>\n"
        + "\t<h2 style=\"text-align: left;\">\n"
        + "\t\t“真的很开心！我已经不会觉得羞耻了！”</h2>\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F60510333E59.jpg\" style=\"width: 550px; height: 190px;\"><br>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;然后勃起酱的台词好像比之前多了好几倍……</p>\n"
        + "\t<h2 style=\"text-align: left;\">\n"
        + "\t\t“第7卷的广播剧比起第一弹有三倍勃起量！大家快预约听听勃起吧！”</h2>\n"
        + "\t<img alt=\"动物朋友,小野早稀,变女\" src=\"http://www.005.tv/uploads/allimg/170605/32-1F605103411407.jpg\" style=\"width: 550px; height: 205px;\"><br>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;声优都要讲出羞耻的台词，真辛苦……</p>\n"
        + "\t<p style=\"text-align: left;\">\n"
        + "\t\t&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;不过就是这样才能拓展戏路啊，加油提升耻力吧！</p>\n"
        + "</div>\n"
        + "<br>\n"
        + "\n"
        + "\t\t\t\n"
        + "\t\t\t\n"
        + "\t\t\t\n"
        + "\t\t</div>";

    RichText.fromHtml(testHtml)
        //.scaleType(ScaleType.FIT_AUTO)
        .into(tvAcgDetailContent);
  }

}
