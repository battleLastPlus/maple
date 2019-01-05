package com.maple.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yrf
 * @date 2019��1��5��
 */
public class ValidateUtil {

	private static final Pattern P_EMAIL = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

	/**
	 *
	 * @������У��email��ʽ
	 * @param str
	 * @return
	 */
	public static boolean isEmail(String str) {
		return str != null && P_EMAIL.matcher(str).matches();
	}

	private static final Pattern SIMPLE_PASSWORD = Pattern.compile("^[0-9_a-zA-Z]{6,20}$");

	public static boolean isSimplePassword(String str) {
		return StringUtils.isNotBlank(str) && SIMPLE_PASSWORD.matcher(str).matches();
	}

	private static final Pattern PASSWORD = Pattern.compile("");
	private static final Pattern CENTER_PASSWORD = Pattern.compile("");
	private static final Pattern STRONG_PASSWORD = Pattern.compile("");

	/**
	 *
	 * @����������У�飺ƥ��Сд��ĸ����д��ĸ�����֡�������ŵ����ּ��������ϡ������ġ� @�����ˣ�wyait @����ʱ�䣺2017��1��5��15:19:17
	 * @param str
	 * @return
	 */
	public static boolean isPassword(String str) {
		return str != null && PASSWORD.matcher(str).matches();
	}

	public static boolean isCenterPassword(String str) {
		return str != null && CENTER_PASSWORD.matcher(str).matches();
	}

	public static boolean isStrongPassword(String str) {
		return str != null && STRONG_PASSWORD.matcher(str).matches();
	}

	private static final Pattern P_MOBILEPHONE = Pattern.compile("^1\\d{10}$");

	/**
	 *
	 * @������У���Ƿ�Ϊ11λ1��ͷ�ֻ���
	 * @param str
	 * @return
	 */
	public static boolean isMobilephone(String str) {
		return StringUtils.isNotBlank(str) && P_MOBILEPHONE.matcher(str).matches();
	}

	// ��������
	private static final Pattern P_NUMBER = Pattern.compile("^[-\\+]?[\\d]+$");

	/**
	 *
	 * @������У���Ƿ���������
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return StringUtils.isNotBlank(str) && P_NUMBER.matcher(str).matches();
	}

	// QQУ�� 4_12λ
	private static final Pattern QQ = Pattern.compile("[1-9][0-9]{4,12}");

	/**
	 *
	 * @������У���Ƿ�Ϊ4-12λ������
	 * @param str
	 * @return
	 */
	public static boolean isQQ(String str) {
		return StringUtils.isNotBlank(str) && QQ.matcher(str).matches();
	}

	// 6λ����֤��
	private static final Pattern CODE = Pattern.compile("[0-9]{6}$");

	/**
	 *
	 * @������У���Ƿ�Ϊ6λ������֤��
	 * @param str
	 * @return
	 */
	public static boolean isCode(String str) {
		return StringUtils.isNotBlank(str) && CODE.matcher(str).matches();
	}

	// ͼƬ��֤�� 4λ�����ĸ������
	private static final Pattern PICCODE = Pattern.compile("\\w{4}$");

	/**
	 *
	 * @������У���Ƿ�Ϊ4λ�������������ĸ
	 * @param str
	 * @return
	 */
	public static boolean isPicCode(String str) {
		return str != null && PICCODE.matcher(str).matches();
	}

	// ������������С����������
	private static final Pattern P_DOUBLE = Pattern.compile("");

	/**
	 *
	 * @������У���Ƿ�Ϊ������������С���������� @�����ˣ�wyait
	 * @return
	 */
	public static boolean isDouble(String str) {
		return str != null && P_DOUBLE.matcher(str).matches();
	}

	// �Ƿ�ȫ�����ģ�����������������ַ���false
	private static final Pattern P_CHINESE = Pattern.compile("^[\u0391-\uFFE5]+$");

