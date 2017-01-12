/**
 * 
 */
package br.com.cams7.app.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author César Magalhães
 *
 */
public interface BaseReportController {

	@RequestMapping(value = "/pdf", method = GET)
	public ModelAndView generatePdfReport(HttpServletRequest request);
}
