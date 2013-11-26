package com.android.speedsearch.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class DataConverter{
  public static String TAG = "DataConverter";

  public static String Compose(String str1, String str2, int len){
    int len1 = str1.length();
    int len2 = str2.length();
    String result = str1;
    int num = len - len1 - len2;
    for (int i = 0; i< num; i++){
      result = result + "\t";
    }
    return result + str2;
  }

  public static String ConvertTime(int seconds){
    if (seconds <= 0)
      return "00:00";

      int mins = seconds % 3600 / 60;
      int secs = seconds % 60;
      String time = "";
      if (mins < 10){
    	  time = "" + "0" + mins;
      }else{
    	  time = "" + mins;
      }
      time = time + ":";
      if (secs >= 10){
    	  time = time + "0" + secs;
      }else{
    	  time = time + secs;
      }
      return time;
  }

  public static String ConvertTime2(int seconds){
    if (seconds <= 0){
    	return "0\"";
    }

    String time = "";
    int mins = seconds % 3600 / 60;
    int secs = seconds % 60;
    if (mins > 0)
    	time = new StringBuilder(String.valueOf(time)).append(mins).toString() + "'";
    else{
    	time = new StringBuilder(String.valueOf(time)).append(secs).toString() + "\"";
    }
    return time;
  }

  public static String DecimalToInteger(String Decimal){
    return String.valueOf((int)(100.0D * parseDouble(Decimal)));
  }

  public static String ExtractFileName(String url){
    if (url == null)
      return null;
    int len = url.length();
    int start = url.lastIndexOf("/") + 1;
    if (start > len){
        return null;
    }else{
        String filename = url.substring(start);
        if (!filename.contains("."))
          return null;
        return filename;
    }
  }

  public static String ExtractFileRoot(String url){
    if (url == null){
    	return null;
    }
    int len = url.length();
    int start = url.lastIndexOf("/") + 1;
    if (start <= len)
        return url.substring(0, start);
    return null;
  }

  public static String FilterFileChar(String str){
    if (str == null){
    	return "";
    }
    return str.replaceAll("[//,\\,:,*,?,\",|,<,>,-,_]", "");
  }

  public static double GetDistance(double lat1, double lng1, double lat2, double lng2){
    double d1 = rad(lat1);
    double d2 = rad(lat2);
    double d3 = d1 - d2;
    double d4 = rad(lng1) - rad(lng2);
    return 1000.0D * (6378.1369999999997D * (2.0D * Math.asin(Math.sqrt(Math.pow(Math.sin(d3 / 2.0D), 2.0D) + Math.cos(d1) * Math.cos(d2) * Math.pow(Math.sin(d4 / 2.0D), 2.0D)))));
  }

  public static String GetDouble1(double num){
    return new DecimalFormat("0.0").format(num);
  }

  public static String GetDouble2(double num){
    return new DecimalFormat("0.00").format(num);
  }

  public static int GetGpsHigh(double lng, double lat){
    int iLat = (int)(lat * 1000000.0D);
    return 10000 * ((int)(lng * 1000000.0D) / 10000) + iLat / 10000;
  }

  public static int GetGpsLow(double lng, double lat){
    int iLat = (int)(lat * 1000000.0D);
    int iLng = (int)(lng * 1000000.0D);
    return 10000 * (iLat % 10000) + iLng % 10000;
  }

  public static String IntegerToDecimal(String Integer){
    return GetDouble2(parseInt(Integer) / 100.0D);
  }

  public static String Mask(String str, String masker, int startPos, int masklen){
    if (str == null)
    	return "";
    if(masklen < 0)
      return str;
    int l = str.length();
    if (startPos > l)
    	startPos = l;
    String a = str.substring(0, startPos);
    startPos = startPos + masklen;
    if (startPos > l)
    	startPos = l;
    String b = str.substring(startPos);
    for (int i = 0; i< masklen; i++){
      a = a + masker;
    }
  	str = a + b;
  	return str;
  }

  public static int MoveRightE5(double data){
    return (int)(0.5D + 100000.0D * data);
  }

  public static int MoveRightE6(double data){
    return (int)(0.5D + 1000000.0D * data);
  }

  public static String PriceDecimalFormat(String DecimalPrice){
	  String result = "0.00";
	  try{
		  double value = Double.parseDouble(DecimalPrice.replaceAll("[,，]", ""));
		  return new DecimalFormat(result).format(value);
	  }catch (Exception e){
		  return result;
	  }
  }

  public static double PriceMul(String DecimalPrice, String IntegerCount){
    return parseDouble(DecimalPrice) * parseInt(IntegerCount);
  }

  public static String RemoveZeroAndDot(String str){
    if (str == null)
    	return "";
    if (str.indexOf(".") > 0)
    	str = str.replaceAll("0*$", "").replaceAll("[.]*$", "");
    return str;
  }

  public static String RenameByteSize(long ByteSize){
    if (ByteSize < 1024L)
      return ByteSize + "B";
    if (ByteSize / 1024L < 1024L)
        return GetDouble1(ByteSize / 1024.0D) + "KB";
    else
        return GetDouble1(ByteSize / 1048576.0D) + "MB";
  }

  public static String ReplaceSpace(String str){
    if (str == null){
    	return "";
    }else{
    	return str.replaceAll("[ ,\t,\r,\n]+", "");
    }
  }

  public static String[] Split(String str, int len){
    if (len == 0){
      return null;
    }
    int i = 0;
    int length = str.length();
    char[] chas = str.toCharArray();
    int[] lastCharPoses = new int[10];
    lastCharPoses[0] = 0;
    int bytelen = 0;
    int num = 1;
    for(i=0;i<length;i++){
    	if(chas[i] > 'ÿ'){
    		bytelen +=2;
    	}else{
    		bytelen++;
    	}
    	if (bytelen > num * len){
    		lastCharPoses[num] = i;
    		num++;
    	}
    }
    lastCharPoses[num] = i;
    String[] result = new String[num];
    
    for (i = 0; i< num -1; i++){
      result[i] = str.substring(lastCharPoses[i], lastCharPoses[(i + 1)]);
    }
    result[(num - 1)] = str.substring(lastCharPoses[i]);
    return result;
  }

  public static String StringFilter(String str){
    if (str == null){
    	return "";
    }else{
    	return str.replaceAll("<p>", "").replaceAll("</p>", "</br>").replaceAll("【", " [").replaceAll("】", "] ").replaceAll("！", "! ").replaceAll(":", ": ").replaceAll("：", ": ").replaceAll("；", "; ").replaceAll("，", ", ").replaceAll("（", " (").replaceAll("。", ". ").replaceAll("）", ") ").replace('\r', ' ').trim();
    }
  }

  public static String StringFilterDotNoSpace(String str){
    if (str == null){
    	return "";
    }else{
    	return str.replaceAll("【", " [").replaceAll("】", "] ").replaceAll("！", "! ").replaceAll(":", ": ").replaceAll("：", ": ").replaceAll("；", "; ").replaceAll("，", ", ").replaceAll("（", " (").replaceAll("。", ".").replaceAll("）", ") ").replace('\r', ' ').trim();
    }
  }

  public static String StringFilterNoSpace(String str){
    if (str == null){
    	return "";
    }else{
    	return str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!").replaceAll(":", ":").replaceAll("：", ":").replaceAll("；", ";").replaceAll("，", ",").replaceAll("（", "(").replaceAll("。", ".").replaceAll("）", ")").replace('\r', ' ').trim();
    }
  }

  public static String TrimLongerString(String str, int maxbytelen){
    if (str == null)
    	return "";
    if(str.getBytes().length <= maxbytelen)
      return str;
    String Pseudo = str;
    int length = str.length();
    char[] chas = str.toCharArray();
    int bytelen = 0;
    for (int i = 0; i<length ; i++){
      if (chas[i] > 'ÿ')
    	  bytelen += 2;
      else {
    	  bytelen++;
      }
      if (bytelen > maxbytelen){
    	  Pseudo = str.substring(0, i) + "...";
    	  break;
      }
    }
    return Pseudo;
  }

  public static String convert(String str, int index){
    try{
      char[] a = str.toCharArray();
      int length = a.length;
      int len2 = "@n0dr#ew!$".length();
      for (int i = 0; i<length; i++){
        a[i] = ((char)(a[i] ^ "@n0dr#ew!$".charAt((i + index) % len2)));
      }
      str = new String(a);
    }catch (Exception e){
      e.printStackTrace();
    }
    return str;
  }

  public static String convertMD5(byte[] byteMD5){
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'e', 'e', 'f' };
    char[] str = new char[32];
    int k = 0;
    int i = 0;
    int j = k;
    for(i=0;i < 16;i++){
    	byte byte0 = byteMD5[i];
    	k = j+1;
    	str[j] = hexDigits[(0xF & byte0 >>> 4)];
    	j = k+1;
    	str[k] = hexDigits[(byte0 & 0xF)];
    }
    return new String(str);
  }

  public static String fullToHalfCorner(String input){
	if(input == null){
		return "";
	}
    char[] c = input.toCharArray();
    for(int i =0;i<c.length;i++){
    	if(c[i] == '　'){
    		c[i] = ' ';
    	}else{
            if ((c[i] > 65280) && (c[i] < 65375))
                c[i] = ((char)(c[i] - 65248));	
    	}
    }
    return new String(c);
  }

  public static int getChBytelen(String ChStr){
    int length = ChStr.length();
    char[] chas = ChStr.toCharArray();
    int bytelen = 0;
    for(int i =0; i < length; i++){
        if (chas[i] > 'ÿ')
        	bytelen += 2;
        else{
        	bytelen ++;   	
        }
    }
    return bytelen;
  }

  public static String getCurrencySymbolById(String currencyId){
    switch (parseInt(currencyId)){
    case 1:
    	return Currency.getInstance("HKD").getSymbol();
    case 2:
    	return Currency.getInstance("TWD").getSymbol();
    case 3:
    	return Currency.getInstance("MOP").getSymbol();
    case 4:
    	return Currency.getInstance("USD").getSymbol();
    case 5:
    	return Currency.getInstance("GBP").getSymbol();
    case 6:
    	return Currency.getInstance("EUR").getSymbol();
    default:
        return Currency.getInstance("CNY").getSymbol();
    }
  }

  public static String getDistanceCN(double lat1, double lng1, double lat2, double lng2){
    double dis = GetDistance(lat1, lng1, lat2, lng2);
    if ((dis > 0.0D) && (dis < 100.0D))
      return "附近";
    if ((dis >= 100.0D) && (dis < 1000.0D))
      return (int)dis + "米";
    else if (dis >= 1000.0D)
      return GetDouble1(dis / 1000.0D) + "公里";
    else
      return "";
  }

  public static String getJsonObject(String str){
    if (str == null){
    	return null;
    }
    if (Validator.IsContainJson(str)){
        int start = str.indexOf("{");
        int end = str.lastIndexOf("}");
        if ((start > 0) && (start <= end) && (end <= str.length()))
          return str.substring(start, end);
    }
    return null;
  }

  public static String getMD5(byte[] source){
    String s = "";
    try{
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(source);
      s = convertMD5(md.digest());
    }catch (Exception e){
        e.printStackTrace();
    }
    return s;
  }

  public static String getValueofKey(String str, String key, String spliter){
    int index = hasKey(str, key, spliter);
    if (index != -1){
      String[] tmps = str.substring(index).split(spliter);
      if ((tmps != null) && (tmps.length > 0))
        str = tmps[0];
    }
    return str;
  }

  public static String getValueofKey(String str, String[] keys){
    int index = hasKeys(str, keys);
    if (index != -1){
      String[] tmps = str.substring(index).split("[\n\r;\t]");
      if ((tmps != null) && (tmps.length > 0))
        str = tmps[0];
    }
    return str;
  }

  public static String getValueofKey(String str, String[] keys, String spliter){
    int index = hasKeys(str, keys, spliter);
    if (index != -1){
      String[] tmps = str.substring(index).split(spliter);
      if ((tmps != null) && (tmps.length > 0))
        str = tmps[0];
    }
    return str;
  }

  public static List<String> getValueofKey(String[] fields, String key){
    ArrayList<String> values = new ArrayList<String>();
    if ((fields == null) || (key == null)){
    	return values;
    }
    int size = fields.length;
    for (int i = 0; i < size; i++){
    	String value = fields[i];
    	if (value.startsWith(key))
    		values.add(value.substring(key.length()));
    }
    return values;
  }

  public static List<String> getValueofKeys(String[] fields, String[] keys){
    ArrayList<String> values = new ArrayList<String>();
    if ((fields == null) || (keys == null))
      return values;
    int size = fields.length;
    int len = keys.length;
    for(int i = 0; i < size;i++){
    	String value = fields[i];
    	for(int j = 0;j<len;j++){
    		if (value.startsWith(keys[j])){
    			int index = keys[j].length();
    			values.add(value.substring(index));
    			j = len;
    		}
    	}
    }
    return values;
  }

  public static int hasKey(String str, String key){
    if ((str == null) || (key == null))
      return -1;

    int index = str.indexOf(key);
    if (index >= 0){
      if (index == 0)
    	  index += key.length();
      else{
    	  if (String.valueOf(str.charAt(index - 1)).replaceAll("[\n\r\t;]", "").length() == 0)
    		  index += key.length();
      }
    }
    return index;
  }

  public static int hasKey(String str, String key, String spliter){
    if ((str == null) || (key == null))
      return -1;

    int index = str.indexOf(key);
    if (index >= 0){
      if (index == 0)
    	  index += key.length();
      else if (String.valueOf(str.charAt(index - 1)).replaceAll(spliter, "").length() == 0)
    	  index += key.length();
    }
    return index;
  }

  public static int hasKeys(String str, String[] keys){
    if ((str == null) || (keys == null)){
      return -1;
    }
    int len = keys.length;
    int size = str.length();
    int pos = 0;
    int index = -1;
    for(int i = 0;i < len;i++){
    	for(pos = 0; pos<size;pos++){
    		index = str.indexOf(keys[i], pos);
    		if(index == 0){
    			return index + keys[i].length();
    		}else if(index > 0){
    			if(String.valueOf(str.charAt(index - 1)).replaceAll("[\n\r\t;]", "").length() == 0){
    				return index + keys[i].length();
    			}else{
    				pos = index + keys[i].length();
    			}
    		}
    	}
    	index --;
    }
    return index;
  }

  public static int hasKeys(String str, String[] keys, String spliter){
    if ((str == null) || (keys == null)){
        return -1;
      }
      int len = keys.length;
      int size = str.length();
      int pos = 0;
      int index = -1;
      for(int i = 0;i < len;i++){
      	for(pos = 0; pos<size;pos++){
      		index = str.indexOf(keys[i], pos);
      		if(index == 0){
      			return index + keys[i].length();
      		}else if(index > 0){
      			if(String.valueOf(str.charAt(index - 1)).replaceAll(spliter, "").length() == 0){
      				return index + keys[i].length();
      			}else{
      				pos = index + keys[i].length();
      			}
      		}
      	}
      	index --;
      }
      return index;
  }

  public static String joinStrings(String firstStr, String secondStr){
	  if ((!Validator.isEffective(firstStr)) && (!Validator.isEffective(secondStr)))
    	return "";
      if ((Validator.isEffective(firstStr)) && (!Validator.isEffective(secondStr)))
    	  secondStr = firstStr;
      else if (((Validator.isEffective(firstStr)) || (!Validator.isEffective(secondStr))) && (!secondStr.startsWith(firstStr)))
    	  secondStr = firstStr + secondStr;
      return secondStr;
  }

  public static String[] newStringArray(int addPos, String[] ori, boolean PaddingLeft){
      if (ori == null)
    	  return null;
      int size = ori.length;
      String[] strings = new String[size + addPos];
      if (!PaddingLeft)
        for (int k = 0; k < size; k++)
        	strings[(k + addPos)] = ori[k];
      else
        for (int j = 0; j < size; j++)
        	strings[(j + addPos)] = ("\t\t" + ori[j]);
      return strings;
  }

  public static double parseDouble(String strDouble){
	  double d1 = 0.0D;
      if ((strDouble == null) || ("".equals(strDouble)))
    	  return d1;
      try{
        d1 = Double.parseDouble(strDouble);
      }catch (Exception e){
      }
      return d1;
  }

  public static double parseDouble(String strDouble, double defaultValue){
	  if ((strDouble == null) || ("".equals(strDouble)))
		  return defaultValue;
      try{
    	  defaultValue = Double.parseDouble(strDouble);
      }catch (Exception e){
      }
      return defaultValue;
  }

  public static int parseInt(String strInt){
    return parseInt(strInt, 0);
  }

  public static int parseInt(String strInt, int defaultValue){
	  if ((strInt == null) || ("".equals(strInt)))
    	return defaultValue;
      try{
    	  defaultValue = Integer.parseInt(strInt);
      }catch (Exception e){
      }
      return defaultValue;
  }

  private static double rad(double d){
    return 3.141592653589793D * d / 180.0D;
  }

  public static String removeSuffix(String str, String suffix){
    if (str.endsWith(suffix))
    	str = str.substring(0, str.lastIndexOf(suffix));
    return str;
  }

  public static String urlDecode(String str){
    if ((str == null) || ("".equalsIgnoreCase(str))){
    	return str;
    }
    return URLDecoder.decode(str);
  }

  public static String urlEncode(String str){
    if ((str == null) || ("".equalsIgnoreCase(str))){
    	return str;
    }
    return URLEncoder.encode(str);
  }
}