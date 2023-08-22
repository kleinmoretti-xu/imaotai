package com.xzc.imaotai.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 *
 * @author zhencx
 * @version 1.0
 */
public class DateUtil {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SPECIAL = "yyyyMMdd-HH_mm_ss";
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

    /**
     * get the current date
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * get the beginning of current date
     */
    public static Date getStartCurrentDate() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return localDateTimeToDate(start);
    }

    public static Date getStartCurrentDate(Date date) {
        LocalDateTime start = LocalDateTime.of(dateToLocalDate(date), LocalTime.MIN);
        return localDateTimeToDate(start);
    }

    /**
     * get the ending of current date
     */
    public static Date getEndCurrentDate() {
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return localDateTimeToDate(end);
    }

    public static Date getEndCurrentDate(Date date) {
        LocalDateTime end = LocalDateTime.of(dateToLocalDate(date), LocalTime.MAX);
        return localDateTimeToDate(end);
    }

    /**
     * get the previous date
     */
    public static Date getPreviousDate(long days) {
        LocalDate localDate = LocalDate.now().minusDays(days);
        return localDateToDate(localDate);
    }

    /**
     * get the previous minutes date
     */
    public static Date getPreviousMinutesDate(int minutes) {
        return localDateTimeToDate(LocalDateTime.now().minusMinutes(minutes));
    }

    /**
     * get the future date
     */
    public static Date getFutureDate(long days) {
        LocalDate localDate = LocalDate.now().plusDays(days);
        return localDateToDate(localDate);
    }

    /**
     * get the future date
     */
    public static Date getFutureMinutesDate(int minutes) {
        return localDateTimeToDate(LocalDateTime.now().plusMinutes(minutes));
    }

    /**
     * LocalDate => Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime => Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date => LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date => LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date => String
     */
    public static String dateToString(Date date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
    }

    /**
     * Date => String
     */
    public static String dateToString(Date date, DateTimeFormatter formatter) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
    }

    /**
     * Date => String
     */
    public static String dateToString(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DEFAULT_DATE_TIME_FORMATTER);
    }

    /**
     * dateString => Date
     */
    public static Date dateStringToDate(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * dateTimeString => Date
     */
    public static Date dateTimeStringToDate(String dateTimeStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * dateString => LocalDate
     */
    public static LocalDate dateStringToLocalDate(String dateStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, dateTimeFormatter);
    }

    /**
     * dateTimeString => LocalDate
     */
    public static LocalDateTime dateTimeStringToLocalDateTime(String dateTimeStr, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }

    public static boolean timeInterval(Date intervalDate, Date between, Date and) {
        return intervalDate.after(between) && and.after(intervalDate);
    }

    /**
     * 获取本周的开始时间
     */
    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getStartCurrentDate(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     */
    public static Date getEndDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getEndCurrentDate(weekEndSta);
    }

    /**
     * 获取本月的开始时间
     */
    public static Date getBeginDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        return getStartCurrentDate(calendar.getTime());
    }

    /**
     * 获取本月的结束时间
     */
    public static Date getEndDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1, day);
        return getEndCurrentDate(calendar.getTime());
    }

    /**
     * 获取本年的开始时间
     */
    public static Date getBeginDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return getStartCurrentDate(cal.getTime());
    }

    /**
     * 获取本年的结束时间
     */
    public static Date getEndDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, getNowYear());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        return getEndCurrentDate(cal.getTime());
    }

    /**
     * 获取今年是哪一年
     */
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    /**
     * 获取本月是哪一月
     */
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }

}