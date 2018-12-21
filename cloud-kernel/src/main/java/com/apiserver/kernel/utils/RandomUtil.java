package com.apiserver.kernel.utils;

import java.util.Random;

/**
 * @author jarvis
 */
public class RandomUtil {
	public static final char underline = '_';
	public static final String ALLCHAR = "_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";


	public static String base = "abcdefghijklmnopqrstuvwxyz0123456789";

	private static final String[] email_suffix="@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com,@live.com,@qq.com,@0355.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net,@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");

	public static int getNum(int start,int end) {
		return (int)(Math.random()*(end-start+1)+start);
	}

	/**
	 * 返回Email
	 * 为了保证唯一,加了时间戳
	 * @param lMin 前缀最小长度
	 * @param lMax 最大长度
	 * @return
	 */
	public static String getEmail(int lMin, int lMax) {
		int length=getNum(lMin,lMax);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = (int)(Math.random()*base.length());
			sb.append(base.charAt(number));
		}
		sb.append(DateUtils.getCurrentTime());
		sb.append(email_suffix[(int)(Math.random()*email_suffix.length)]);
		return sb.toString();
	}

	/**
	 * 返回手机号码
	 */
	private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
	private static String getTel() {
		int index=getNum(0,telFirst.length-1);
		String first=telFirst[index];
		String second= String.valueOf(getNum(1,888)+10000).substring(1);
		String thrid= String.valueOf(getNum(1,9100)+10000).substring(1);
		return first+second+thrid;
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 *
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		return generateString(length, true);
	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 *
	 * @param length
	 *            随机字符串长度
	 * @param includeULine 是否包含下划线
	 * @return 随机字符串
	 */
	public static String generateString(int length, Boolean includeULine) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		char temp;
		for (int i = 0; i < length; i++) {
			temp = ALLCHAR.charAt(random.nextInt(ALLCHAR.length()));
			if(!includeULine && temp == underline){
				i--;
				continue;
			}
			sb.append(temp);
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 *
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(LETTERCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	 *
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 *
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 *
	 * @param length
	 *            字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 *
	 * @param num
	 *            数字
	 * @param fixdlenth
	 *            字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
					+ "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 每次生成的len位数都不相同
	 *
	 * @param param
	 * @return 定长的数字
	 */
	public static int getNotSimple(int[] param, int len) {
		Random rand = new Random();
		for (int i = param.length; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = param[index];
			param[index] = param[i - 1];
			param[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + param[i];
		}
		return result;
	}

	/**
	 * 生成定长len位数值
	 * @param len
	 * @return
	 */
	public static int generateLenNum(int len) {
		Double temp = Math.pow(10,len-1);
		int result = (int) (Math.random() * 9 * temp.intValue()) + temp.intValue();
		return result;
	}

	/**
	 * 随机生成指定范围数字
	 * @param num 最大数
	 * @return
     */
	public static Integer generateAppointScopeNum(Integer num) {
		Random random=new Random();
		Integer result= random.nextInt(num);
		return result;
	}


	/**
	 * 随机生成2到99的数字
	 * @return Integer 随机生成2到99的结果
	 */
	public static Integer generateThreeToHundred(){
		Random random=new Random();
		Integer result= random.nextInt(97)+3;
		return result;
	}
}
