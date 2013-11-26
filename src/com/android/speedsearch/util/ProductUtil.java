package com.android.speedsearch.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
public final class ProductUtil{
	  private String SearchResulttotalCount;
	  private String SearchResultfussyCurrent;
	  private String SearchResultTitle;
	  private String SearchResultFirmname;
	  private brandtitle SearchResultbrand = new brandtitle();
	  private title SearchResulttitle = new title();
	  private List<productdes> SearchResult = new ArrayList<productdes>();
	  private productdes SearchResultproduct = new productdes();
	  public ProductUtil(InputStream paramInputStream){
		  parseJson(new InputStreamReader(paramInputStream));
	  }
	  
	  private void parseJson(Reader paramReader){
	    try{
	    	JsonReader jasonReader = new JsonReader(paramReader);
	    	jasonReader.beginObject();
	      while(jasonReader.hasNext()){
	    	 String strtag;
	        strtag = jasonReader.nextName();
	        if (strtag.equals("total_cnt")){
	        	SearchResulttotalCount = jasonReader.nextString();
	        }else if (strtag.equals("fussy_search")){
	        	SearchResultfussyCurrent = jasonReader.nextString();
	        }else if (strtag.equals("title_by_scanner")){
	        	SearchResultTitle = jasonReader.nextString();
	        }else if (strtag.equals("firm_name")){
	        	SearchResultFirmname = jasonReader.nextString();
	        }else if (strtag.equals("brand")){
	        	jasonReader.beginArray();
	          if (SearchResultbrand.gettitle().size() == 0)
	        	  SearchResultbrand.adddtitle("全部");
	          if (!jasonReader.hasNext()){
	        	  jasonReader.endArray();
	          }else{
	        	  while (jasonReader.hasNext()) {
		        	  jasonReader.beginObject();
			            while (jasonReader.hasNext()){
			            	String brandtag = jasonReader.nextName();
			              if (brandtag.equals("brand_name"))
			            	  SearchResultbrand.adddtitle(jasonReader.nextString());
			              else
			            	  jasonReader.skipValue();
			            }
			            jasonReader.endObject();
				}
	            jasonReader.endArray();
	          }
	        }else if (strtag.equals("category")){
	        	jasonReader.beginArray();
	          if (SearchResultbrand.b().size() == 0){
	        	  title locale = new title();
	        	  locale.Setcategoryid("");
	        	  locale.Setcategoryname("全部");
	        	  SearchResultbrand.addtitle(locale);
	          }
	          if (!jasonReader.hasNext()){
	        	  jasonReader.endArray();
	          }else{
	        	  SearchResulttitle = new title();
	        	  while (jasonReader.hasNext()) {
		        	  jasonReader.beginObject();
			            while (jasonReader.hasNext()){
			              String str3 = jasonReader.nextName();
			              if (str3.equals("category_id"))
			            	  SearchResulttitle.Setcategoryid(jasonReader.nextString());
			              else if (str3.equals("name"))
			            	  SearchResulttitle.Setcategoryname(jasonReader.nextString());
			              else {
			            	  jasonReader.skipValue();
			              }
			            }
			            jasonReader.endObject();
			            SearchResultbrand.addtitle(SearchResulttitle);
				}
	            jasonReader.endArray();
	          }
	        }else if (strtag.equals("product")){
	        	jasonReader.beginArray();
	          if (!jasonReader.hasNext()){
	        	  jasonReader.endArray();
	          }else{
	            SearchResultproduct = new productdes();
	            while(jasonReader.hasNext()){
	            jasonReader.beginObject();
	            while (jasonReader.hasNext()){
	              String str2 = jasonReader.nextName();
	              if (str2.equals("site_id"))
	            	  SearchResultproduct.Setsiteid(jasonReader.nextString());
	              else if (str2.equals("url_crc"))
	            	  SearchResultproduct.Seturlcrc(jasonReader.nextString());
	              else if (str2.equals("avg_score"))
	            	  SearchResultproduct.Setavgscore(jasonReader.nextString());
	              else if (str2.equals("min_price"))
	            	  SearchResultproduct.Setminprice(jasonReader.nextString());
	              else if (str2.equals("max_price"))
	            	  SearchResultproduct.Setmaxprice(jasonReader.nextString());
	              else if (str2.equals("site_cnt_onsale"))
	            	  SearchResultproduct.Setsitecntonsale(jasonReader.nextString());
	              else if (str2.equals("site_name"))
	            	  SearchResultproduct.Setsitename(jasonReader.nextString());
	              else if (str2.equals("review_cnt"))
	            	  SearchResultproduct.Setreviewcnt(jasonReader.nextString());
	              else if (str2.equals("avg_star"))
	            	  SearchResultproduct.Setavgstar(jasonReader.nextString());
	              else if (str2.equals("title"))
	            	  SearchResultproduct.Settitle(jasonReader.nextString());
	              else if (str2.equals("represent_review"))
	            	  SearchResultproduct.Setrepresentreview(jasonReader.nextString());
	              else{
	            	  jasonReader.skipValue();
	              }
	            }
	            jasonReader.endObject();
                SearchResult.add(SearchResultproduct);
	            }
                jasonReader.endArray();
	          }
	        }else{
	        	jasonReader.skipValue();
	        }
	      }
	      jasonReader.endObject();
	    }catch (Exception e){
	        e.printStackTrace();
	    }
	  }
	  
