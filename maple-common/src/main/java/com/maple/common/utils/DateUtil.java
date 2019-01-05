package com.maple.common.utils;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author yrf
 * @date 2019��1��5��
 */
public class DateUtil  implements Serializable{
	private static final long serialVersionUID = 5110771010886130754L;
	//��������
	public static DateUtil instance = new DateUtil();
	private DateUtil() {
	}

	public static DateUtil getInstance() {
		return instance;
	}
	//�����л����ž��������󱻷����л�ʱ�������ɶ���
	private Object readResolve() throws ObjectStreamException {
		return instance;
	}

	// SimpleDateFormat�̲߳���ȫ���࣬ʹ��ThreadLocal,
	// Ҳ�ǽ����������Ϊ�����̶߳���϶��ܱȷ��������ڲ����������ܼ��ٲ��ٴ�������Ŀ��������������Ҫ��Ƚϸߵ�����£�һ���Ƽ�ʹ�����ַ�����
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 
	 * @��������ʽ��Stringת��ΪDate
	 * @param dateStr
	 * @return Date
	 * @throws ParseException
	 */
	public static Date parse(String dateStr) throws ParseException {
		return threadLocal.get().parse(dateStr);
	}

	/**
	 * 
	 * @��������date����ת��Ϊstring
	 * @param date
	 * @return ��ʽ��yyyy-MM-dd HH:mm:ss
	 */
	public static String format(Date date) {
		return threadLocal.get().format(date);
	}


}
