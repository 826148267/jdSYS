package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFormatTool {
	/**
	 * ���ڼ���id������
	 * ֻ��������ĸ�����֣��»���
	 * ���Ϸ���true
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
