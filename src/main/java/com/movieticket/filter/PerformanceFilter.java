package com.movieticket.filter;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class PerformanceFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		long start = System.currentTimeMillis();

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String method = httpRequest.getMethod();
		String uri = httpRequest.getRequestURI();

		System.out.println("==================================================");
		System.out.println("REQUEST START: " + method + " " + uri);

		try {

			chain.doFilter(request, response);

		} finally {

			long end = System.currentTimeMillis();

			System.out.println("REQUEST END: " + method + " " + uri);

			System.out.println("STATUS: " + httpResponse.getStatus());

			System.out.println("TOTAL REQUEST TIME: " + (end - start) + " ms");

			System.out.println("==================================================");
		}
	}
}