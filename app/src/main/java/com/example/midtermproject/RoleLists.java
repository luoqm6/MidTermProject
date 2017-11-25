package com.example.midtermproject;

/*
 * Created by qingming on 2017/11/13.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
            "蔡文姬","貂蝉",
            "黄忠","赵云",
            "马超","吕布",

            "吕蒙","典韦",
            "黄月英","孙策",
            "曹植","刘禅",
            "孙鲁班","郭女王",
            "司马昭","华雄"
    };
    final String []nickname={
            "玄德","孟德",
            "仲谋","云长",
            "益德","孔明",
            "公瑾","仲达",
            "文远","兴霸",

            "无","无",
            "无","无",
            "文姬(昭姬)","无",
            "汉升(汉叔)","子龙",
            "孟起","奉先",

            "子明","无",
            "无","伯符",
            "子建","公嗣",
            "大虎","女王",
            "子上","无"
    };
    final String []country={"蜀","魏","吴","蜀","蜀","蜀","吴","魏","魏","吴",
            "吴","蜀","魏","吴","魏","群","蜀","蜀","蜀","群",
            "吴","魏","蜀","吴","魏","蜀","吴","魏","魏","群"};
    final String []period={
            "161年－223年6月10日","155年－220年3月15日",
            "182年－252年5月21日","？－220年",
            "？－221年","181年-234年10月8日",
            "175年-210年","179年—251年9月7日",
            "169年－222年","？—220年",

            "180年-?","？ - ？",
            "183年- 221年","？ - ？",
            "？ - ？","？ - ？",
            "？－220年","？－229年",
            "176年-223年","？－198年",

            "178－219","？－197年",
            "？ - ？","175－200年",
            "192年－232年","207年－271年",
            "？ - ？","184年－235年",
            "211年－265年","？－191年"
    };
    final String []sex={"男","男","男","男","男","男","男","男","男","男",
            "女","女","女","女","女","女","男","男","男","男",
            "男","男","女","男","男","男","女","女","男","男"};
    final String []hometown={
            "幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人",

            "扬州庐江郡皖（安徽安庆市潜山县）","扬州吴郡富春（浙江杭州市富阳）",
            "冀州中山国毋极（河北石家庄市无极县西、滋水北岸）","扬州庐江郡皖（安徽安庆市潜山县）",
            "东汉陈留郡圉县(今河南开封杞县)人","无法考证",
            "南阳（今河南南阳）人","常山真定（今河北省正定）人",
            "扶风茂陵人(今陕西兴平)","五原郡九原县人（今内蒙古包头九原区）",

            "汝南富陂人","陈留己吾（今河南商丘市宁陵县己吾城村）人",
            "荆州沔南白水（今湖北襄阳）人","吴郡富春（今浙江富阳）人",
            "沛国谯（今安徽省亳州市）人","不详",
            "吴郡富春人","祖籍安平广宗",
            "河内温（今河南温县）人","不详"
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
            "舍身报国的可敬女子，她为了挽救天下黎民，为了推翻权臣董卓的荒淫统治，受王允所托，上演了可歌可泣的连环计（连环美人计），周旋于两个男人之间，成功的离间了董卓和吕布，最终吕布将董卓杀死，结束了董卓专权的黑暗时期。",
            "东汉末年名将。本为刘表部下中郎将，后归刘备，并助刘备攻益州刘璋。建安二十四年（219年），黄忠在定军山一战中阵斩曹操部下名将夏侯渊，升任征西将军，刘备称汉中王后改封后将军，赐关内侯。次年，黄忠病逝。景耀三年（260年），追谥刚侯。黄忠在后世多以勇猛的老将形象出现于各类文学艺术作品中，《三国演义》里，刘备称汉中王后将其封为五虎上将之一，而黄忠的名字在中国也逐渐成为了老当益壮的代名词。",
            "蜀汉名将之一。汉末军阀混战，赵云受本郡推举，率领义从加入白马将军公孙瓒。期间结识了汉室皇亲刘备，但不久之后，赵云因为兄长去世而离开。赵云离开公孙瓒大约七年左右的时间，在邺城与刘备相见，从此追随刘备。 赵云跟随刘备将近三十年，先后参加过博望坡之战、长坂坡之战、江南平定战，独自指挥过入川之战、汉水之战、箕谷之战，都取得了非常好的战果。除了四处征战，赵云还先后以偏将军任桂阳太守，以留营司马留守公安，以翊军将军督江州。 除此，赵云于平定益州时引霍去病故事劝谏刘备将田宅归还百姓，又于关羽张飞被害之后劝谏刘备不要伐吴。赵云死后，刘禅又下令追谥赵云，姜维以“柔贤慈惠曰顺，执事有班曰平，克定祸乱曰平”追谥赵云为顺平侯。",
            "汉末卫尉马腾之子，东汉末年及蜀汉开国名将，汉末群雄之一。早年随父征战，马腾入京后，马超留驻割据三辅。潼关之战被曹操击败后，又割据陇上诸郡。失败后投靠张鲁，又转投刘备。刘备建立蜀汉后，马超官至骠骑将军、斄乡侯、凉州牧。于章武二年十二月病逝(223年1月)，终年47岁，追谥威侯。",
            "先后为丁原、董卓的部将，也曾为袁绍效力，后占据徐州，自成一方势力。于建安三年（198年）在下邳被曹操击败并处死。由于《三国演义》及各种民间艺术的演绎，吕布向来是以“三国第一猛将”的形象存在于人们的心目之中。",


            "少年时依附姊夫邓当，随孙策为将。以胆气称，累封别部司马。孙权统事后，渐受重用，从破黄祖作先登，封横野中郎将。从围曹仁于南郡，并于濡须数御曹军，屡献奇计，累功拜庐江太守。",
            "东汉末年曹操部将。相貌魁梧，膂力过人。公元197年（建安二年），张绣背叛曹操，典韦为保护曹操而独挡叛军，击杀多人，但最终因寡不敌众而战死。典韦是陈留己吾人。他形貌魁梧，膂力过人，有大志气节，性格任侠。",
            "沔阳名士黄承彦之女，诸葛亮之妻，诸葛瞻之母。本名于史无载，“月英”是她在民间传说中的名字。黄承彦以黄月英有才，向诸葛亮推荐，请求配婚，诸葛亮答应后遂与黄月英结为夫妻。相传黄月英黄头发黑皮肤，但知识广博。",
            "孙坚长子，孙权长兄。东汉末年割据江东一带的军阀，汉末群雄之一，三国时期吴国的奠基者之一。三国演义中绰号“小霸王”。为继承父亲孙坚的遗业而屈事袁术，后脱离袁术，统一江东。",
            "出生于东阳武，是曹操与武宣卞皇后所生第三子，生前曾为陈王，去世后谥号“思”，因此又称陈思王。 曹植是三国时期曹魏著名文学家，建安文学的代表人物。其代表作有《洛神赋》、《白马篇》.",
            "刘备之子，母亲是昭烈皇后甘氏，三国时期蜀汉第二位皇帝。幼年时多遭难，幸得大将赵云两次相救，刘备定益州后入蜀，蜀汉建立后被立为太子。于公元223年继位为皇帝，在位四十年。期间拜诸葛亮为相父。",
            "东吴大帝孙权长女，生母步夫人，后母潘皇后，全怿、全吴生母，全绪、全寄继母，朱氏姨母。初嫁周瑜的儿子周循，周循死后，改嫁全琮，因此孙鲁班又称全公主。",
            "曹魏王朝第一位皇后，祖籍安平广宗。有智数，性俭约，魏文帝曹丕的夫人，卢弼《三国志集解》中称其“之足以制魏文可知”。郭氏少年时父母双亡，丧乱流离，29岁时被比她小3岁的曹丕纳为妾，深得宠遇，然而始终没有生下子女。后来曹丕即位魏王，册封郭氏为魏王夫人。曹魏建立，拜为贵嫔，位次皇后。黄初三年九月初九，曹丕在许昌立她为后。郭女王做了四年的皇后，后曹丕病笃驾崩，由太子曹叡继位，郭女王成为皇太后。八年后逝世，谥曰“文德皇后”，葬于魏文帝首阳陵西侧。",
            "三国时期曹魏权臣，西晋王朝的奠基人之一。他是宣帝司马懿与张春华的次子，司马师的弟弟，西晋开国皇帝司马炎的父亲。早年随父抗蜀，多有战功。景初二年，封新城乡侯。正始初，迁洛阳典农中郎将。曹髦时，继兄司马师为大将军。专揽国政，走向代魏。甘露五年，魏帝曹髦死后，立曹奂为帝。景元四年，分兵遣钟会、邓艾、诸葛绪三路伐蜀，灭之。自称晋公。咸熙元年三月丁丑加为晋王。咸熙二年，薨。昭死数月，子司马炎代魏称帝。建晋朝。追尊司马昭为文帝，庙号太祖。",
            "中国东汉末年董卓部下的武将，为董卓帐下都督。公元191年，关东军阀联合讨伐董卓，时任长沙太守的孙坚大破董卓军，华雄在此战中被孙坚一军所杀。明·罗贯中所著历史小说《三国演义》中则对这段历史作了改动，描写华雄英勇无敌，后被刘备义弟关羽所杀，这段被称为“温酒斩华雄”的故事情节也流传于后世。"
    };
    final int []imgId={
            R.mipmap.liubei,R.mipmap.caocao,
            R.mipmap.sunquan,R.mipmap.guanyu,
            R.mipmap.zhangfei,R.mipmap.zhugeliang,
            R.mipmap.zhouyu,R.mipmap.simayi,
            R.mipmap.zhangliao,R.mipmap.ganning,

            R.mipmap.daqiao,R.mipmap.sunshangxiang,
            R.mipmap.zhenji,R.mipmap.xiaoqiao,
            R.mipmap.caiwenji,R.mipmap.diaochan,
            R.mipmap.huangzhong,R.mipmap.zhaoyun,
            R.mipmap.machao,R.mipmap.lvbu,

            R.mipmap.lvmeng,R.mipmap.dianwei,
            R.mipmap.huangyueying,R.mipmap.sunce,
            R.mipmap.caozhi,R.mipmap.liushan,
            R.mipmap.sunluban,R.mipmap.guonvwang,
            R.mipmap.simazhao,R.mipmap.huaxiong
    };

    /*声明放在外面*/
    private List<Map<String,Role>> listItems1 = new ArrayList<>();
    private List<Map<String,Role>> listItems2 = new ArrayList<>();
    private List<Map<String,Object>> simpleListItems2 = Role.getSimpleList(listItems2);
    Map<String,Role> s;



    //切换界面的一些变量两个浮动按钮声明
    private FloatingActionButton fab1 ;
    private FloatingActionButton fab2 ;
    private int whichView;//记录当前在哪一个界面，0为英雄列表，1为麾下

    //广播使用的filter
    String STATICACTION="com.example.midtermproject.STATICACTION";


    //两个adapter
    private CommonAdapter recycleAdapter;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rolelists);


        //商品列表的初始化
        //final List<Map<String,Object>> listItems1 = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            Map<String,Role> tmp = new LinkedHashMap<>();
