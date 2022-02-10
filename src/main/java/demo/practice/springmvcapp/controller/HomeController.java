package demo.practice.springmvcapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Main controller for login and home
 */
@Controller
public class HomeController {

	private static final Logger _log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String landingPage(Model model) {
		_log.info("on landing Page");
		return "index";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model) throws Exception {
		return "home";
	}

	@RequestMapping(value = "login")
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/404")
	public String pageNotFound() {
		return "error/404";
	}

	@RequestMapping(value = "/500")
	public String error() {
		return "error/500";
	}

	/**
	 * Getting the user name of the logged in user
	 */
	private String getUserPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}

		return userName;
	}
}
