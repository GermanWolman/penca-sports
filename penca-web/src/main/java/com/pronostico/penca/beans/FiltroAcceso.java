package com.pronostico.penca.beans;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


//@WebFilter(filterName = "FilterAcceso", urlPatterns = "/*")
public class FiltroAcceso implements Filter {

	private static Logger LOG = Logger.getLogger(FiltroAcceso.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String path="";
		try {
			//LOG.info("Path en dofilter= "+path);
			HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
			path = request.getRequestURI();
			//LOG.info("Path en dofilter= "+path);
			Cookie[] cookie = request.getCookies();
			//System.out.println("ID de sesion: " +request.getSession().getId());
//			for (Cookie c: cookie) {
//				System.out.println(c.getName() + " - " + c.getValue());
//			}
			if(!path.matches(".*(css|jpg|png|gif|js|svg|ttf|ico)")){
//				LOG.info("primer if "+path);
				if(!path.contains("login.jsf") &&  !path.contains("registro.jsf")){
					if (path.contains("principal.jsf")){
						HttpSession sessionHttp = request.getSession(true);
						SesionBean sesion = (SesionBean) sessionHttp.getAttribute("sesionBean");
						if (sesion!=null && sesion.getUsuarioLogueado()!=null){
							sessionHttp.setAttribute("usuario", sesion.getUsuarioLogueado().getUsuario());
						}
					}
//					LOG.info("segundo if "+path);
					if (request.getSession() != null){
//						LOG.info("tercer if "+path);
						String username = (String) request.getSession()
							.getAttribute("usuario");
//						LOG.info("usuario:: "+username);
						SesionBean session = (SesionBean) request.getSession().getAttribute("sesionBean");
//						if (session!=null){
//							LOG.info("session bean no es null");
//						} else {
//							LOG.info("session bean es null");
//						}
						if (username == null || "".equals(username)) {	
//							LOG.info("usuario es null o vacio");
							((HttpServletResponse) response).sendRedirect("/penca-web/iniciarSesion/login.jsf");			
						}else{
							if (session!=null){
//								LOG.info("session no es null");
								session.setIntervalMaxSession(request.getSession().getMaxInactiveInterval());
							}
							chain.doFilter(request, response);
						}
					} else{
//						LOG.info("session es null - redirijo a login");
						((HttpServletResponse) response).sendRedirect("/penca-web/iniciarSesion/login.jsf");			
						chain.doFilter(request, response);
					}
				}else{
//					HttpSession sessionHttp = request.getSession(true);
//					SesionBean sesion = (SesionBean) sessionHttp.getAttribute("sesionBean");
//					if (sesion!=null && sesion.getUsuarioLogueado()!=null){
//						sessionHttp.setAttribute("usuario", sesion.getUsuarioLogueado().getUsuario());
//					}
					chain.doFilter(request, response);
				}
			} else{
				chain.doFilter(request, response);
			}
//			LOG.info("Fin dofilter= "+path);
		} catch (Exception e) {
			LOG.error("Error en el dofilter path: "+path, e);
			throw e;
		}
		
		

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	

}
