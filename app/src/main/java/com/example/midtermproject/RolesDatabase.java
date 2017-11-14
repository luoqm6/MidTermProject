package com.example.midtermproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Created by qingming on 2017/11/13.
 */

public class RolesDatabase implements Serializable {

    /*所有的人物信息*/
    final String []name={"刘备","曹操",
            "孙权","关羽",
            "张飞","诸葛亮",
            "周瑜","司马懿",
            "张辽","甘宁"};
    final String []nickname={"字玄德","字孟德",
            "字仲谋","字云长",
            "字益德","字孔明",
            "字公瑾","字仲达",
            "字文远","字兴霸"};
    final String []country={"蜀","魏","吴","蜀","蜀","蜀","吴","魏","魏","吴"};
    final String []period={"161年－223年6月10日","155年－220年3月15日",
            "182年－252年5月21日","？－220年",
            "？－221年","181年-234年10月8日",
            "175年-210年","179年—251年9月7日",
            "169年－222年","？—220年"};
    final String []sex={"男","男","男","男","男","男","男","男","男","男"};
    final String []hometown={"幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人"};
    final String []intro={"幽州涿郡涿县（今河北省涿州市）人", "沛国谯县（今安徽亳州）人",
            "吴郡富春（今浙江杭州富阳区）人", "河东郡解县（今山西运城）人",
            "幽州涿郡（今河北省保定市涿州市）人", "徐州琅琊阳都（今山东临沂市沂南县）人",
            "庐江舒（今合肥庐江舒县）人", "河内郡温县孝敬里（今河南省焦作市温县）人",
            "雁门马邑（今山西朔州）人", "巴郡临江（今重庆忠县）人"};
    final int []imgId={R.mipmap.liubei,R.mipmap.caocao,
            R.mipmap.sunquan,R.mipmap.guanyu,
            R.mipmap.zhangfei,R.mipmap.zhugeliang,
            R.mipmap.zhouyu,R.mipmap.simayi,
            R.mipmap.zhangliao,R.mipmap.ganning};

    private List<Map<String,Role>> rolesindb;
    private List<Map<String,Role>> rolesindb1;


    public RolesDatabase(){
        resetDatabase();
    }
    public void resetDatabase(){
        for(int i=0;i<name.length;i++){
            Map<String,Role> tmp = new LinkedHashMap<>();
            Role tmpR = new Role(country[i],name[i],nickname[i],period[i],sex[i],hometown[i],intro[i],imgId[i],false);
            tmp.put("Role",tmpR);
            rolesindb.add(tmp);
        }
    }
    public void addarole(Role role){
        Map<String,Role> tmp = new LinkedHashMap<>();
        tmp.put("Role",role);
        rolesindb.add(tmp);
    }
    public List<Map<String,Role>> getrolesindb(){
        return rolesindb;
    }
    public List<Map<String,Role>> getrolesindb(int num){
        List<Map<String,Role>> tmpL = new ArrayList<>();;
        for(int i=0;i<num;i++){
            tmpL.add(rolesindb.get(i));
        }
        return tmpL;
    }
    public List<Map<String,Role>> getrolesindb1(){
        return rolesindb1;
    }

}
