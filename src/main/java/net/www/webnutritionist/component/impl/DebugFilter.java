package net.www.webnutritionist.component.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import net.www.webnutritionist.filter.AbstractFilter;
import net.www.webnutritionist.util.DebugUtil;

@Component
public class DebugFilter extends AbstractFilter {

	public boolean isEnabledDebug(){
		return true;
	}
	
	public String[] getDebugUrl(){
		return new String[]{"/welcome"};
	}

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		try {
			LOGGER.info("**************************************************** start ***************************************************");
			DebugUtil.turnOnShowSQL();
			chain.doFilter(req, resp);
		} finally {
			DebugUtil.turnOffShowSQL();
			LOGGER.info("****************************************************  end  ***************************************************");
		}
	}
}