	  public final String getSearchCount(){
	    if ((SearchResulttotalCount == null) || ("".equals(SearchResulttotalCount))){
	    	return "0";
	    }
	    return SearchResulttotalCount;
	  }
	  
	  public final String getFussysearch(){
	    return SearchResultfussyCurrent;
	  }
	  
	  public final String getScannertitle(){
	    return SearchResultTitle;
	  }
	  
	  public final String getFirmname(){
	    return SearchResultFirmname;
	  }
	  
	  public final brandtitle getSearchbrand(){
	    return SearchResultbrand;
	  }
	  
	  public final List<productdes> getSearchProductdes(){
	    return SearchResult;
	  }
	  
	  private final class brandtitle implements Serializable{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<String> title = new ArrayList<String>();
		  private ArrayList<title> b = new ArrayList<title>();
		  private ArrayList<String> c = new ArrayList<String>();
		  
		  public final ArrayList<String> gettitle(){
		    return title;
		  }
		  
		  public final void addtitle(title parame){
		    this.b.add(parame);
		  }

		  public final void adddtitle(String title){
			  this.title.add(title);
		  }

		  public final ArrayList<title> b(){
		    return this.b;
		  }

		  public final ArrayList<String> c(){
		    return this.c;
		  }
	  }
	  public final class title implements Serializable{
		  /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String category_id;
		  private String category_name;
	
		  public final String getCategoryid(){
		    return category_id;
		  }
	
		  public final void Setcategoryid(String categoryid){
			  category_id = categoryid;
		  }
	
		  public final String getCategoryname(){
		    return category_name;
		  }
	
		  public final void Setcategoryname(String categoryname){
			  category_name = categoryname;
		  }
	}
	public final class productdes implements Serializable{
	  private String A;
	  private String B;
	  private String represent_review;
	  private String a;
	  private String b;
	  private String c;
	  private String d;
	  private String e;
	  private String f;
	  private String g;
	  private String produt_title;
	  private String url_crc;
	  private String site_id;
	  private String site_name;
	  private String min_price;
	  private String max_price;
	  private String n;
	  private String o;
	  private String avg_score;
	  private String review_cnt;
	  private String site_cnt_onsale;
	  private String s;
	  private String t;
	  private String u;
	  private String avg_star;
	  private String w;
	  private String x;
	  private String y;
	  private String z;

	  public final String getSitename(){
	    return site_name;
	  }

	  public final String getRepresentreview(){
	    return represent_review;
	  }

	  public final String a(){
	    return this.c;
	  }

	  public final void a(String paramString){
	    this.c = paramString;
	  }

	  public final String b(){
	    return this.d;
	  }

	  public final void b(String paramString){
	    this.d = paramString;
	  }

	  public final String c(){
	    return this.e;
	  }

	  public final void c(String paramString){
	    this.e = paramString;
	  }

	  public final String d(){
	    this.s = ("http://img.pingluntuan.com/dp" + url_crc + "-" + site_id + "_60.jpg");
	    return this.s;
	  }

	  public final void Settitle(String title){
		  produt_title = title;
	  }

	  public final String e(){
	    this.t = ("http://img.pingluntuan.com/dp" + url_crc + "-" + site_id + "_90.jpg");
	    return this.t;
	  }

