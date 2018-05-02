package com.test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.test.dao.UserDao;
import com.test.dao.impl.UserDaoImpl;
import com.test.model.User;
import com.test.service.UserService;
import com.test.service.impl.UserServiceImpl;
import com.test.util.MyUtils;
import com.test.util.Static;

public class LoginFilter implements Filter {
	// 需要排除的页面
	private String excludePages;
	private String[] excludePageArray;
	// 定义一个users服务类
	private UserService userService ;
	

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(true);  
		Cookie[] cookies;
		boolean isExcludePage = false;

		for (int i = 0; i < excludePageArray.length; i++) {
			if (request.getServletPath().equals(excludePageArray[i])) {
				isExcludePage = true;
				break;
			}
		}
		// 获取用户 根据cookie或者在线session
		User user = (User)session.getAttribute(Static.onlineUser);
		if (user == null) {
			// 开始判断cookie
			cookies = request.getCookies();
			if(cookies!=null&&cookies.length>0){
				String cValue = getCookieValue(cookies, Static.onlineUser); //获取cookie值
				System.out.println(cValue);
				if(checkMD5(cValue)){
					 //验证成功 则跳转
					 if(isExcludePage){ //用户已登录 当他访问index.jsp时 直接跳转到main
						    response.sendRedirect("/inbox/main.jsp");
			    	    	arg2.doFilter(request, response);
			    	 }
				     arg2.doFilter(request, response);
				}else{
					 if(isExcludePage){
				    	 arg2.doFilter(request, response);
				     }else{
				    	 if(!request.getServletPath().equals("/inbox.jsp")){
			    	    		//排除 不需要登录也可以直接访问的界面
				    		 response.sendRedirect("/inbox/index.jsp");
				    		 arg2.doFilter(request, response);
			    	     }
				    	 arg2.doFilter(request, response);
				     } 
				}
			}else{
				 if(isExcludePage){
			    	 arg2.doFilter(request, response);
			     }else{
			    	 if(!request.getServletPath().equals("/inbox.jsp")){
		    	    		//排除 不需要登录也可以直接访问的界面
			    		 response.sendRedirect("/inbox/index.jsp");
			    		 arg2.doFilter(request, response);
		    	     }
			    	 arg2.doFilter(request, response);
			     } 
			}
			
		}else{ //session不为空
			 //验证成功 则跳转
			 if(isExcludePage){ //用户已登录 当他访问index.jsp时 直接跳转到main
				    response.sendRedirect("/inbox/main.jsp");
	    	    	arg2.doFilter(request, response);
	    	 }
		     arg2.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		excludePages = arg0.getInitParameter("excludePages");
		excludePageArray = excludePages.split(",");
		ServletContext sc = arg0.getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
		if(cxt != null && cxt.getBean("userService") != null && userService == null)
            userService = (UserService) cxt.getBean("userService");       
	}

	public String getCookieValue(Cookie[] cookies, String str) {
		String result = null;
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName());
			if (cookie.getName().equals(str)) {
				result = cookie.getValue();
				break;
			}
		}
		return result;
	}
	
	public boolean checkMD5(String value){
		if(value==null)
			return false;
		String[] msg = value.split(":");
		//获取用户信息
		User user = userService.getUserById(Integer.parseInt(msg[0]));
		String newHash = user.getId()+":"+user.getPassword()+":"+msg[1]+":"+user.getSalt();
		String newMD5 = MyUtils.getMD5(newHash);
		if(newMD5.equals(msg[2])){
			//计算后相等
			MyUtils.getSession().setAttribute(Static.onlineUser,user);
			return true;
		}
		return false;
	}
}
