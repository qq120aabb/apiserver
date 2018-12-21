package com.apiserver.kernel.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期工具方法类
 * @author jarvis  2018-3-20
 *
 */
public class DateUtils {
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm:ss");
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");

	/**
	 * 当年法定节假日列表
	 */
	private static Set<Date> holidays = new HashSet<Date>();
	/**
	 * 针对法定节假日的调班日期
	 */
	private static Set<Date> adjustWorkDays = new HashSet<Date>();

	// 初始化节假日和调班日列表
	static  {
		// 元旦3天
		for (int i = 1; i <= 3; i++) {
			holidays.add(generateDate(2016, 1, i));
		}
		// 农历春节
		adjustWorkDays.add(generateDate(2016, 2, 6));
		for(int i = 7; i <= 13; i++) {
			holidays.add(generateDate(2016, 2, i));
		}
		adjustWorkDays.add(generateDate(2016, 2, 14));
		// 清明节
		for (int i = 2; i <= 4; i++) {
			holidays.add(generateDate(2016, 4, i));
		}
		// 劳动节
		holidays.add(generateDate(2016, 4, 30));
		holidays.add(generateDate(2016, 5, 1));
		holidays.add(generateDate(2016, 5, 2));
		// 端午节
		for (int i = 9; i <= 11; i++) {
			holidays.add(generateDate(2016, 6, i));
		}
		adjustWorkDays.add(generateDate(2016, 6, 12));
		// 中秋节
		for (int i = 15; i <= 17; i++) {
			holidays.add(generateDate(2016, 9, i));
		}
		adjustWorkDays.add(generateDate(2016, 9, 18));
		// 国庆节
		for (int i = 1; i <= 7; i++) {
			holidays.add(generateDate(2016, 10, i));
		}
		adjustWorkDays.add(generateDate(2016, 10, 8));
		adjustWorkDays.add(generateDate(2016, 10, 9));
	}


	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}
	

	/**
	 * 获得当前日期时间
	 * <p>
	 * 日期时间格式yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String currentDateTime() {
		return dateTimeFormat.format(now());
	}



	public static Long getTimeStampByDate(Date date) {
		if(date == null){
			return -1l;
		}

		return date.getTime();
	}
	/**
	 * 获取指定时间的时间戳
	 * @param millis
	 * @return
	 */
	public static Timestamp getTimestamp(long millis)
	{
		return new Timestamp(millis);
	}
	
	public static Long getCurrentTime(){
		return now().getTime();
	}
	public static Timestamp getCurrentTimestamp(){
		return new Timestamp(getCurrentTime());
	}
	
	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}


	/**
	 * 格式化日期时间
	 * <p>
	 * 日期时间格式yyyy-MM-dd HH:mm:ss
	 *
	 * @return
	 */
	public static Date formatDateStr(String dateStr) {
		ParsePosition pos = new ParsePosition(0);
		return datetimeFormat.parse(dateStr,pos);
	}
	
	public static Date getDateByTimestamp(Long time){
		return new Date(time);
	}

	/**
	 * 格式化日期时间
	 * 
	 * @param date
	 * @param pattern
	 *            格式化模式，详见{@link SimpleDateFormat}构造器
	 *            <code>SimpleDateFormat(String pattern)</code>
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
				.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}

	/**
	 * 获得当前日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * 格式化日期
	 * <p>
	 * 日期格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 获得当前时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * 格式化时间
	 * <p>
	 * 时间格式HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}

	/**
	 * 获得当前时间的<code>java.util.Date</code>对象
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}
	
	
	/**
	 * 判断指定日期是否是调班日
	 * @param date
	 * @return
     */
	public static boolean isAdjustWorkDay(Date date) {
		if (date == null) {
			throw new NullPointerException("isAdjustWorkDay - parameter date is null.");
		}

		return adjustWorkDays.contains(date);
	}

	/**
	 * 判断指定日期是否是节假日
	 * @param date
	 * @return
     */
	public static boolean isHolidayWorkDay(Date date) {
		if (date == null) {
			throw new NullPointerException("isHolidayWorkDay - parameter date is null.");
		}

		return holidays.contains(date);
	}

	// 日期格式化常量
	/**
	 * 将日期格式为时间分钟，如09：23
	 */
	public static final String DateFormat_HHMM_COLON = "HH:mm";
	
	/**
	 * 将日期格式为年月日时间基本格式,如1970-01-01
	 */
	public static final String DateFormat_DATE_COLON = "yyyy-MM-dd";


	/**
	 * 将日期格式为年月日时间基本格式,如19700101
	 */
	public static final String DateFormat_DATE_COLON_NOLINE = "yyyyMMdd";
	
	/**
	 * 将日期格式为年月日时间基本格式,如1970-01-01 00:00
	 */
	public static final String DateFormat_FULL_COLON = "yyyy-MM-dd HH:mm";

	/**
	 * 将日期格式为年月日时间基本格式,如1970-01-01 00:00:00
	 */
	public static final String DateFormat_ALL_FULL_COLON = "yyyy-MM-dd HH:mm:ss";

	// 保存日期格式化对象的Map
	private static Map<String, SimpleDateFormat> dateFormatMap = new ConcurrentHashMap<String, SimpleDateFormat>();

	static {
		// 创建各日期格式的转换器对象
		dateFormatMap.put(DateFormat_HHMM_COLON, new SimpleDateFormat(DateFormat_HHMM_COLON));
		dateFormatMap.put(DateFormat_DATE_COLON_NOLINE, new SimpleDateFormat(DateFormat_DATE_COLON_NOLINE));
		dateFormatMap.put(DateFormat_DATE_COLON, new SimpleDateFormat(DateFormat_DATE_COLON));
		dateFormatMap.put(DateFormat_FULL_COLON, new SimpleDateFormat(DateFormat_FULL_COLON));
		dateFormatMap.put(DateFormat_ALL_FULL_COLON, new SimpleDateFormat(DateFormat_ALL_FULL_COLON));
	}

	public static Calendar getCalendarInstance() {
		return Calendar.getInstance();
	}
	
	public static Calendar getCalendarInstance(Date date) {
		Calendar calendar = getCalendarInstance();
		
		calendar.setTime(date);
		
		return calendar;
	}
	
	/**
	 * 获取日期部的指定部分
	 * @param date   来源日期对象
	 * @param partType  日期中的部分, 对应Calendar中的静态常量
	 * @return
	 */
	public static int getPartDate(Date date, int partType) {
		if (date == null) {
			throw new NullPointerException(
					"getDepartDate - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		
		return calendar.get(partType);
	}

	/**
	 * 获取指定日期是星期几
	 * @param date 日期
	 * @return 0为星期日，1为星期一，以此类推
	 */
	public static int getWeekDay(Date date) {
		return getPartDate(date, Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取指定日期所在周的第一天的日期
	 * @param date   日期
	 * @param sundayIsFirstDay 星期天是否是第一周的第一天
	 * @return  本周的第一天
     */
	public static Date getFirstWeekDay(Date date, boolean sundayIsFirstDay) {
		if (date == null) {
			throw new NullPointerException("getFirstWeekDay - parameter date is null.");
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int offsetDay = weekDay - 1;

		// 移到一周的第一天
		calendar.add(Calendar.DAY_OF_WEEK, - weekDay);

		// 如果星期天不是第一天则将第一天再向后加1天
		if (!sundayIsFirstDay) {
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}

		return calendar.getTime();
	}

	/**
	 * 获取指定日期所在周的最后一天的日期
	 * @param date   日期
	 * @param sundayIsFirstDay 星期天是否是第一周的第一天
	 * @return  本周的最后一天
	 */
	public static Date getLastWeekDay(Date date, boolean sundayIsFirstDay) {
		if (date == null) {
			throw new NullPointerException("getLastWeekDay - parameter date is null.");
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int offsetDay = weekDay - 1;

		// 移到一周的第一天
		calendar.add(Calendar.DAY_OF_WEEK, - weekDay);

		// 如果星期天不是第一天则将第一天再向后加1天
		if (!sundayIsFirstDay) {
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}

		// 将日期移到最后一天
		calendar.add(Calendar.DAY_OF_WEEK, 6);

		return calendar.getTime();
	}


	/**
	 * 获取指定日期所在周的一周日期列表
	 * @param date   日期
	 * @param sundayIsFirstDay 星期天是否是第一周的第一天
	 * @return  本周的所有天数列表
	 */
	public static List<Date> getWeekDays(Date date, boolean sundayIsFirstDay) {
		if (date == null) {
			throw new NullPointerException("getWeekDays - parameter date is null.");
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		int offsetDay = weekDay - 1;

		// 移到一周的第一天
		calendar.add(Calendar.DAY_OF_WEEK, - offsetDay);

		// 如果星期天不是第一天则将第一天再向后加1天
		if (!sundayIsFirstDay) {
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}

		List<Date> weekDays = new ArrayList<Date>();
		weekDays.add(calendar.getTime());

		for (int i = 1; i <= 6; i++) {
			calendar.add(Calendar.DAY_OF_WEEK, 1);
			weekDays.add(calendar.getTime());
		}

		return weekDays;
	}
	
	public static int getYear(Date date) {
		return getPartDate(date, Calendar.YEAR);
	}
	
	public static int getMonth(Date date) {
		return getMonth(date, false);
	}
	
	/**
	 * 获取指定日期中的月份
	 * @param date   日期
	 * @param naturalMonth  是否为自然月份，系统的月份值为0~11，自然月份为1~12
	 * @return
	 */
	public static int getMonth(Date date, boolean naturalMonth) {
		if (date == null) {
			throw new NullPointerException("getMonth - parameter date is null.");
		}
		
		int month = getPartDate(date, Calendar.MONTH);
		
		if (naturalMonth) {
			month += 1;
		}
		
		return month;
	}
	
	/**
	 * @return  当前月份，0~11
	 */
	public static int getMonth() {
		return getMonth(false);
	}
	
	/**
	 * 返回当前月份（1~12月)
	 * @param naturalMonth 是否是返回自然月份
	 * @return 月份值
	 */
	public static int getMonth(boolean naturalMonth) {
		int month = getCalendarInstance().get(Calendar.MONTH);
		
		if (naturalMonth) {
			month += 1;
		}
		
		return month;
	}
	
	public static int getDay(Date date) {
		return getPartDate(date, Calendar.DAY_OF_MONTH);
	}
	
	public static int getHour(Date date) {
		return getPartDate(date, Calendar.HOUR_OF_DAY);
	}
	
	public static int getMinute(Date date) {
		return getPartDate(date, Calendar.MINUTE);
	}
	
	public static int getSecond(Date date) {
		return getPartDate(date, Calendar.SECOND);
	}
	
	public static int getMillisecond(Date date) {
		return getPartDate(date, Calendar.MILLISECOND);
	}
	
	/**
	 * 返回指定日期中的月份，其中的月份为0~11的值
	 * @param date  日期对象
	 * @return  月份，如201509
	 */
	public static int getYearMonth(Date date) {
		return getYearMonth(date, true);
	}
	
	/**
	 * 返回指定日期中的月份
	 * @param date  日期对象
	 * @param naturalMonth  true返回的月份为1~12，否则为0~11
	 * @return 月份，如201509
	 */
	public static int getYearMonth(Date date, boolean naturalMonth) {
		if (date == null) {
			throw new NullPointerException("getYearMonth - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		
		return calendar.get(Calendar.YEAR) * 100 +
				 (naturalMonth ? calendar.get(Calendar.MONTH) + 1 : calendar.get(Calendar.MONTH));
	}
	
	public static int getYearMonth(boolean naturalMonth) {
		
		Calendar calendar = getCalendarInstance();
		
		return calendar.get(Calendar.YEAR) * 100 +
				 (naturalMonth ? calendar.get(Calendar.MONTH) + 1 : calendar.get(Calendar.MONTH));
	}
	
	public static int getYearMonth() {
		return getYearMonth(true);
	}
	
	/**
	 * 清除日期中的指定时间部分
	 * @param date  来源日期
	 * @param partType  日期某一部分的类型，对应Calendar中的常量
	 */
	public static Date clearPartDate(Date date, int partType) {
		Calendar calendar = getCalendarInstance();
		
		calendar.setTime(date);
		
		calendar.clear(partType);

		return calendar.getTime();
	}
	
	/**
	 * 清除日期中的时间部分
	 * @param date 来源日期
	 */
	public static Date clearTime(Date date) {
		Calendar calendar = getCalendarInstance();
		
		calendar.setTime(date);
		
		//calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		return calendar.getTime();
	}

	/**
	 * 将日期对象的日期部分还原为1970.1.1,只保留时间部分
	 * @param date   日期来源对象
	 * @return
	 */
	public static Date clearDate(Date date) {
		Calendar calendar = getCalendarInstance();

		calendar.setTime(date);

		/*
		calendar.clear(Calendar.YEAR);
		calendar.clear(Calendar.MONTH);
		calendar.clear(Calendar.DAY_OF_YEAR);
        */
		calendar.set(Calendar.YEAR, 1970);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_YEAR, 1);

		return calendar.getTime();
	}
	
	public static Date clearMillisecond(Date date) {
		return clearPartDate(date, Calendar.MILLISECOND);
	}
	
	public static Date clearSeconds(Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);

		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		
		return calendar.getTime();
	}


	public static Date clearMinutes(Date date) {
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);

		return calendar.getTime();
	}
	
	public static Date generateTime(int hour, int minute, int seconds) {
		Calendar calendar = getCalendarInstance();
		
		calendar.clear();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);
		
		return calendar.getTime();
	}
	
	public static Date generateTodayTime(int hour, int minute, int seconds) {
		Calendar calendar = getCalendarInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);
		
		return calendar.getTime();
	}

	/**
	 * 获取当月指定的时间
	 * @param day
	 * @param hour
	 * @param minute
	 * @param seconds
     * @return
     */
	public static Date generateDayTime(int day, int hour, int minute, int seconds) {
		Calendar calendar = getCalendarInstance();

		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);

		return calendar.getTime();
	}

	/**
	 * 获取当月指定的时间
	 * @param day
	 * @param hour
	 * @param minute
	 * @param seconds
	 * @return
	 */
	public static Date generateMonthDayTime(int month, int day, int hour, int minute, int seconds) {
		Calendar calendar = getCalendarInstance();

		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, seconds);

		return calendar.getTime();
	}
	
	public static Date generateDate(int year, int month, int day) {
		Calendar calendar = getCalendarInstance();
		
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		
		return calendar.getTime();
	}
	
	/**
	 * 在指定日期上的指定部分进行累加操作
	 * @param date   来源日期
	 * @param partType 需要累加的日期部分
	 * @param amount  累加值
	 */
	public static Date addDateByPartType(Date date, int partType, int amount) {
		if (date == null) {
			throw new NullPointerException(
					"addDateByPartType - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		
		calendar.setTime(date);
		calendar.add(partType, amount);

		return  calendar.getTime();
	}
	
	public static Date addMinutes(Date date, int minutes) {
		return addDateByPartType(date, Calendar.MINUTE, minutes);
	}

	public static Date addMilliseconds(Date date, int milliseconds) {
		return addDateByPartType(date, Calendar.MILLISECOND, milliseconds);
	}

	/**
	 * (date1>date2)=1,(date1==date2)=0,(date1<date2)=-1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2){
		if (date1 == null) {
			throw new NullPointerException("compareDate - parameter date1 is null.");
		}

		if (date2 == null) {
			throw new NullPointerException("compareDate - parameter date2 is null.");
		}
		if(date1.getTime() > date2.getTime()){
			return 1;
		}
		if(date1.getTime() < date2.getTime()){
			return -1;
		}

		return 0;
	}
	
	public static Date addDays(Date date, int days) {
		return addDateByPartType(date, Calendar.DAY_OF_MONTH, days);
	}
	
	public static Date addMonths(Date date, int months) {
		return addDateByPartType(date, Calendar.MONTH, months);
	}
	
	public static Integer addYearMonths(int yearMonth, int months, boolean naturalMonth) {
		Date date = DateUtils.getFirstDateOfMonth(yearMonth);
		date = DateUtils.addMonths(date, months);
		
		return DateUtils.getYearMonth(date, naturalMonth);
	}
	
	/**
	 * 使用指定年月日替换指定的日期中的对应部分
	 * @param srcDate   源日期对象, 被替换的日期
	 * @param newDate   新的日期对象，用来替换的日期
	 * @return  替换后的日期对象
	 */
	public static Date replaceDate(Date srcDate, Date newDate) {
		if (srcDate == null) {
			throw new NullPointerException("replaceDate - parameter srcDate is null.");
		}
		
		if (newDate == null) {
			throw new NullPointerException("replaceDate - parameter newDate is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(newDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		calendar.clear();
		calendar.setTime(srcDate);
		calendar.set(year, month, day);
		
		return calendar.getTime();
	}
	
	/**
	 * 判断指定日期是否为当天
	 * @param date  来源日期
	 * @return  true当天，false不是当天
	 */
	public static boolean isToday(Date date) {
		if (date == null) {
			throw new NullPointerException("isToday - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		
		calendar.setTime(date);
		
		return year == calendar.get(Calendar.YEAR) &&
				month == calendar.get(Calendar.MONTH) &&
				day == calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 判断两个日期是否同一天
	 * @param date1   比较的第一个日期
	 * @param date2  比较的第二个日期
	 * @return true相同，false不相同
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null) {
			throw new NullPointerException("isToday - parameter date1 is null.");
		}
		
		if (date2 == null) {
			throw new NullPointerException("isToday - parameter date2 is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date1);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		
		calendar.setTime(date2);
		
		return year == calendar.get(Calendar.YEAR) &&
				month == calendar.get(Calendar.MONTH) &&
				day == calendar.get(Calendar.DAY_OF_YEAR);
		
	}
	
	/**
	 * 判断指定日期是否当前月份
	 * @param date  日期
	 * @return  true 当前月份，false非当前月份
	 */
	public static boolean isCurrMonth(Date date) {
		if (date == null) {
			throw new NullPointerException("isCurrMonth - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		
		calendar.setTime(date);
		
		return calendar.get(Calendar.YEAR) == year &&
				calendar.get(Calendar.MONTH) == month;
	}
	
	/**
	 * 判断指定日期是否当前月份
	 * @param yearMonth  月份
	 * @return  true 当前月份，false非当前月份
	 */
	public static boolean isCurrMonth(Integer yearMonth) {
		if (yearMonth == null) {
			throw new NullPointerException("isCurrMonth - parameter yearMonth is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		
		return year * 100 + month == yearMonth;
	}
	
	/**
	 * @return  当月的第一天的日期
	 */
	public static Date getFirstDateTimeOfCurrentMonth() {
		Calendar calendar = getCalendarInstance();
		
		// 将日期设置为第一天
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		return calendar.getTime();
	}
	
	public static Date getFirstDateOfCurrentMonth() {
		Date firstDate = getFirstDateTimeOfCurrentMonth();
		
		return clearTime(firstDate);
	}
	
	/**
	 * 获取指定月份的第一天
	 * @param month   月份，其格式为201509
	 * @return  当月的第一天日期
	 */
	public static Date getFirstDateOfMonth(Integer month) {
		if (month == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter month is null.");
		}
		
		int y = month / 100;		
		int m = month % 100 - 1;
		if (m < 0 || m > 11) {
			throw new IllegalStateException("非法的月份：" + m);
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, y);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		return calendar.getTime();
	}
	
	public static Date getFirstDateOfMonth(Date date) {
		if (date == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		return calendar.getTime();
	}

	/**
	 * 获取当月第一天0：00
	 * @param date
	 * @return
     */
	public static Date getFirstDateOfMonthOnTime(Date date) {
		if (date == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter date is null.");
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);

		return calendar.getTime();
	}

	/**
	 * 获取当月最后一天0：00
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonthOnTime(Date date) {
		if (date == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter date is null.");
		}

		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		// 设置到当月1号
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 加一个月
		calendar.add(Calendar.MONTH, 1);
		// 再减1天即为上个月的最后一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);

		return calendar.getTime();
	}
	
	public static Date getLastDateOfMonth(Integer month) {
		if (month == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter month is null.");
		}
		
		int y = month / 100;		
		int m = month % 100 - 1;
		if (m < 0 || m > 11) {
			throw new IllegalStateException("非法的月份：" + m);
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, y);
		calendar.set(Calendar.MONTH, m);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		// 加一个月
		calendar.add(Calendar.MONTH, 1);
		
		// 再减1天即为上个月的最后一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		return calendar.getTime();		
	}
	
	public static Date getLastDateOfMonth(Date date) {
		if (date == null) {
			throw new NullPointerException("getFirstDateOfMonth - parameter date is null.");
		}
		
		Calendar calendar = getCalendarInstance();
		calendar.setTime(date);
		// 设置到当月1号
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		// 加一个月
		calendar.add(Calendar.MONTH, 1);
		
		// 再减1天即为上个月的最后一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		return calendar.getTime();		
	}

	/**
	 * 将日期格式化成指定的格式字符串
	 * @param format   对应静态常量
	 * @param date     需要格式化的来源日期对象
	 * @return    格式化后的字符串
	 */
	public static String formatDate(String format, Date date) {
		if (StringUtils.isBlank(format)) {
			throw new IllegalArgumentException("formatDate - parameter format is null or empty.");
		}

		if (date == null) {
			throw new NullPointerException("formatDate - parameter date is null.");
		}

		SimpleDateFormat sdf = dateFormatMap.get(format);
		if (sdf == null) {
			throw new IllegalStateException("未找到指定格式【"+ format + "】对应的日期格式化对象。");
		}

		return sdf.format(date);
	}
	
	/**
	 * 将日期格式的字符串对象转化成日期对象
	 * @param format   对应静态常量
	 * @param dateStr     需要格式化的来源字符串对象
	 * @return    格式化后的日期
	 */
	public static Date stringToDate(String format, String dateStr) {
		if (StringUtils.isBlank(format)) {
			throw new IllegalArgumentException("formatDate - parameter format is null or empty.");
		}

		if (StringUtils.isBlank(dateStr)) {
			throw new NullPointerException("formatDate - parameter date is null.");
		}

		SimpleDateFormat sdf = dateFormatMap.get(format);
		if (sdf == null) {
			//throw new IllegalStateException("未找到指定格式【"+ format + "】对应的日期格式化对象。");
            sdf = new SimpleDateFormat(format);
            dateFormatMap.put(format, sdf);
		}
		Date formatDate = null;
		try {
			formatDate = sdf.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("使用格式字符串【"+ format + "】格式化来源日期【" + dateStr +"】发生错误。");
		}
		return formatDate;
	}
	
	
	public static Date today() {
		return clearTime(new Date());
	}
	
	/**
	 * 在指定日期时间区间生成一个随机生日期
	 * @param beginDate   开始日期
	 * @param endDate     结束日期
	 * @return  随机日期
	 */
	public static Date getRandomDateTime(Date beginDate, Date endDate) {
		if (beginDate == null) {
			throw new NullPointerException("getRandomDateTime - parameter beginDate is null.");
		}
		
		if (endDate == null) {
			throw new NullPointerException("getRandomDateTime - parameter endDate is null.");
		}
		
		Random random = new Random();
		long time = random.nextInt((int)(endDate.getTime() - beginDate.getTime() + 1)) + beginDate.getTime();
		
		return new Date(time);
	}

	/**
	 * 把毫秒数转换为日期格式
	 * @param signedDate
	 * @return
	 */
	public static String getDate(String signedDate){
		Long signedTime = Long.valueOf(signedDate);
		Date date = new Date(signedTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 把毫秒数转换为日期格式
	 * @param signedDate
	 * @param isClearTime true：清除时间部分； false:不清除
	 * @return
	 */
	public static Date getDate(String signedDate, Boolean isClearTime) throws ParseException {
		if (StringUtils.isBlank(signedDate)) {
            return null;
        }

		Long signedTime = Long.valueOf(signedDate);
		Date date = new Date(signedTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		Date date1 = sdf.parse(dateStr.toString());
		if (isClearTime) {
            date1 = clearTime(date1);
        }

		return date1;
	}


    /**
	 * 增加或减少日期中的时间部分（24小时制 ）
	 * @param date 日期
	 * @param hour 小时
	 * @return
     */
	public static Date addDayOfHour(Date date, int hour) {
		Calendar calendar = getCalendarInstance();

		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);

		return  calendar.getTime();
	}


	/**
	 * 增加或减少日期中的时间部分（12小时制）
	 * @param date 日期
	 * @param hour 小时
	 * @return
	 */
	public static Date addDayHour(Date date, int hour) {
		Calendar calendar = getCalendarInstance();

		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);

		return  calendar.getTime();
	}

	/**
	 *
	 * @param beginTime 计算的开始时间
	 * @param endTime    计算的结束时间
	 * @return  时间差值
	 */
	public static String getSpendTime(Date beginTime, Date endTime){
		if(beginTime == null&&endTime == null){
			return null;
		}
		Long beginNum = beginTime.getTime();
		Long endNum = endTime.getTime();
		if(endNum < beginNum){
			throw new IllegalArgumentException("getSpendTime enTime should bigger than beginTime.");
		}
		Long yy=endNum-beginNum;
		return  yy.toString();
	}
	/**
	 *
	 * @param beginTime 计算的开始时间
	 * @param endTime    计算的结束时间
	 * @return  时间差值
	 */
	public static Long getSpendTimeWithLong(Date beginTime, Date endTime){
		if(beginTime == null&&endTime == null){
			return null;
		}
		Long beginNum = beginTime.getTime();
		Long endNum = endTime.getTime();
		if(endNum < beginNum){
			throw new IllegalArgumentException("getSpendTime enTime should bigger than beginTime.");
		}
		Long yy=endNum-beginNum;
		return  yy;
	}

    /**
     * 将日期和时间合并为日期时间
     * @param date  来源日期
     * @param time  来源时间
     * @return  合并后的日期时间对象
     */
	public static Date mergeDateTime(Date date, Date time) {
        if (date == null) {
            throw new IllegalArgumentException("mergeDateTime - param data is null.");
        }

        if (time == null) {
            throw new IllegalArgumentException("mergeDateTime - param time is null.");
        }

        Calendar calendar = DateUtils.getCalendarInstance(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(time);
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