	/**
	 *
	 * @������У���Ƿ�Ϊ���ĺ���
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		return str != null && P_CHINESE.matcher(str).matches();
	}

	// �Ƿ�������ģ�������Ϊtrue
	private static final Pattern P_CHINESE_A = Pattern.compile("[\u0391-\uFFE5]");

	/**
	 *
	 * @������У���Ƿ��������
	 * @param str
	 * @return
	 */
	public static boolean hasChinese(String str) {
		return str != null && P_CHINESE_A.matcher(str).find();
	}

	// ��������������У��
	private static final Pattern NAME = Pattern.compile("");

	/**
	 *
	 * @��������������У�飨��ĸ�����֡����֡��»��ߵȷ��ţ� @�����ˣ�wyait
	 * @param str
	 * @return
	 */
	public static boolean isSearchName(String str) {
		return str != null && NAME.matcher(str).matches();
	}

	/**
	 * ���п���У�飨16��19λ��
	 */
	private static final Pattern BANKCODE = Pattern.compile("");

	/**
	 *
	 * @���������п���У��
	 * @param str
	 * @return
	 */
	public static boolean isBankCode(String str) {
		return str != null && BANKCODE.matcher(str).matches();
	}

	/**
	 * 6λ���ֶ�����֤��
	 */
	private static final Pattern MESCODE = Pattern.compile("^[0-9]{6}$");

	public static boolean isMessageCode(String str) {
		return str != null && MESCODE.matcher(str).matches();
	}

	/**
	 * ��������С�������λ ���У��
	 */
	private static final Pattern MONEY = Pattern.compile("");

	/**
	 *
	 * @��������������С�������λ ���У��
	 * @param str
	 * @return
	 */
	public static boolean isMoney(String str) {
		return str != null && MONEY.matcher(str).matches();
	}

	/**
	 * С�ڵ���100����������С�������λ
	 */
	private static final Pattern BAIMONEY = Pattern.compile("");

	/**
	 *
	 * @������С�ڵ���100����������С�������λ
	 * @param str
	 * @return
	 */
	public static boolean isBaiMoney(String str) {
		return str != null && BAIMONEY.matcher(str).matches();
	}

	// ��עУ�� �����룺��Ӣ�ġ��ո���Ӣ�ı���»��ߡ�
	private static final Pattern REMARK = Pattern.compile("");

	/**
	 *
	 * @��������עУ�� �����룺��Ӣ�ġ��ո���Ӣ�ı���»��ߡ�
	 * @param str
	 * @return
	 */
	public static boolean isRemark(String str) {
		return str != null && REMARK.matcher(str).matches();
	}

	/**
	 * ΢�ź�У��
	 */
	private static final Pattern WXNUM = Pattern.compile("");

	/**
	 *
	 * @������΢�ź�У��
	 * @param str
	 * @return
	 */
	public static boolean isWxnum(String str) {
		return str != null && WXNUM.matcher(str).matches();
	}

	/**
	 * �û���У��:��ĸ�����֡����ġ��»������2-20λ���������»��߿�ͷ��
	 */
	private static final Pattern USERNAME = Pattern.compile("");

	/**
	 *
	 * @�������û���У��
	 * @param str
	 * @return
	 */
	public static boolean isUsername(String str) {
		return str != null && USERNAME.matcher(str).matches();
	}

	/**
	 * �û���У��:��ĸ�����ġ������2-20λ
	 */
	private static final Pattern REALRNAME = Pattern.compile("");

	/**
	 *
	 * @��������ʵ����У��
	 * @param str
	 * @return
	 */
	public static boolean isRealname(String str) {
		return str != null && REALRNAME.matcher(str).matches();
	}

	/**
	 * �û���У��:��ĸ�����ġ������2-20λ
	 */
	private static final Pattern ADDRIP = Pattern.compile("");

	public static boolean isAddrIP(String str) {
		return str != null && ADDRIP.matcher(str).matches();
	}

	// ֻ��������λС����У��
	private static final Pattern MONEY2 = Pattern.compile("");

	public static boolean isMONEY2(String str) {
		return str != null && MONEY2.matcher(str).matches();
	}

	/** ���֧��1000-3999 ֧�ֺ��� **/
	private static final Pattern DATE = Pattern.compile("");

	public static boolean isDate(String str) {
		return str != null && DATE.matcher(str).matches();
	}

}
