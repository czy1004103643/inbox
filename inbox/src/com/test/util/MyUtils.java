package com.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 我的工具类
 * 
 * @author xiongcheng
 *
 */
public class MyUtils {

	/**
	 * 获取struts2封装的的HttpSession
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 获取struts2封装的的HttpServletResponse
	 * 
	 * @return
	 */
	public static HttpServletResponse getRes() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 获取struts2封装的的HttpServletRequest
	 * 
	 * @return
	 */
	public static HttpServletRequest getReq() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 快捷获取hashmap
	 * 
	 * @author Jhon
	 * @return
	 */
	public static Map<String, Object> getHashMap() {
		return new HashMap<String, Object>();
	}

	/**
	 * 快捷获取ArrayList
	 * 
	 * @author Jhon
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getArrayList() {
		return new ArrayList<T>();
	}

	/**
	 * 判断当前会话obj是否在线
	 * 
	 * @return
	 */
	public static Boolean isObjectOnline(String sessionObjName) {
		/**
		 * 1.获取session中的obj对象 2.判断对象是否为空 -返回判断结果
		 */
		// 1.获取session中的obj对象
		Object onlineObj = MyUtils.getSession().getAttribute(sessionObjName);
		// 2.判断对象是否为空
		if (onlineObj == null) {
			return false;
		}
		return true;
	}

	/**
	 * 获取当前会话中指定的T对象
	 * 
	 * @return
	 */
	public static <T> T getSessionObject(String sessionObjName) {
		return (T) MyUtils.getSession().getAttribute(sessionObjName);
	}

	/**
	 * 返回json对象
	 * 
	 * @param o
	 */
	public static void writeJSON(Object o) {
		PrintWriter pt = null;
		try {
			/* 获得response */
			HttpServletResponse response = ServletActionContext.getResponse();
			/* 设置格式为text/json */
			response.setContentType("text/json");
			/* 设置字符集为'UTF-8' */
			response.setCharacterEncoding("UTF-8");
			pt = response.getWriter();
			// 序列化对�?
			String json = JSON.toJSONStringWithDateFormat(o,
					"yyyy-MM-dd HH:mm:ss", SerializerFeature.PrettyFormat);
			// 写入对象
			pt.write(json);
			pt.flush();
			pt.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭
			pt.close();
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param source
	 *            ：需要写入的文件
	 * @param dir
	 *            ： 写入的目录
	 * @param fileName
	 *            ： 新的文件名
	 */
	public static void writeFile(File sourceFile, String dir, String fileName) {
		// 保存文件
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(sourceFile);
			// 设置目标文件
			File toFile = new File(dir, fileName);
			// 创建一个输出流
			os = new FileOutputStream(toFile);
			// 写入
			IOUtils.write(IOUtils.toByteArray(is), os);
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			MyUtils.closeStream(is, os);
		}

	}

	/**
	 * 删除文件
	 * 
	 * @param dir
	 * @param fileName
	 */
	public static void deleteFile(String dir, String fileName) {
		File file = new File(dir, fileName);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 关闭流
	 * 
	 * @param is
	 * @param os
	 */
	public static void closeStream(InputStream is, OutputStream os) {
		/**
		 * 1.判断流是否为null 2.执行操作
		 */
		// 1.判断流是否为null
		if (is != null) {
			// 2.执行操作
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 1.判断流是否为null
		if (os != null) {
			// 2.执行操作
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeImg(File imgFile) {
		// 获取respone对象
		HttpServletResponse response = null;
		// 声明输出流
		OutputStream os = null;
		// 声明输入流
		FileInputStream fis = null;
		try {
			// 获取respone
			response = ServletActionContext.getResponse();
			// 获取响应输出流
			os = response.getOutputStream();
			// 文件获取流
			fis = new FileInputStream(imgFile);
			response.setHeader("Content-Type", "image/jped");// 设置响应的媒体类型，这样浏览器会识别出响应的是图片
			// 写入数据
			os.write(IOUtils.toByteArray(fis));
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.closeStream(fis, os);
		}
	}

	/**
	 * 返回JSON信息 状态符 给前台
	 * 
	 * @param msg
	 * @param flag
	 */
	public static void outMsg(String msg, boolean flag) {
		JSONObject jo = new JSONObject();
		jo.put("msg", msg);
		jo.put("status", flag);
		MyUtils.writeJSON(jo);
	}

	/**
	 * 加密 用户的 cookie值 第一步 拼接hash id:password:currentTime:key 第二步 加密hash md5加密
	 * 第三步 得到cookie id:nickname:currentTime:md5(hash)
	 * 
	 * @param id
	 *            用户id
	 * @param password
	 *            用户密码
	 * @param key
	 *            密匙
	 * @param nickName
	 *            用户名称 或者 昵称 便于显示
	 * @return
	 */
	public static String encodeCookie(int id, String password, String key) {
		// 获取当前的时间戳
		long currentTime = System.currentTimeMillis();
		String hash = id + ":" + password + ":" + currentTime + ":" + key;
		String md5Hash = MyUtils.getMD5(hash); // 获取加密hash值
		// 得到cookie
		String cookieValue = id + ":" + currentTime + ":" + md5Hash;
		return cookieValue;
	}

	/**
	 * 添加cookie 时间固定为3600
	 * 
	 * @param key
	 * @param value
	 */
	public static void addCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(3600);
		cookie.setPath("/inbox");
		MyUtils.getRes().addCookie(cookie);
	}

	/**
	 * MD5加密
	 * 
	 * @param message
	 *            要进行MD5加密的字符串
	 * @return 加密结果为32位字符串
	 */
	public static String getMD5(String message) {
		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = new StringBuffer();
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(message.getBytes("UTF-8"));

			byte[] byteArray = messageDigest.digest();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return md5StrBuff.toString().toUpperCase();// 字母大写
	}

	/**
	 * 计算文件的MD5值
	 * 
	 * @param file
	 * @return
	 */
	public static String getMd5ByFile(File file) {
		String result = null;
		if (null == file)
			return null;
		try {
			FileInputStream fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = fis.read(buffer, 0, 1024)) != -1) {
				md.update(buffer, 0, length);
			}
			BigInteger bigInt = new BigInteger(1, md.digest());
			result = bigInt.toString(16);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 产生随机的六位数
	 * 
	 * @return
	 */
	public static String getSix() {
		Random rad = new Random();

		String result = rad.nextInt(1000000) + "";

		if (result.length() != 6) {
			return getSix();
		}
		return result;
	}

	/**
	 * 字节数转为单位
	 * 
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte(s)";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

	/**
	 * 生成 length长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 根据Value取Key
	public static Object getKeyByValue(Map map, Object value) {
		for (Object key : map.keySet())
			if (map.get(key).equals(value))
				return key;
		return null;
	}

	/***
	 * textarea的段落文章 存储到数据库 进行字符替换
	 * 
	 * @param value
	 * @return
	 */
	public static String textareaToSql(String value) {
		value = value.replace("\n", "<br>");
		value = value.replace(" ", "&nbsp;");
		return value;
	}

	/**
	 * 和上面的方法相反
	 * 
	 * @param value
	 * @return
	 */
	public static String sqlTotextarea(String value) {
		value = value.replace("<br>", "\n");
		value = value.replace("&nbsp;", " ");
		return value;
	}

	public static void main(String[] args) {

	}
}
