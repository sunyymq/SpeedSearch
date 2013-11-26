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
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Բͨ�ٵ�', 'YuanTongSuDi', '/pac_images/yto.jpg', 1, 2, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ϴ����', 'YunDaKuaiYun', '/pac_images/yunda.jpg', 1, 5, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('EMS', 'EMS', '/pac_images/ems.jpg', 1, 1, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('˳������', 'ShunFengSuYun', '/pac_images/sf.jpg', 1, 0, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ͨ�ٵ�', 'ZhongTongSuDi', '/pac_images/zto.jpg', 1, 6, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ͨ���', 'ShenTongKuaiDi', '/pac_images/sto.jpg', 1, 3, 1)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('������ͨ', 'BaiShiHuiTong', '/pac_images/ht.jpg', 1, 8, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ٶ����', 'SuErKuaiDi', '/pac_images/suer.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('������', 'TianTianKuaiDi', '/pac_images/tt.jpg', 1, 7, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ٿ��', 'YouSuKuaiDi', '/pac_images/uc.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('FedEx', 'FedEx', '/pac_images/fedex.jpg', 1, 9, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����ٵ�', 'KuaiJieSuDi', '/pac_images/kjsd.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ȫ����', 'QuanFengKuaiDi', '/pac_images/qfkd.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����ٵ�', 'LongBangSuDi', '/pac_images/longbang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('լ����', 'ZhaiJiSong', '/pac_images/zjs.jpg', 1, 4, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ʥ������', 'ShengAnWuLiu', '/pac_images/shengan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'MingLiangWuLiu', '/pac_images/mingliang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�°�����', 'XinBangWuLiu', '/pac_images/xinbang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ʢ���', 'MinShengKuaiDi', '/pac_images/minsheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�׸�����', 'YiGaoWuLiu', '/pac_images/yigaowuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����ͨ', 'YunWuTong', '/pac_images/yunwutong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��Խ����', 'KuaYueSuYun', '/pac_images/kuayuesuyun.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('AAE�й�', 'AAEZhongGuo', '/pac_images/aae.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���Ŵ�', 'AnXinDa', '/pac_images/anxindakuaixi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ݿ��', 'AnJieKuaiDi', '/pac_images/anjie.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ٸ�����', 'BaiFuDongFang', '/pac_images/baifudongfang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('BHT�й�', 'BHTZhongGuo', '/pac_images/bht.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��һ���', 'ChuangYiKuaiDi', '/pac_images/chuangyi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ͨ����', 'ChangTongWuLiu', '/pac_images/changtong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����֮��', 'ChengShiZhiXing', '/pac_images/chengshizhixing.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����ٵ�', 'ChengLianSuDi', '/pac_images/chenglian.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��־����', 'ChuanZhiKuaiYun', '/pac_images/chuanzhi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('DHL�й�', 'DHLZhongGuo', '/pac_images/dhl.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'DaTianWuLiu', '/pac_images/datianwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('D�ٿ��', 'DSuKuaiDi', '/pac_images/dsukuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�°�����', 'DeBangWuLiu', '/pac_images/debangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�������', 'DongFangKuaiDi', '/pac_images/dongfang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('DPEX�й�', 'DPEXZhongGuo', '/pac_images/dpex.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ķ�', 'DiSiFang', '/pac_images/disifang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ɰ�����', 'FeiBangWuLiu', '/pac_images/feibang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ɿ���', 'FeiKangDa', '/pac_images/feikangda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ɱ����', 'FeiBaoKuaiDi', '/pac_images/feibao.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����', 'FengDaKuaiDi', '/pac_images/fengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��Զ��ý', 'FeiYuanChuanMei', '/pac_images/feiyuan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��˿��', 'FengHuangKuaiDi', '/pac_images/fenghuang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�㶫����', 'GuangDongYouZheng', '/pac_images/guangdongyouzhengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��·����', 'HengLuWuLiu', '/pac_images/hengluwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'HaiMengWuLiu', '/pac_images/haimeng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'HaiHongWangSong', '/pac_images/haihong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���⻷��', 'HaiWaiHuanQiu', '/pac_images/haiwaihuanqiu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ȴ�', 'JiXianDa', '/pac_images/jixianda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('������', 'JaiYunMei', '/pac_images/jiayunmeiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����ͨ', 'JiaLiDaTong', '/pac_images/jialidatong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'JiaYiWuLiu', '/pac_images/jiayiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�Ѽ�����', 'JiaJiKuaiYun', '/pac_images/jiajiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'XiAnJuXin', '/pac_images/juxin.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��Խ���', 'JinYueKuaiDi', '/pac_images/jinyuekuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�������', 'JinDaWuLiu', '/pac_images/jinda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����ٵ�', 'JinGuangSuDi', '/pac_images/jinguangsudikuaijian.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'KangLiWuLiu', '/pac_images/kangli.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ͨ', 'LianHaoTong', '/pac_images/lianhaowuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('������', 'LiJiSong', '/pac_images/lijisong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ֽݵ�', 'LeJieDi', '/pac_images/lejiedi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ڿ��', 'LanBiaoKuaiDi', '/pac_images/lanbiaokuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�Ŷ���', 'MenDuiMen', '/pac_images/menduimen.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ܴ���', 'NengDaKuaiDi', '/pac_images/nengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����', 'RuFengDa', '/pac_images/rufengda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ʢ������', 'ShengFengWuLiu', '/pac_images/shengfengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ʢ������', 'ShengHuiWuLiu', '/pac_images/shenghuiwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ϴ�����', 'ShangDaWuLiu', '/pac_images/shangda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��̬�ٵ�', 'SanTaiSuDi', '/pac_images/santaisudi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('������', 'SuLaiDa', '/pac_images/sulaida.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���ĵ�', 'SaiAoDi', '/pac_images/saiaodi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ػ���', 'TianDiHuaYu', '/pac_images/tiandihuayu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ͨ������', 'TongHeTianXia', '/pac_images/tonghetianxia.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��Ѷ��', 'TengXunDa', '/pac_images/tengxunda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ͨ������', 'TongChengWuLiu', '/pac_images/tongcheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('TNT�й�', 'TongChengWuLiu', '/pac_images/tnt.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('OCS������', 'OCSZhongWaiYun', '/pac_images/ouxiaisi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ƽ����', 'PingAnDa', '/pac_images/pinganda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��˼�ٵ�', 'PeiSiSuDi', '/pac_images/peisi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����ȫ��ͨ', 'BaiShiQuanJiTong', '/pac_images/quanjitong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ȫ��ͨ', 'QuanRiTong', '/pac_images/quanritongkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ȫ�����', 'QuanChenKuaiDi', '/pac_images/quanchenkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ȫһ���', 'QuanYiKuaiDi', '/pac_images/quanyikuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('΢����', 'WeiTePai', '/pac_images/weitepai.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��Բ�ٵ�', 'WuYuanSuDi', '/pac_images/wuyuan.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ΰ���ٵ�', 'WeiBangSuDi', '/pac_images/weibang.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�������', 'WanJiaWuLiu', '/pac_images/wanjiawuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ŷ�����', 'XinFengWuLiu', '/pac_images/xinfengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�ηɺ�', 'XinFeiHong', '/pac_images/xinhongyukuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Զ������', 'YuanChengWuLiu', '/pac_images/yuanchengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('һ���ٵ�', 'YiBangSuDi', '/pac_images/yibangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Ԫ�ǽݳ�', 'YuanZhiJieCheng', '/pac_images/yuanzhijiecheng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Դ����', 'YuanAnDa', '/pac_images/yuananda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�Ƿ��ٵ�', 'YaFengSuDi', '/pac_images/yafengsudi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Դΰ��', 'YuanWeiFeng', '/pac_images/yuanweifeng.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('ԭ�ɺ�', 'YuanFeiHang', '/pac_images/yuanfeihangwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��ͨ���', 'YunTongKuaiDi', '/pac_images/yuntongkuaidi.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('�����ٵ�', 'YinJieSuDi', '/pac_images/yinjie.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('Խ������', 'YueFengWuLiu', '/pac_images/yuefengwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('һͳ�ɺ�', 'YiTongFeiHong', '/pac_images/yitongfeihong.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('���Ŵ�', 'ZhongXinDa', '/pac_images/zhongxinda.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'ZhongTieKuaiYun', '/pac_images/zhongtiewuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('��������', 'ZhongYouWuLiu', '/pac_images/zhongyouwuliu.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('֥�鿪��', 'ZhiMaKaiMen', '/pac_images/zhimakaimen.jpg', 1, -1, 2)");
    db.execSQL("INSERT INTO express (_name, _pinyin, _img, _valid, _index, _type) VALUES ('����', '####', '/pac_images/other.jpg', 1, 1000, 3)");
  }
}