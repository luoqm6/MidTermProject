package com.example.midtermproject;

/*
 * Created by qingming on 2017/11/13.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class RoleLists extends AppCompatActivity {
    /*所有的人物信息*/
    final String []name={
            "刘备","曹操",
            "孙权","关羽",
            "张飞","诸葛亮",
            "周瑜","司马懿",
            "张辽","甘宁",
            "大乔","孙尚香",
            "甄姬","小乔",
            "蔡文姬","貂蝉"
    };
    final String []nickname={
            "玄德","孟德",
            "仲谋","云长",
            "益德","孔明",
            "公瑾","仲达",
            "文远","兴霸",
            "无","无",
            "无","无",
            "文姬（昭姬）","无"
    };
    final String []country={"蜀","魏","吴","蜀","蜀","蜀","吴","魏","魏","吴","吴","蜀","魏","吴","魏","其他"};
    final String []period={
            "161年－223年6月10日","155年－220年3月15日",
            "182年－252年5月21日","？－220年",
            "？－221年","181年-234年10月8日",
            "175年-210年","179年—251年9月7日",
            "169年－222年","？—220年",
            "180年-?","？ - ？",
            "183 - 221","？ - ？",
            "？ - ？","？ - ？"
    };
    final String []sex={"男","男","男","男","男","男","男","男","男","男","女","女","女","女","女","女"};
    final String []hometown={
            "幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人",
            "扬州庐江郡皖（安徽安庆市潜山县）","扬州吴郡富春（浙江杭州市富阳）",
            "冀州中山国毋极（河北石家庄市无极县西、滋水北岸）","扬州庐江郡皖（安徽安庆市潜山县）",
            "东汉陈留郡圉县(今河南开封杞县)人","无法考证"
    };
    final String []intro={
            "刘备，蜀汉的开国皇帝，汉景帝之子中山靖王刘胜的后代。刘备少年孤贫，以贩鞋织草席为生。黄巾起义时，刘备与关羽、张飞桃园结义，成为异姓兄弟，一同剿除黄巾，有功，任安喜县尉，不久辞官；董卓乱政之际，刘备随公孙瓒讨伐董卓，三人在虎牢关战败吕布。后诸侯割据，刘备势力弱小，经常寄人篱下，先后投靠过公孙瓒、曹操、袁绍、刘表等人，几经波折，却仍无自己的地盘。赤壁之战前夕，刘备在荆州三顾茅庐，请诸葛亮出山辅助，在赤壁之战中，联合孙权打败曹操，奠定了三分天下的基础。刘备在诸葛亮的帮助下占领荆州，不久又进兵益州，夺取汉中，建立了横跨荆益两州的政权。后关羽战死，荆州被孙权夺取，刘备大怒，于称帝后伐吴，在夷陵之战中为陆逊用火攻打得大败，不久病逝于白帝城，临终托孤于诸葛亮。",
            "曹操是西园八校尉之一，曾只身行刺董卓，失败后和袁绍共同联合天下诸侯讨伐董卓，后独自发展自身势力，一生中先后战胜了袁术、吕布、张绣、袁绍、刘表、张鲁、马超等割据势力，统一了北方。但是在南下讨伐江东的战役中，曹操在赤壁惨败。后来在和蜀汉的汉中争夺战中，曹操再次无功而返。曹操一生未称帝，他病死后，曹丕继位后不久称帝，追封曹操为魏武皇帝。",
            "孙权19岁就继承了其兄孙策之位，力据江东，击败了黄祖。后东吴联合刘备，在赤壁大战击溃了曹操军。东吴后来又和曹操军在合肥附近鏖战，并从刘备手中夺回荆州、杀死关羽、大破刘备的讨伐军。曹丕称帝后孙权先向北方称臣，后自己建吴称帝，迁都建业。",
            "因本处势豪倚势凌人，关羽杀之而逃难江湖。闻涿县招军破贼，特来应募。与刘备、张飞桃园结义，羽居其次。使八十二斤青龙偃月刀随刘备东征西讨。虎牢关温酒斩华雄，屯土山降汉不降曹。为报恩斩颜良、诛文丑，解曹操白马之围。后得知刘备音信，过五关斩六将，千里寻兄。刘备平定益州后，封关羽为五虎大将之首，督荆州事。羽起军攻曹，放水淹七军，威震华夏。围樊城右臂中箭，幸得华佗医治，刮骨疗伤。但未曾提防东吴袭荆州，关羽父子败走麦城，突围中被捕，不屈遭害。",
            "与刘备和关羽桃园结义，张飞居第三。随刘备征讨黄巾，刘备终因功被朝廷受予平原相，后张飞鞭挞欲受赂的督邮。十八路诸侯讨董时，三英战吕布，其勇为世人所知。曹操以二虎竞食之计迫刘备讨袁术，刘备以张飞守徐州，诫禁酒，但还是因此而鞭打曹豹招致吕布东袭。刘备反曹后，反用劫寨计擒曹将刘岱，为刘备所赞。徐州终为曹操所破，张飞与刘备失散，占据古城。误以为降汉的关羽投敌，差点一矛将其杀掉。曹操降荊州后引骑追击，刘备败逃，张飞引二十余骑，立马于长阪桥，吓退曹军数十里。庞统死后刘备召其入蜀，张飞率军沿江而上，智擒巴郡太守严颜并生获之，张飞壮而释放。于葭萌关和马超战至夜间，双方点灯，终大战数百回合。瓦口关之战时扮作醉酒，智破张郃。后封为蜀汉五虎大将。及关羽卒，张飞悲痛万分，每日饮酒鞭打部下，导致为帐下将张达、范强所杀，他们持其首顺流而奔孙权。",
            "人称卧龙先生，有经天纬地之才，鬼神不测之机。刘皇叔三顾茅庐，遂允出山相助。曾舌战群儒、借东风、智算华容、三气周瑜，辅佐刘备于赤壁之战大败曹操，更取得荆州为基本。后奉命率军入川，于定军山智激老黄忠，斩杀夏侯渊，败走曹操，夺取汉中。刘备伐吴失败，受遗诏托孤，安居平五路，七纵平蛮，六出祁山，鞠躬尽瘁，死而后已。其手摇羽扇，运筹帷幄的潇洒形象，千百年来已成为人们心中“智慧”的代名词。",
            "偏将军、南郡太守。自幼与孙策交好，策离袁术讨江东，瑜引兵从之。为中郎将，孙策相待甚厚，又同娶二乔。策临终，嘱弟权曰：“外事不决，可问周瑜”。瑜奔丧还吴，与张昭共佐权，并荐鲁肃等，掌军政大事。赤壁战前，瑜自鄱阳归。力主战曹，后于群英会戏蒋干、怒打黄盖行诈降计、后火烧曹军，大败之。后下南郡与曹仁相持，中箭负伤，与诸葛亮较智斗，定假涂灭虢等计，皆为亮破，后气死于巴陵，年三十六岁。临终，上书荐鲁肃代其位，权为其素服吊丧。",
            "早年任文学掾，后任主簿，是曹操帐下谋士之一，但并不出名。襄樊之战时和蒋济劝曹操勿迁都，可割江南封孙权，令其袭杀关羽，事后如他所料。后来司马懿协助曹丕代汉，出谋五路伐蜀，开始崭露头角，在曹丕病逝前成为顾命大臣。曹叡继位后，司马懿主动请命去防御魏国西部，后因蜀汉马谡的反间计一度被废，但面对诸葛亮的北伐强攻，魏国不得不再次启用司马懿，先擒孟达，后败马谡于街亭。司马懿多次败于诸葛亮，于是采用闭门不战的策略防守，直至诸葛亮病逝。后出兵平公孙渊。曹芳继位后，司马懿、曹爽共同辅政。司马懿受曹爽排挤，于是发动政变诛杀曹爽一族，自此掌握魏国大权。司马懿病逝后，魏国政权仍由其儿子把持，多年后导致晋朝代魏的发生。司马懿被后代追封为晋朝皇帝。",
            "面如紫玉，目若朗星。初为吕布部将，数令曹操陷于苦战，武勇亦为关羽所称道。后吕布亡，曹操听刘备、关羽之劝，待张辽以上礼，遂降。以后张辽随军征讨，多有战功，曹操亦待之如亲信。曹操围关羽时张辽成功劝关羽降。赤壁败，张辽亲载曹操脱难，并射伤权将黄盖。后曹操以张辽引李典、乐进守合肥，以御孙权。后孙权果引军入寇，张辽用计分弱其势，自率二千余骑败敌十万，威震逍遥津。黄初五年，随曹丕征江东，为吴将丁奉以箭射其腰，回营后不治身亡，文帝厚葬之。",
            "吴将。初为锦帆贼，后投黄祖，未受重用，转仕吴，并助孙权败黄祖军。与曹军战，甘宁冲锋在先，奋勇杀敌，百骑劫曹营。后救凌统，两人遂结为生死之交。刘备伐吴时甘宁得痢疾，带病出战。后为蛮王沙摩柯以箭射头，坐于大树之下而死，树上群鸦数百，围绕其尸。",
            "江东乔国老有二女，大乔和小乔。大乔有沉鱼落雁之资，倾国倾城之容。孙策征讨江东，攻取皖城，娶大乔为妻。自古美女配英雄，伯符大乔堪绝配。曹操赤壁鏖兵，虎视江东，曾有揽二乔娱暮年，还足平生之愿。",
            "演义中为孙尚香。孙尚香之名为日后戏曲所创作出来的。刘备向东吴借荆州不还，鲁肃身负关系；周瑜一为救友，二为国计，于是上书孙权，教使「美人计」，进妹予刘备为夫人，诱其丧志而疏远属下。孙夫人才捷刚猛，有诸兄之风，身边侍婢百余人，皆亲自执刀侍立。不料在诸葛亮的锦囊妙计安排下，假婚成真姻；后来夫人更助刘备返蜀，于路上怒斥追袭的吴将。后刘备入益州，使赵云领留营司马，留守荆州。此时孙权闻知刘备西征，于是遣周善引领舟船以迎孙夫人，而夫人带着后主刘禅回吴，幸得赵云与张飞勒兵截江，方重夺刘禅。彝陵之战，刘备战败，有讹言传入吴中，道刘备已死，孙夫人伤心不已，望西痛哭，投江而死。后人为其立庙，号曰「枭姬庙」。",
            "甄氏初期嫁与袁绍次子袁熙，袁熙带兵出外征战，留下甄氏独身照顾婆婆，袁氏败亡后，曹操之子曹丕见其美艳动人，便纳为己有。黄初年间，魏文帝曹丕新纳的宠妾郭后栽赃甄后，诬陷她埋木偶诅咒文帝。文帝曹丕大怒，将甄后赐死。",
            "庐江皖县桥国老次女，秀美绝伦，貌压群芳，又琴棋书画无所不通周瑜攻取皖城，迎娶小乔为妻。周郎小乔英雄美女、郎才女貌 ，被流传为千古佳话.",
            "东汉大文学家蔡邕的女儿。初嫁于卫仲道，丈夫死去而回到自己家里，后值因匈奴入侵，蔡琰被匈奴左贤王掳走，嫁给匈奴人，并生育了两个孩子。十二年后，曹操统一北方，用重金将蔡琰赎回，并将其嫁给董祀。蔡琰同时擅长文学、音乐、书法。",
            "舍身报国的可敬女子，她为了挽救天下黎民，为了推翻权臣董卓的荒淫统治，受王允所托，上演了可歌可泣的连环计（连环美人计），周旋于两个男人之间，成功的离间了董卓和吕布，最终吕布将董卓杀死，结束了董卓专权的黑暗时期。"
    };
    final int []imgId={
            R.mipmap.liubei,R.mipmap.caocao,
            R.mipmap.sunquan,R.mipmap.guanyu,
            R.mipmap.zhangfei,R.mipmap.zhugeliang,
            R.mipmap.zhouyu,R.mipmap.simayi,
            R.mipmap.zhangliao,R.mipmap.ganning,
            R.mipmap.daqiao,R.mipmap.sunshangxiang,
            R.mipmap.zhenji,R.mipmap.xiaoqiao,
            R.mipmap.caiwenji,R.mipmap.diaochan
    };

    /*为了回调函数使用这些变量向购物车列表添加商品，声明放在外面*/
    private List<Map<String,Role>> listItems1 = new ArrayList<>();
    private List<Map<String,Role>> listItems2 = new ArrayList<>();
    private List<Map<String,Object>> simpleListItems2 = Role.getSimpleList(listItems2);
    Map<String,Role> s;
    SimpleAdapter simpleAdapter;

    //这两个声明放在onCreate外面不能初始化byid！！！！！！！！！！！！！！！！！！！！！！！！！！
    private FloatingActionButton fab1 ;
    private FloatingActionButton fab2 ;
    //这两个声明放在onCreate外面不能初始化byid！！！！！！！！！！！！！！！！！！！！！！！！！！

    //广播使用的filter
    String STATICACTION="com.example.midtermproject.STATICACTION";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolelists);


        //商品列表的初始化
        //final List<Map<String,Object>> listItems1 = new ArrayList<>();
        for(int i=0;i<10;i++){
            Map<String,Role> tmp = new LinkedHashMap<>();
            Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],intro[i],imgId[i],false);
            tmp.put("Role",tmpR);
            listItems1.add(tmp);
        }


        //eventbus的注册
        EventBus.getDefault().register(this);



        //两个右下角按钮的初始化以及将进入页面初始化为商品列表
        fab1 = (FloatingActionButton) findViewById(R.id.fabSL1);
        fab2 = (FloatingActionButton) findViewById(R.id.fabSL2);
        changeToRecyclerView();


        //发送静态广播推送推荐商品
        BroadcastStatic(STATICACTION);


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToListView();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToRecyclerView();
            }
        });


        //RecyclerView实现物品清单begin/////////////////
        final RecyclerView mRecyclerView;
        final CommonAdapter recycleAdapter;
        final RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recycleAdapter = new CommonAdapter(this,R.layout.recyclerviewitem,listItems1){
            @Override
            public void convert(ViewHolder holder,Map<String,Role> s){
                TextView itemCountryInR = holder.getView(R.id.itemCountryInR);
                itemCountryInR.setText(s.get("Role").getcountry());
                TextView itemNameInR = holder.getView(R.id.itemNameInR);
                itemNameInR.setText(s.get("Role").getname());
//                ImageView itemImgInR = holder.getView(R.id.itemImgInR);
//                itemImgInR.setImageResource(s.get("Role").getimgId());
            }
        };
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        recycleAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view,int position) {
                s=listItems1.get(position);
                Role tmpR=s.get("Role");
                //跳转至商品详情界面
                Bundle bundle = tmpR.putInBundle();
                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(RoleLists.this,ItemInfo.class);
                startActivityForResult(intent,1);
            }
            @Override
            public boolean onLongClick(View view,int position) {
                s=listItems1.get(position);
                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "从列表移除", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                listItems1.remove(position);
                recycleAdapter.notifyItemRemoved(position);
                recycleAdapter.notifyItemChanged(position);
                recycleAdapter.notifyDataSetChanged();
                return true;
            }
        });



        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(recycleAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
        //mRecyclerView.setAdapter(recycleAdapter);/////
        //RecyclerView实现物品清单end/////////////////////////////



        //ListView实现购物车清单begin/////////////////////////////


        final ListView listview = (ListView) findViewById(R.id.ListView);
        simpleListItems2 = Role.getSimpleList(listItems2);
        simpleAdapter = new SimpleAdapter(
                this,simpleListItems2,R.layout.listviewitem,
                new String[]{"country","name"},new int []{R.id.itemCountryInL,R.id.itemNameInL});
        listview.setAdapter(simpleAdapter);

        final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if(position==0) return ;
                s=listItems2.get(position);
                Role tmpR=listItems2.get(position).get("Role");
                Bundle bundle = tmpR.putInBundle();
                Intent intent=new Intent();
                intent.putExtras(bundle);
                intent.setClass(RoleLists.this,ItemInfo.class);
                startActivityForResult(intent,1);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                //if(position==0) return true;
                s=listItems2.get(position);
                //购物车界面长按删除提示信息
                alterDialog.setTitle("逐出英雄").setMessage("从麾下逐出"+s.get("Role").getname()+"?").setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                            }
                        }
                ).setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "从麾下逐出", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                listItems2.remove(position);
                                simpleListItems2.remove(position);
                                simpleAdapter.notifyDataSetChanged();
                            }
                        }
                ).create();
                alterDialog.show();
                return true;
            }
        });
        //ListView实现购物车清单end//////////////////////////////////

        //搜索框实现////////////////////////////////////////////////
        SearchView searchView=(SearchView) findViewById(R.id.svinrolelists);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("请输入搜索内容");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            List<Map<String,Role>> listtmp1=listItems1;
            List<Map<String,Role>> listtmp2=listItems2;
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO onQueryTextSubmit
                Toast.makeText(RoleLists.this, "cdsjcgsjgc", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO onQueryTextChange
                if(TextUtils.isEmpty(newText)){
                    listItems1.clear();
                    listItems2.clear();
                    listItems1.addAll(listtmp1);
                    listItems2.addAll(listtmp2);
                }
                else{
                    listItems1.clear();
                    listItems2.clear();
                }
                simpleListItems2 = Role.getSimpleList(listItems2);
                recycleAdapter.notifyDataSetChanged();
                simpleAdapter.notifyDataSetChanged();
                return false;
            }
        });
        //搜索框实现////////////////////////////////////////////////
    }

    /*回调函数，当从子界面回到这个父界面时调用，如果回传数据不为空也即是按下加入购物车则更新购物车列表*/
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==1){
            if(resultCode==1){
                Bundle bundle=getIntent().getExtras();
                if(bundle!=null){
                    if(bundle.getInt("whichView")==0){
                        changeToRecyclerView();
                    }
                    else{
                        changeToListView();
                    }
                }
            }
        }
    }
    public void BroadcastStatic(String message){
        Random random=new Random();
        int index=random.nextInt(10);
        Role tmpR=listItems1.get(index).get("Role");
        Bundle bundle = tmpR.putInBundle();
        Intent intentBroadcast = new Intent(message);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);
    }


    //声明并重写自己的订阅函数
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        Role tmpR=messageEvent.getRole();
        Map<String,Role> tmp = new LinkedHashMap<>();
        tmp.put("Role",tmpR);
        listItems2.add(tmp);
        Map<String,Object> tmp1 = new LinkedHashMap<>();
        tmp1.put("country",tmpR.getcountry());
        tmp1.put("name",tmpR.getname());
        tmp1.put("imgId",tmpR.getimgId());
        simpleListItems2.add(tmp1);
        simpleAdapter.notifyDataSetChanged();
    }



    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            if(bundle.getInt("whichView")==0){
                changeToRecyclerView();
            }
            else{
                changeToListView();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initlistItems1(){
        //商品列表的初始化
        //final List<Map<String,Object>> listItems1 = new ArrayList<>();
        for(int i=0;i<10;i++){
            Map<String,Role> tmp = new LinkedHashMap<>();
            Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],intro[i],imgId[i],false);
            tmp.put("Role",tmpR);
            listItems1.add(tmp);
        }
    }



    public void changeToRecyclerView(){
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.VISIBLE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.GONE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.VISIBLE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.GONE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
    }

    public void changeToListView(){
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.GONE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.VISIBLE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.GONE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.VISIBLE);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.VISIBLE);
    }
}
