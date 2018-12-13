package com.utils.reg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegUtils {
	private static String msg = "";
	private static String regRepSpace = "\\s*|\t|\r|\n";

	public RegUtils() {
	}

	/**
	 * <strong>ɾ���ַ����еĿո񡢻��з���Tab</strong><br>
	 * <br>
	 * 
	 * @author Aaron.ffp
	 * @version V1.0.0: autotest-base cn.ffp.autotest.base.util RegUtils.java
	 *          replaceSpace, 2016-03-03 13:13:00.083 Exp $
	 * 
	 * @param str
	 *            ��Ҫɾ���ո񡢻��з���Tab��Դ�ַ���
	 * 
	 * @return String
	 */
	public static String replaceSpace(String str) {
		return RegUtils.replace(str, regRepSpace, "");
	}

	/**
	 * <strong>�ַ����滻</strong><br>
	 * <ul>
	 * <li>ͨ��������ʽƥ�䣬ƥ��Ĳ��ֽ����滻</li>
	 * </ul>
	 * <br>
	 * 
	 * @author Aaron.ffp
	 * @version V1.0.0: autotest-base cn.ffp.autotest.base.util RegUtils.java
	 *          replace, 2016-01-14 00:13:56.657 Exp $
	 * 
	 * @param str
	 *            ��Ҫ�滻��Դ�ַ���
	 * @param reg
	 *            ������ʽ��ƥ����Ҫ���滻�Ĳ���
	 * @param replacement
	 *            �滻����ַ���
	 * @return String
	 */
	public static String replace(String str, String reg, String replacement) {
		String strReplace = "";

		try {
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			strReplace = matcher.replaceAll(replacement);
		} catch (PatternSyntaxException pse) {
		}

		return strReplace;
	}

	/**
	 * <strong>�ж��ַ����Ƿ����������ʽ</strong><br>
	 * <ul>
	 * <li>������������ʽ���򷵻� true</li>
	 * </ul>
	 * <br>
	 * 
	 * @author Aaron.ffp
	 * @version V1.0.0: autotest-base cn.ffp.autotest.base.util RegUtils.java reg,
	 *          2015-12-27 23:30:35.064 Exp $
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param reg
	 *            ������ʽ
	 * @return boolean
	 */
	public static boolean reg(String str, String reg) {
		boolean match = false;

		try {
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(str);
			match = matcher.matches();
		} catch (PatternSyntaxException pse) {
		}

		return match;
	}
}