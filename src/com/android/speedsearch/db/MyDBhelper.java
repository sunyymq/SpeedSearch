package com.android.speedsearch.db;

import java.util.ArrayList;
import java.util.List;

import com.android.speedsearch.util.ExpressBaseInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBhelper extends SQLiteOpenHelper{
  private static final String DATABASE_NAME = "codestiny_db";
  private static final int DATABASE_VERSION = 1;
  public static final String FIELD_date = "date";
  public static final String FIELD_id = "_id";
  public static final String FIELD_name = "name";
  public static final String FIELD_number = "number";
  private static final String TABLE_NAME = "user_table";
  private final SQLiteDatabase database = getWritableDatabase();
  private static MyDBhelper instance;
  
  public MyDBhelper(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public void delete(){
    getWritableDatabase().delete(TABLE_NAME, null, null);
  }

  public void delete(String date){
    getWritableDatabase().delete(TABLE_NAME, "date = ?", new String[] { date });
  }

  public long insert(String date, String name, String number){
    SQLiteDatabase db = getWritableDatabase();
    ContentValues cv = new ContentValues();
    cv.put(FIELD_date, date);
    cv.put(FIELD_name, name);
    cv.put(FIELD_number, number);
    return db.insert(TABLE_NAME, null, cv);
  }

  public void onCreate(SQLiteDatabase db){
	  db.execSQL("CREATE TABLE user_table (_id INTEGER primary key autoincrement,  date text, name text, number text)");
	  createExpressCompanyTable(db);
  }

  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	db.execSQL("DROP TABLE IF EXISTS user_table");
    onCreate(db);
  }

  public Cursor select(String[] column, String where, String[] whereValue){
    return getReadableDatabase().query(TABLE_NAME, column, where, whereValue, null, null, "_id desc");
  }

  public void update(int id, String date, String name, String number){
    SQLiteDatabase db = getWritableDatabase();
    String where = "_id = ?";
    String[] whereValue = new String[1];
    whereValue[0] = Integer.toString(id);
    ContentValues cv = new ContentValues();
    cv.put(FIELD_date, date);
    cv.put(FIELD_name, name);
    cv.put(FIELD_number, number);
    db.update(TABLE_NAME, cv, where, whereValue);
  }
  
  private List<ExpressBaseInfo> getExpressList(String sql, boolean needPinyin){
    ArrayList<ExpressBaseInfo> list = new ArrayList<ExpressBaseInfo>();
    Cursor cursor = null;
    if (database != null)
    	cursor = database.rawQuery(sql, null);
    while (cursor.moveToNext()) {
        ExpressBaseInfo info = new ExpressBaseInfo();
        info.setCompanyName(cursor.getString(0));
        info.setImageUrl(cursor.getString(1), 2, true);
        if (needPinyin)
        	info.setPinyin(cursor.getString(2));
        list.add(info);
	}
    cursor.close();
    return list;
  }

  public static MyDBhelper getInstance(Context paramContext){
    try{
      if (instance == null)
        instance = new MyDBhelper(paramContext);
      	return instance;
    }finally{
    }
  }
  
  public List<ExpressBaseInfo> getTopExpress(){
    return getExpressList("select _name, _img from express where _type = 1 and _valid = 1 order by _index desc", false);
  }

  public List<ExpressBaseInfo> getTopExpress(String keywords){
    return getExpressList("select _name, _img from express where _type = 1 and _valid = 1 and (_name like '%" + keywords + "%' or _pinyin like '" + keywords + "%') order by _index asc", false);
  }
  
  public List<ExpressBaseInfo> getAllExpress(){
    return getExpressList("select _name, _img, _pinyin from express where _valid = 1 order by _pinyin asc", true);
  }

  public List<ExpressBaseInfo> getAllExpress(String keywords){
    return getExpressList("select _name, _img, _pinyin from express where _valid = 1 and (_name like '%" + keywords + "%' or _pinyin like '" + keywords + "%') order by _pinyin asc", true);
  }
  private void createExpressCompanyTable(SQLiteDatabase db){
    if (db == null){
    	return;
    }
    db.execSQL("DROP TABLE IF EXISTS express");
    db.execSQL("CREATE TABLE IF NOT EXISTS express (_name varchar, _pinyin varchar, _img varchar, _valid integer, _index long, _type integer)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('圆通速递', 'YuanTongSuDi', '/pac_images/yto.jpg', 1, 2, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('韵达快运', 'YunDaKuaiYun', '/pac_images/yunda.jpg', 1, 5, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('EMS', 'EMS', '/pac_images/ems.jpg', 1, 1, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('顺丰速运', 'ShunFengSuYun', '/pac_images/sf.jpg', 1, 0, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('中通速递', 'ZhongTongSuDi', '/pac_images/zto.jpg', 1, 6, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('申通快递', 'ShenTongKuaiDi', '/pac_images/sto.jpg', 1, 3, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('百世汇通', 'BaiShiHuiTong', '/pac_images/ht.jpg', 1, 8, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('速尔快递', 'SuErKuaiDi', '/pac_images/suer.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('天天快递', 'TianTianKuaiDi', '/pac_images/tt.jpg', 1, 7, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('优速快递', 'YouSuKuaiDi', '/pac_images/uc.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('FedEx', 'FedEx', '/pac_images/fedex.jpg', 1, 9, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('快捷速递', 'KuaiJieSuDi', '/pac_images/kjsd.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('全峰快递', 'QuanFengKuaiDi', '/pac_images/qfkd.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('龙邦速递', 'LongBangSuDi', '/pac_images/longbang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('宅急送', 'ZhaiJiSong', '/pac_images/zjs.jpg', 1, 4, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('圣安物流', 'ShengAnWuLiu', '/pac_images/shengan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('明亮物流', 'MingLiangWuLiu', '/pac_images/mingliang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('新邦物流', 'XinBangWuLiu', '/pac_images/xinbang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('闽盛快递', 'MinShengKuaiDi', '/pac_images/minsheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('易高物流', 'YiGaoWuLiu', '/pac_images/yigaowuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('云物通', 'YunWuTong', '/pac_images/yunwutong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('跨越速运', 'KuaYueSuYun', '/pac_images/kuayuesuyun.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('AAE中国', 'AAEZhongGuo', '/pac_images/aae.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('安信达', 'AnXinDa', '/pac_images/anxindakuaixi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('安捷快递', 'AnJieKuaiDi', '/pac_images/anjie.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('百福东方', 'BaiFuDongFang', '/pac_images/baifudongfang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('BHT中国', 'BHTZhongGuo', '/pac_images/bht.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('创一快递', 'ChuangYiKuaiDi', '/pac_images/chuangyi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('长通物流', 'ChangTongWuLiu', '/pac_images/changtong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('城市之星', 'ChengShiZhiXing', '/pac_images/chengshizhixing.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('城联速递', 'ChengLianSuDi', '/pac_images/chenglian.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('传志快运', 'ChuanZhiKuaiYun', '/pac_images/chuanzhi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('DHL中国', 'DHLZhongGuo', '/pac_images/dhl.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('大田物流', 'DaTianWuLiu', '/pac_images/datianwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('D速快递', 'DSuKuaiDi', '/pac_images/dsukuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('德邦物流', 'DeBangWuLiu', '/pac_images/debangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('东方快递', 'DongFangKuaiDi', '/pac_images/dongfang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('DPEX中国', 'DPEXZhongGuo', '/pac_images/dpex.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('递四方', 'DiSiFang', '/pac_images/disifang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('飞邦物流', 'FeiBangWuLiu', '/pac_images/feibang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('飞康达', 'FeiKangDa', '/pac_images/feikangda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('飞豹快递', 'FeiBaoKuaiDi', '/pac_images/feibao.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('丰达快递', 'FengDaKuaiDi', '/pac_images/fengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('飞远传媒', 'FeiYuanChuanMei', '/pac_images/feiyuan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('凤凰快递', 'FengHuangKuaiDi', '/pac_images/fenghuang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('广东邮政', 'GuangDongYouZheng', '/pac_images/guangdongyouzhengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('恒路物流', 'HengLuWuLiu', '/pac_images/hengluwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('海盟物流', 'HaiMengWuLiu', '/pac_images/haimeng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('海红网送', 'HaiHongWangSong', '/pac_images/haihong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('海外环球', 'HaiWaiHuanQiu', '/pac_images/haiwaihuanqiu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('急先达', 'JiXianDa', '/pac_images/jixianda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('加运美', 'JaiYunMei', '/pac_images/jiayunmeiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('嘉里大通', 'JiaLiDaTong', '/pac_images/jialidatong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('佳怡物流', 'JiaYiWuLiu', '/pac_images/jiayiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('佳吉快运', 'JiaJiKuaiYun', '/pac_images/jiajiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('西安聚信', 'XiAnJuXin', '/pac_images/juxin.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('晋越快递', 'JinYueKuaiDi', '/pac_images/jinyuekuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('金大物流', 'JinDaWuLiu', '/pac_images/jinda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('京广速递', 'JinGuangSuDi', '/pac_images/jinguangsudikuaijian.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('康力物流', 'KangLiWuLiu', '/pac_images/kangli.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('联昊通', 'LianHaoTong', '/pac_images/lianhaowuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('立即送', 'LiJiSong', '/pac_images/lijisong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('乐捷递', 'LeJieDi', '/pac_images/lejiedi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('蓝镖快递', 'LanBiaoKuaiDi', '/pac_images/lanbiaokuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('门对门', 'MenDuiMen', '/pac_images/menduimen.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('能达快递', 'NengDaKuaiDi', '/pac_images/nengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('如风达', 'RuFengDa', '/pac_images/rufengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('盛丰物流', 'ShengFengWuLiu', '/pac_images/shengfengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('盛辉物流', 'ShengHuiWuLiu', '/pac_images/shenghuiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('上大物流', 'ShangDaWuLiu', '/pac_images/shangda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('三态速递', 'SanTaiSuDi', '/pac_images/santaisudi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('速来达', 'SuLaiDa', '/pac_images/sulaida.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('赛澳递', 'SaiAoDi', '/pac_images/saiaodi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('天地华宇', 'TianDiHuaYu', '/pac_images/tiandihuayu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('通和天下', 'TongHeTianXia', '/pac_images/tonghetianxia.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('腾讯达', 'TengXunDa', '/pac_images/tengxunda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('通成物流', 'TongChengWuLiu', '/pac_images/tongcheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('TNT中国', 'TongChengWuLiu', '/pac_images/tnt.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('OCS中外运', 'OCSZhongWaiYun', '/pac_images/ouxiaisi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('平安达', 'PingAnDa', '/pac_images/pinganda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('配思速递', 'PeiSiSuDi', '/pac_images/peisi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('百世全际通', 'BaiShiQuanJiTong', '/pac_images/quanjitong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('全日通', 'QuanRiTong', '/pac_images/quanritongkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('全晨快递', 'QuanChenKuaiDi', '/pac_images/quanchenkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('全一快递', 'QuanYiKuaiDi', '/pac_images/quanyikuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('微特派', 'WeiTePai', '/pac_images/weitepai.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('伍圆速递', 'WuYuanSuDi', '/pac_images/wuyuan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('伟邦速递', 'WeiBangSuDi', '/pac_images/weibang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('万家物流', 'WanJiaWuLiu', '/pac_images/wanjiawuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('信丰物流', 'XinFengWuLiu', '/pac_images/xinfengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('鑫飞鸿', 'XinFeiHong', '/pac_images/xinhongyukuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('远成物流', 'YuanChengWuLiu', '/pac_images/yuanchengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('一邦速递', 'YiBangSuDi', '/pac_images/yibangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('元智捷诚', 'YuanZhiJieCheng', '/pac_images/yuanzhijiecheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('源安达', 'YuanAnDa', '/pac_images/yuananda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('亚风速递', 'YaFengSuDi', '/pac_images/yafengsudi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('源伟丰', 'YuanWeiFeng', '/pac_images/yuanweifeng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('原飞航', 'YuanFeiHang', '/pac_images/yuanfeihangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('运通快递', 'YunTongKuaiDi', '/pac_images/yuntongkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('银捷速递', 'YinJieSuDi', '/pac_images/yinjie.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('越丰物流', 'YueFengWuLiu', '/pac_images/yuefengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('一统飞鸿', 'YiTongFeiHong', '/pac_images/yitongfeihong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('忠信达', 'ZhongXinDa', '/pac_images/zhongxinda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('中铁快运', 'ZhongTieKuaiYun', '/pac_images/zhongtiewuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('中邮物流', 'ZhongYouWuLiu', '/pac_images/zhongyouwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('芝麻开门', 'ZhiMaKaiMen', '/pac_images/zhimakaimen.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('其他', '####', '/pac_images/other.jpg', 1, 1000, 3)");
  }
}