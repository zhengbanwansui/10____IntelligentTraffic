package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtils {

//    /**
//     * 获取时间戳
//     * 输出结果:1438692801766
//     */
//    @Test
//    public void getTimeStamp() {
//        Date date = new Date();
//        long times = date.getTime();
//        System.out.println(times);
//
//        //第二种方法：
//        new Date().getTime();
//    }
//
//    /**
//     * 获取格式化的时间
//     * 输出格式：2015-08-04 20:55:35
//     */
//    @Test
//    public void getFormatDate(){
//        Date date = new Date();
//        long times = date.getTime();//时间戳
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(date);
//        System.out.println(dateString);
//    }
//
//    /**
//     * 将时间戳转化为标准时间
//     * 输出：Tue Oct 07 12:04:36 CST 2014
//     */
//    @Test
//    public void timestampToDate(){
//        long times = 1412654676572L;
//        Date date = new Date(times);
//        System.out.println(date);
//    }
}