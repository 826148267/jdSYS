package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormatTool {
	/**
	 * 用于检验id和密码
	 * 只允许用字母，数字，下划线
	 * 符合返回true
	 * @param id
	 * @param psw
	 * @return
	 */
	public static boolean checkIAP(String id,String psw){
		boolean flag = false;
		Pattern p = Pattern.compile("[a-zA-Z0-9_]{6,25}");
		Matcher m1 = p.matcher(id);
		Matcher m2 = p.matcher(psw);
        if(m1.matches()){
        	flag = m2.matches();
        }
		return flag; 
	}
	
}
