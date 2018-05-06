package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CheckLoginState
 */
@WebFilter("/CheckLoginState")
public class CheckLoginState implements Filter {

    /**
     * Default constructor. 
     */
    public CheckLoginState() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		Object user = null;
		System.out.print(req.getServletPath());
		if(req.getServletPath().equals("/jsp/login.jsp")||req.getServletPath().equals("/jsp/register.jsp")){
			chain.doFilter(request, response);
		}else{
			user = req.getSession().getAttribute("user");
			if(user==null){
				res.sendRedirect("/jdSYS/jsp/login.jsp");
			}else{
				chain.doFilter(request, response);
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