//            Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],intro[i],imgId[i],false);
//            tmp.put("Role",tmpR);
//            listItems1.add(tmp);
//        }
        initlistItems1(0,10);


        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.rolelists) ;
        constraintLayout.getBackground().mutate().setAlpha(60);


        //eventbus的注册
        EventBus.getDefault().register(this);



        //两个右下角按钮的初始化以及将进入页面初始化为商品列表
        fab1 = (FloatingActionButton) findViewById(R.id.fabSL1);
        fab2 = (FloatingActionButton) findViewById(R.id.fabSL2);
        whichView = 0;
        changeToRecyclerView();


        //发送静态广播推送推荐商品
        BroadcastStatic(STATICACTION);


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToListView();
                whichView=1;
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToRecyclerView();
                whichView=0;
            }
        });
        /*左上角返回按键返回主界面*/
        ImageView backBtnInList=(ImageView) findViewById(R.id.backBtnInList);
        backBtnInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(RoleLists.this,MainActivity.class);
                //回到RoleLists
                setResult(1,intent);
                finish();
            }
        });


        /*右上角加号从已有数据寻找人物列表*/
        final AlertDialog.Builder alertDialogRole = new AlertDialog.Builder(this);
        alertDialogRole.setTitle("内置数据中人物")
                .setItems(name, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (name[i].equals(name[i])) {
                            addTolistItems1(i);
                            Toast.makeText(getApplication(), "添加新人物"+name[i], Toast.LENGTH_SHORT).show();
                            recycleAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplication(), "你点击了取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                .create();

        /*右上角加号提示增加方法*/
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final String mitems[] = {"从内置数据中添加", "自行设置"};
        alertDialog.setTitle("添加人物方式")
                .setItems(mitems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mitems[i].equals("从内置数据中添加")) {
                            alertDialogRole.show();

                        } else if (mitems[i].equals("自行设置")) {
                            Role newRole= new Role();
                            Bundle bundle = newRole.putInBundle();
                            bundle.putInt("whichView",whichView);
                            bundle.putBoolean("isEditable",true);
                            Intent intent=new Intent();
                            intent.putExtras(bundle);
                            intent.setClass(RoleLists.this,ItemInfo.class);
                            startActivityForResult(intent,1);
                        }
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplication(), "你点击了取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                .create();



        /*右上角加号按键添加新角色*/
        ImageView addBtnInList=(ImageView) findViewById(R.id.addBtnInList);
        addBtnInList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        //RecyclerView实现物品清单begin/////////////////
        final RecyclerView mRecyclerView;
        //final CommonAdapter recycleAdapter;
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
                bundle.putInt("whichView",whichView);
                bundle.putBoolean("isEditable",false);
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
                bundle.putInt("whichView",whichView);
                bundle.putBoolean("isEditable",false);
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
            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO onQueryTextSubmit
                onQueryTextAct(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                onQueryTextAct(newText);
                return false;
            }
            public void onQueryTextAct(String newText){
                final List<Map<String,Role>> searchlistItems=new ArrayList<>();
                //TODO onQueryTextChange
                if(TextUtils.isEmpty(newText)){
                    if(whichView==0){
                        changeToRecyclerView();
                    }
                    else{
                        changeToListView();
                    }
                }
                else{//!TextUtils.isEmpty(newText)
                    if(whichView==0){
                        for(int i=0;i<listItems1.size();i++){
                            String nameText=listItems1.get(i).get("Role").getname();
                            String countryText=listItems1.get(i).get("Role").getcountry();
                            if(nameText.indexOf(newText)==-1&&countryText.indexOf(newText)==-1){

                            }
                            else{
                                searchlistItems.add(listItems1.get(i));
                            }
                        }
                    }
                    else{//whichView==1
                        for(int i=0;i<listItems2.size();i++){
                            String nameText=listItems2.get(i).get("Role").getname();
                            String countryText=listItems2.get(i).get("Role").getcountry();
                            if(nameText.indexOf(newText)==-1&&countryText.indexOf(newText)==-1){
                            }
                            else{
                                searchlistItems.add(listItems2.get(i));
                            }
                        }
                    }//whichView==1
                    ListView SearchListView = (ListView) findViewById(R.id.SearchListView);
                    final List<Map<String,Object>>simpleListtmp = Role.getSimpleList(searchlistItems);

                    final SimpleAdapter simpleAdapterTmp = new SimpleAdapter(
                            RoleLists.this,simpleListtmp,R.layout.listviewitem,
                            new String[]{"country","name"},new int []{R.id.itemCountryInL,R.id.itemNameInL});
                    SearchListView.setAdapter(simpleAdapterTmp);
                    SearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //if(position==0) return ;
                            Role tmpR=searchlistItems.get(position).get("Role");
                            Bundle bundle = tmpR.putInBundle();
                            bundle.putInt("whichView",whichView);
                            bundle.putBoolean("isEditable",false);
                            Intent intent=new Intent();
                            intent.putExtras(bundle);
                            intent.setClass(RoleLists.this,ItemInfo.class);
                            startActivityForResult(intent,1);
                        }
                    });
                    SearchListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent,final View view,final int position, long id) {
                            Role tmpR=searchlistItems.get(position).get("Role");
                            final String name=tmpR.getname();

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
                                            if(whichView==0){
                                                int tmpposition=checkStringInList(name,listItems1);
                                                if(tmpposition!=-1){
                                                    listItems1.remove(tmpposition);
                                                    recycleAdapter.notifyItemRemoved(tmpposition);
                                                    recycleAdapter.notifyItemChanged(tmpposition);
                                                    recycleAdapter.notifyDataSetChanged();

                                                }
                                                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "已从列表移除", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }
                                            else {
                                                int tmpposition=checkStringInList(name,listItems2);
                                                if(tmpposition!=-1){
                                                    listItems2.remove(tmpposition);
                                                    simpleListItems2.remove(tmpposition);
                                                    simpleAdapter.notifyDataSetChanged();
                                                }
                                                Snackbar.make( view,"英雄 " + s.get("Role").getname() + "已从麾下逐出", Snackbar.LENGTH_LONG)
                                                        .setAction("Action", null).show();
                                            }
                                            searchlistItems.remove(position);
                                            simpleListtmp.clear();
                                            simpleListtmp.addAll(Role.getSimpleList(searchlistItems));
                                            simpleAdapterTmp.notifyDataSetChanged();
                                        }
                                    }
                            ).create();
                            alterDialog.show();
                            return true;
                        }
                    });
                    changeToSearchListView();
                }//!TextUtils.isEmpty(newText)
            }
        });
        //搜索框实现////////////////////////////////////////////////
    }

    /*回调函数，当从子界面回到这个父界面时调用，如果回传数据不为空也即是按下加入购物车则更新购物车列表*/
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==1){
            if(resultCode==1){
                Bundle bundle=intent.getExtras();
                if(bundle!=null){
                    Role role=new Role(bundle);
                    Map<String,Role> tmp = new LinkedHashMap<>();
                    tmp.put("Role",role);

                    //每次回传都更新相应角色的内容
                    int position=checkNameInList(role.getname(),listItems1);
                    if(position>-1){
                        listItems1.set(position,tmp);
                    }
                    else{
                        listItems1.add(tmp);
                        Toast.makeText(getApplicationContext(),"添加新人物"+role.getname() , Toast.LENGTH_SHORT).show();
                    }
                    recycleAdapter.notifyDataSetChanged();

                    //切换页面
                    if(bundle.getInt("whichView")==0){
                        changeToRecyclerView();
                        whichView=0;
                    }
                    else{
                        changeToListView();
                        whichView=1;
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

    public void upadteList(){
        //TODO upadteList
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //向初始的人物列表中加入从left到right-1 的所有人物
    public void initlistItems1(int left,int right){
        //商品列表的初始化
        //final List<Map<String,Object>> listItems1 = new ArrayList<>();
        if(left<0||left>=30) left=0;
        if(right>30)right=30;
        for(int i=left;i<right;i++){
            addTolistItems1(i);
        }
    }

    //向初始的人物列表中加入从第i个人物
    public void addTolistItems1(int i){
        Map<String,Role> tmp = new LinkedHashMap<>();
        Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],intro[i],imgId[i],false);
        tmp.put("Role",tmpR);
        listItems1.add(tmp);
    }

    //切换当前的界面到RecyclerView的界面
    public void changeToRecyclerView(){
        whichView=0;
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.VISIBLE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.GONE);
        ListView sliLayout =(ListView) findViewById(R.id.SearchListView);
        sliLayout.setVisibility(View.GONE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.VISIBLE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.GONE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.GONE);
        ImageButton imageButton=(ImageButton) findViewById(R.id.addBtnInList);
        imageButton.setVisibility(View.VISIBLE);
    }
    //切换当前的界面到ListView的界面
    public void changeToListView(){
        whichView=1;
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.GONE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.VISIBLE);
        ListView sliLayout =(ListView) findViewById(R.id.SearchListView);
        sliLayout.setVisibility(View.GONE);
        TextView tv1 = (TextView) findViewById(R.id.head1) ;
        tv1.setVisibility(View.GONE);
        TextView tv2 = (TextView) findViewById(R.id.head2) ;
        tv2.setVisibility(View.VISIBLE);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.VISIBLE);
        ImageButton imageButton=(ImageButton) findViewById(R.id.addBtnInList);
        imageButton.setVisibility(View.GONE);
    }
    //更改当前的list界面到查询的SearchListView的界面
    public void changeToSearchListView(){
        RecyclerView reLayout =(RecyclerView) findViewById(R.id.RecyclerView);
        reLayout.setVisibility(View.GONE);
        ListView liLayout =(ListView) findViewById(R.id.ListView);
        liLayout.setVisibility(View.GONE);
        ListView sliLayout =(ListView) findViewById(R.id.SearchListView);
        sliLayout.setVisibility(View.VISIBLE);
    }
    //查找list是否包含名字或国家与s有关的人物并返回位置
    public int checkStringInList(String s,List<Map<String,Role>> list){
        for(int i=0;i<list.size();i++){
            String nameText=list.get(i).get("Role").getname();
            String countryText=list.get(i).get("Role").getcountry();
            if(nameText.indexOf(s)==-1&&countryText.indexOf(s)==-1){
            }
            else{
                return i;
            }
        }
        return -1;
    }
    //查找list是否包含名字相同人物并返回位置
    public int checkNameInList(String s,List<Map<String,Role>> list){
        for(int i=0;i<list.size();i++){
            String nameText=list.get(i).get("Role").getname();
            String countryText=list.get(i).get("Role").getcountry();
            if(nameText.equals(s)){
                return i;
            }
        }
        return -1;
    }
}