	  public final void Seturlcrc(String urlcrc){
		  url_crc = urlcrc;
	  }

	  public final String f(){
	    this.u = ("http://img.pingluntuan.com/dp" + url_crc + "-" + site_id + ".jpg");
	    return this.u;
	  }

	  public final void Setsiteid(String siteid){
		  site_id = siteid;
	  }

	  public final String getProducttitle(){
	    return produt_title;
	  }

	  public final void Setminprice(String minprice){
	    if ((minprice == null) || (minprice.equals("")) || (minprice.equals("0")) || (minprice.equals("暂无价格"))){
	    	min_price = "暂无价格";
	    }else{
	    	min_price =String.valueOf(Double.valueOf(minprice).doubleValue() / 100.0D);
	    }
	    return;
	  }

	  public final String getUrlcrc(){
	    return url_crc;
	  }

	  public final void Setmaxprice(String maxprice){
	    if ((maxprice == null) || (maxprice.equals("")) || (maxprice.equals("0")) || (maxprice.equals("暂无价格"))){
	    	max_price = "暂无价格";
	    }else{
	    	max_price = String.valueOf(Double.valueOf(maxprice).doubleValue() / 100.0D);
	    }
	    return;
	  }

	  public final String getSiteid(){
	    return site_id;
	  }

	  public final void i(String paramString){
	    this.o = paramString;
	  }

	  public final String getMinprice(){
	    return min_price;
	  }

	  public final void Setavgscore(String avgscore){
	    if ((avgscore == null) || (avgscore.equals(""))){
	    	avg_score = "0";
	    }else{
	    	avg_score = avgscore;
	    }
	    return;
	  }

	  public final String getMaxprice(){
	    return max_price;
	  }

	  public final void Setreviewcnt(String reviewcnt){
	    if ((reviewcnt == null) || (reviewcnt.equals(""))){
	    	review_cnt = "0";
	    }else{
	    	review_cnt = reviewcnt;
	    }
	    return;
	  }

	  public final String l(){
	    return this.o;
	  }

	  public final void Setsitecntonsale(String sitecntonsale){
		  site_cnt_onsale = sitecntonsale;
	  }

	  public final String m(){
	    return avg_score;
	  }

	  public final void Setavgstar(String avgstar){
	    if ((avgstar == null) || (avgstar.equals(""))){
	    	avg_star = "0";
	    }else{
	    	avg_star = avgstar;
	    }
	    return;
	  }

	  public final String getReviewcnt(){
	    return review_cnt;
	  }

	  public final void n(String paramString){
	    this.w = paramString;
	  }

	  public final String getSitecntonsale(){
	    return site_cnt_onsale;
	  }

	  public final void o(String paramString){
	    this.x = paramString;
	  }

	  public final String getAvgstar(){
	    return avg_star;
	  }

	  public final void p(String paramString){
	    this.y = paramString;
	  }

	  public final String q(){
	    return this.w;
	  }

	  public final void q(String paramString){
	    this.z = paramString;
	  }

	  public final String r(){
	    return this.x;
	  }

	  public final void r(String paramString){
	    this.A = paramString;
	  }

	  public final String s(){
	    return this.y;
	  }

	  public final void s(String paramString){
	    if ((paramString == null) || (paramString.equals("0")) || (paramString.equals(""))){
	    	this.n = "暂无价格";
	    }else{
	    	this.n = String.valueOf(Double.valueOf(paramString).doubleValue() / 100.0D);
	    }
	    return;
	  }

	  public final String t(){
	    return this.z;
	  }

	  public final void t(String paramString){
	    this.g = paramString;
	  }

	  public final String u(){
	    return this.A;
	  }

	  public final void u(String paramString){
	    this.a = paramString;
	  }

	  public final String v(){
	    return this.n;
	  }

	  public final void v(String paramString){
	    this.b = paramString;
	  }

	  public final String w(){
	    return this.g;
	  }

	  public final void w(String paramString){
	    this.f = paramString;
	  }

	  public final String x(){
	    return this.a;
	  }

	  public final void x(String paramString){
	    this.B = paramString;
	  }

	  public final String y(){
	    return this.b;
	  }

	  public final void Setsitename(String sitename){
		  site_name = sitename;
	  }

	  public final String z(){
	    return this.f;
	  }

	  public final void Setrepresentreview(String representreview){
		  represent_review = representreview;
	  }
	}
}