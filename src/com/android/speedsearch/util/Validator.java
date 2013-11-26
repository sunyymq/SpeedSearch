package com.android.speedsearch.util;

import java.util.regex.Pattern;

public final class Validator{
  public static boolean IsAllowedLetter(String str){
    return match("^[0-9A-Za-zÒ»-ý›\\(\\)_ .&-]+$", str);
  }

  public static boolean IsChinese(String str){
    return match("^[Ò»-ý›]+", str);
  }

  public static boolean IsContainJson(String str){
    String wrap1 = "(\\{([^\\{\\}]*" + "(\\{[^\\{\\}]*\\})" + "*[^\\{\\}]*)+\\})";
    String wrap2 = "(\\{([^\\{\\}]*" + wrap1 + "*[^\\{\\}]*)+\\})";
    String wrap3 = "(\\{([^\\{\\}]*" + wrap2 + "*[^\\{\\}]*)+\\})";
    return match("[^\\{\\}]*" + wrap3 + "+[^\\{\\}]*$", str);
  }

  public static boolean IsDay(String str){
    return match("^((0?[1-9])|((1|2)[0-9])|30|31)$", str);
  }

  public static boolean IsDecimal(String str){
    return match("^[0-9]+(.[0-9]{2})?$", str);
  }

  public static boolean IsHandset(String str){
    return match("^1[3,4,5,8]\\d{9}$", str);
  }

  public static boolean IsIDcard(String str){
    return match("(^\\d{18}$)|(^\\d{15}$)", str);
  }

  public static boolean IsIntNumber(String str){
    return match("^\\+?[1-9][0-9]*$", str);
  }

  public static boolean IsLetter(String str){
    return match("^[A-Za-z]+$", str);
  }

  public static boolean IsLetterOrNumber(String str){
    return match("^[0-9A-Za-z]", str);
  }

  public static boolean IsLettersOrNumbers(String str){
    int strLen = str.length();
    for (int i = 0; i< strLen; i++){
    	String c = String.valueOf(str.charAt(i)).toString();
        if (IsLetterOrNumber(c))
          return false;
    }
  	return true;
  }

  public static boolean IsLowChar(String str){
    return match("^[a-z]+$", str);
  }

  public static boolean IsMonth(String str){
    return match("^(0?[[1-9]|1[0-2])$", str);
  }

  public static boolean IsNumber(String str){
    return match("^[0-9]*$", str);
  }

  public static boolean IsNumbers(String str){
    if (str == null)
      return false;
    int strLen = str.length();
    for (int i = 0; i< strLen; i++){
      String c = String.valueOf(str.charAt(i)).toString();
      if (!IsNumber(c))
        return false;
    }
    return true;
  }

  public static boolean IsPasswLength(String str){
    return match("^\\d{6,18}$", str);
  }

  public static boolean IsPassword(String str){
    return match("[A-Za-z]+[0-9]", str);
  }

  public static boolean IsPostalcode(String str){
    return match("^\\d{6}$", str);
  }

  public static boolean IsTelephone(String str){
    return match("^(\\+)?(\\d{2,4}-)?(\\d{2,4}-)?\\d{4,13}(-\\d{2,4})?$", str);
  }

  public static boolean IsUpChar(String str){
    return match("^[A-Z]+$", str);
  }

  public static boolean IsUrl(String str){
    String regex = "(" + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + ")";
    return match("(?i)http(?-i)([sS])?[:£º]//(([0-9A-Za-z]([\\w-]*\\.)+[A-Za-z]+)|" + regex + ")" + "(?::(\\d{1,5}))?(/[\\w- ./?!%&=#:~|]*)?", str);
  }

  public static boolean IsUrl2(String str){
    return match("(?i)http(?-i)([sS])?[:£º]//([\\w-]+\\.)+[\\w-]+(?::(\\d{1,5}))?(/[\\w- ./?!%&=#$_:~|'@;,*+()\\[\\]]*)?", str);
  }

  public static boolean isDate(String str){
    return match("^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$", str);
  }

  public static boolean isEffective(String str){
    if ((str == null) || ("".equals(str)) || (" ".equals(str)) || ("null".equals(str))){
    	return false;
    }
    return true;
  }

  public static boolean isEmail(String str){
    return match("^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", str);
  }

  public static boolean isIP(String str){
    return match("^" + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "\\." + "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)" + "$", str);
  }

  public static boolean isLatitude(String string, boolean acceptZero){
	  double lat;
	  try{
		  lat = Double.parseDouble(string);
	  }catch (Exception e) {
		return false;
	  }
      if ((lat > 90.0D) || (lat < -90.0D)){
    	  return false;
      }
      if ((lat != 0.0D) || (acceptZero)){
        	return true;
      }
      return false;
  }

  public static boolean isLongitude(String string, boolean acceptZero){
	  double lat;
	  try{
		  lat = Double.parseDouble(string);
	  }catch(Exception e){
		  return false;
	  }
      if ((lat > 180.0D) || (lat < -180.0D)){
    	  return false;
      }
      if ((lat != 0.0D) || (acceptZero)){
          return true;
      }
      return false;
  }

  public static boolean isPriceEffective(String str_price){
    return isPriceEffective(str_price, 0.0D);
  }

  public static boolean isPriceEffective(String str_price, double limitPirce){
    if (DataConverter.parseDouble(str_price) > limitPirce){
    	return true;
    }
    return false;
  }

  public static boolean isUtf8Data(byte[] b, int index, int type){
    int lLen = b.length;
    int lCharCount = 0;
    int i = index;
    int j = i;
    while((j < lLen) && (lCharCount < type)){
	    i = j + 1;
	    byte lByte = b[j];
	    if(lByte < 0){
	    	if ((lByte >= -64) && (lByte <= -3)){
	    		int lCount;
	    		if (lByte <= -4){
	    			if(lByte > -8){
	    				lCount = 4;
	    			}else if(lByte > -16){
	    				lCount = 3;
	    			}else if(lByte > -32){
	    				lCount = 2;
	    			}else{
	    				lCount = 1;
	    			}
	    		}else{
	    			lCount = 5;
	    		}
		    	if(i + lCount > lLen ){
		    		for(int k =0;k<lCount;k++){
		    			if(b[i] < 64){
		    				k++;
		    				i++;
		    			}else{
		    				return false;
		    			}
		    		}
		    	}else{
		    		return false;
		    	}
	    	}else{
	    		return false;
	    	}

	    }
	    lCharCount ++;
	    j = i;
    }
    i = j;
    return true;
  }

  private static boolean match(String regex, String str){
    if (str == null){
    	return false;
    }
    if (regex == null)
        return true;
    else
       return Pattern.compile(regex).matcher(str).matches();
  }
}