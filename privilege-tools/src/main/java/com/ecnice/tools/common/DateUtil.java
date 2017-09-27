package com.ecnice.tools.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DateUtil {
	public static String formatDate(java.util.Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	public static String formatDateByFormat(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static java.util.Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.util.Date parseDate(String date) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(date);
	}

	public static java.util.Date parseDate(String date, String format)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.parse(date);
	}

	public static java.sql.Date parseSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static List<String> getDaysByWeek(java.util.Date date)
			throws Exception {
		date = DateUtil.getdate(DateUtil.format1(date));
		List<String> days = new ArrayList<String>();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		String firstWeek = DateUtil.getFirstWeekDay(date);
		for (int i = 0; i < 7; i++) {
			Date firstWeekDate = DateUtil.getdate1(firstWeek);
			firstWeekDate = DateUtil.addDate(firstWeekDate, i);
			int j = DateUtil.diffDate(date, firstWeekDate);
			if (j >= 0) {
				days.add(DateUtil.format1(firstWeekDate));
			} else {
				break;
			}
		}
		return days;
	}

	public static Date getNextWeek(java.util.Date date, int count) {
		Calendar strDate = Calendar.getInstance();
		strDate.setTime(date);
		strDate.add(strDate.DATE, count * 7);
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.set(strDate.get(Calendar.YEAR),
				strDate.get(Calendar.MONTH), strDate.get(Calendar.DATE));
		Date day = currentDate.getTime();
		return day;
	}

	/**
	 * 功能描述：根据日期获取天
	 * 
	 * @author 刘文军(bluesky.liu)
	 *         <p>
	 *         创建日期 ：2013-5-27 上午10:48:19
	 *         </p>
	 * 
	 * @param date
	 * @return
	 * 
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 */
	public static List<String> getDaysByDate(java.util.Date date) {
		List<String> days = new ArrayList<String>();
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		int dayss = DateUtil.getDay(date);
		String monthStr = DateUtil.format(date, "yyyy-MM");
		for (int i = 1; i <= dayss; i++) {
			String day = new String();
			if (i < 10) {
				day = monthStr + "-0" + i;
			} else {
				day = monthStr + "-" + i;
			}
			days.add(day);
		}
		return days;
	}

	/**
	 * 当前时间的周一
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getFirstWeekDay(java.util.Date theDate) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(theDate);
		// 取得本周一
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtil.format(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
	}

	/**
	 * 当前时间的周日
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getLastWeekDay(java.util.Date theDate) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(theDate);
		// 取得本周日
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return DateUtil.format(new Date(calendar.getTime().getTime()
				+ (6 * 24 * 60 * 60 * 1000)), "yyyy-MM-dd")
				+ " 23:59:59";
	}

	/**
	 * 当月第一天
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getFirstDay(java.util.Date theDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(day_first).append(
				" 00:00:00");
		return str.toString();
	}

	/**
	 * 功能描述：获取一月 month为1就是上一个月 为-1就是下一个月
	 * 
	 * @author 刘文军(bluesky.liu)
	 *         <p>
	 *         创建日期 ：2013-5-27 下午12:39:22
	 *         </p>
	 * 
	 * @param theDate
	 * @return
	 * 
	 *         <p>
	 *         修改历史 ：(修改人，修改时间，修改原因/内容)
	 *         </p>
	 * @throws ParseException
	 */
	@SuppressWarnings("unused")
	public static Date getUpMonth(java.util.Date theDate, int month)
			throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(theDate);
		c.add(Calendar.MONTH, month);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(c.getTime());
		return format.parse(time);
	}

	/**
	 * 当月最后一天
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getLastDay(java.util.Date theDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(theDate);
		gcLast.set(Calendar.DAY_OF_MONTH,
				gcLast.getActualMaximum(Calendar.DAY_OF_MONTH));
		String s = df.format(gcLast.getTime());
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();
	}

	public static String getMinDay(java.util.Date theDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s).append(" 00:00:00");
		return str.toString();
	}

	public static String getMaxDay(java.util.Date theDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String s = df.format(theDate);
		StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
		return str.toString();
	}

	public static String format(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String format1(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static int getYear(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.YEAR);
	}

	public static int getMonth(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	public static int getDay(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	public static int getHour(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.MINUTE);
	}

	public static int getSecond(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.SECOND);
	}

	public static long getMillis(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static int getWeek(java.util.Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.get(java.util.Calendar.WEEK_OF_YEAR);
	}

	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String getDate(java.util.Date date, String formatStr) {
		return format(date, formatStr);
	}

	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            Date
	 * @param day
	 *            int
	 * @return Date
	 */
	public static java.util.Date addDate(java.util.Date date, int day) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减(返回秒值)
	 * 
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 * @author
	 */
	public static Long diffDateTime(java.util.Date date, java.util.Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}

	public static java.util.Date getdate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}
	
	public static java.util.Date getDate(String date,String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	public static java.util.Date getJsonDate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return sdf.parse(date);
	}

	public static java.util.Date getdate1(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	public static java.util.Date getMaxTimeByStringDate(String date)
			throws Exception {
		String maxTime = date + " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(maxTime);
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = DateUtil.getDateTime(date);
		try {
			return sdf.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static String getCurrentDateTimeToStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentDateTime());
	}

	public static String getCurrentDateTimeToStr2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(getCurrentDateTime());
	}

	public static Long getWmsupdateDateTime() {
		Calendar cl = Calendar.getInstance();

		return cl.getTimeInMillis();
	}

	public static Integer getLeftSeconds(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date condition = sdf.parse(date);
		long n = condition.getTime();
		long s = sdf.parse(getCurrentDateTimeToStr2()).getTime();
		// System.out.println("开始时间:"+date+"-->"+(int)((s-n)/1000));
		return (int) ((s - n) / 1000);
	}

	/**
		* 功能描述：得到两个日期之间的日期list
		*
		* @author 刘文军(bluesky.liu)
		* <p>创建日期 ：2013-6-4 下午12:52:07</p>
		*
		* @param beginDate
		* @param endDate
		* @return
		*
		* <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
	 */
	public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
		List<Date> lDate = new ArrayList<Date>();
		lDate.add(beginDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(cal.getTime());
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}

	/**
	 * 日期加上几个月
	 * @param date
	 * @param month
	 * @return
	 * @Description:
	 * @author wentaoxiang 2016年5月11日 上午9:46:43
	 */
	public static Date addMonth(Date date,int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	public static void main(String[] args) throws Exception {
		System.err.println(new SimpleDateFormat("yyyy-MM-dd mm:HH:ss").format(addMonth(new Date(), 2)));

		List<Date> days = DateUtil.getDatesBetweenTwoDate(DateUtil.getdate("2013-01-09"), DateUtil.getdate("2013-01-11"));

		System.out.println(days.size());
	}
}
