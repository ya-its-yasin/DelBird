package aurora.common.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import aurora.common.dao.UserDao;
import aurora.common.model.SystemSetup;
import aurora.common.model.User;
import aurora.common.model.UserLog;
import aurora.common.service.SystemSetupService;
import aurora.common.service.UserService;





@Controller
public class LoginController {

	static Logger logger = Logger.getLogger(LoginController.class);
	
	
	

	@RequestMapping(value = "loginfailed.do")
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "/common/LoginForm";
	}

	@RequestMapping(value = "login.do")
	public String login(ModelMap model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
		return "/common/LoginForm";
	}
	/*@RequestMapping(value = "DelRegister.do")
	public String DelRegUser(ModelMap model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
		return "/common/RegDelUser";
	}*/

	@RequestMapping(value = "welcome.do")
	public String processForm(ModelMap model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
	
		if(httpSession != null && !httpSession.isNew()) {
			return "/common/Home";
		} else {
			return "/common/LoginForm";
		}
		
		

	}
	@RequestMapping(value = "ClientDashBoard.do")
	public String processClientForm(ModelMap model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
	
		if(httpSession != null && !httpSession.isNew()) {
			return "/masters/clientDashboard";
		} else {
			return "/common/LoginForm";
		}
		
		

	}
	@RequestMapping(value = "ContractorDashBoard.do")
	public String ContractorDashBoard(ModelMap model, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
	
		if(httpSession != null && !httpSession.isNew()) {
			return "/masters/contractorDashboard";
		} else {
			return "/common/LoginForm";
		}
		
		

	}
	
	@Autowired
	private UserService userService;
	
	/*@Autowired
	private SystemSetupService systemSetupService;
	*/
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SystemSetupService systemSetupService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "LoginValidation.do", method = RequestMethod.POST)
	public @ResponseBody String getValidateUser( HttpServletRequest request,
			HttpServletResponse response)  {
		
		String loginId = "", password = "";
		//PasswordPolicy passwordPolicy=new PasswordPolicy();
		UserLog log = new UserLog();
		List<?> list = new ArrayList();
		long loginAttempts=0;
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession();
		int access = 0;
		JSONObject jsonObject = new JSONObject();
		try {
			
			loginId = (String) request.getParameter("loginId");
			password = (String) request.getParameter("password");
			
			// SystemSetup systemSetup = systemSetupService.getSystemSetup();
				
				// check login credentials with application login table
				List<User>  uList = userService.getUsers(loginId, password);
				
				//Added password policy check to login
				String encPassword=userService.getEncryptPassword(password);
				if(uList.size()>0){
					
					if(uList.get(0).getUsrLockYN()!=null && uList.get(0).getUsrLockYN().equals('Y')){
						
						throw new Exception("User is Locked ! Cannot Login");
						
					}else{
						if(uList.get(0).getUserPassword().equals(encPassword)){
							access = 1;
							jsonObject.put("keyword", "SUCCESS");
							jsonObject.put("data", uList);
							 SystemSetup objSysSetup = systemSetupService.getSystemSetup();
							 User user=uList.get(0);
							 session.setAttribute("loginUser", user.getLoginId());
								session.setAttribute("currentUserKey", user.getUserKey());
								session.setAttribute("currentUserType", user.getUserType());
								session.setAttribute("currentRefId", user.getRefID());
								session.setAttribute("currentUserEmailID", user.getEmailId());
							 session.setAttribute("bilingual", objSysSetup.getSsBilingualYN());
							 
							 if(objSysSetup.getSsBilingualYN()=='Y')
							{
								session.setAttribute("langp", objSysSetup.getSlLangP());
								session.setAttribute("langs", objSysSetup.getSlLangS());
								
							}
							else  
							{
								session.setAttribute("langp", objSysSetup.getSlLangP());
							}
							// if(session.getAttribute("lang")==null)
							// {
								 session.setAttribute("lang", objSysSetup.getSlLangP().getSlCode());
							// }
							 jsonObject.put("message", "welcome.do?lang="+session.getAttribute("lang"));
							 session.setAttribute("themeName",user.getUserThemeName());
								session.setAttribute("userHomePageAttr", user.getUserHomePageAttr());
								
								session.setAttribute("userDefaultModule",user.getUserDefaultModule());
								
								String lastLoginTime = userService.getLastLogOnTime(Long
										.parseLong(user.getUserKey() + ""));
								session.setAttribute("lastLogOnTime",lastLoginTime);
						}
						else{
							//log.setActivityFlag("LF");
							//log.setUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							//log.setCreatedUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							//userService.enterUserLog(log);
							jsonObject.put("keyword", "FAIL");
							jsonObject.put("message", "The username or password you entered is incorrect !");
						}
					
					
					
					
					}
				}
			
		} catch (Exception e) {
		
			if(e.getMessage().contains("Cannot open connection")){
			
			throw new BadCredentialsException("Please Configure DB");
		}else{
			throw new BadCredentialsException(e.getMessage());
		}
			
		}	
		
		/*if (access ==0 && list.size() <= 0) {
			
			logger.debug("Bad credentials");
			throw new BadCredentialsException("The username or password you entered is incorrect !");
			
		}
		if (access == 0) {
			logger.debug("UnAuthorized");
			throw new BadCredentialsException("UnAuthorized !");
		}*/

		//return "welcome.do";
		return jsonObject.toString();
		
	}
	
	


	@RequestMapping(value = "checkConnection.do", method = RequestMethod.GET)
	public @ResponseBody
	String CheckConnection(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		if (request.getSession().getAttribute("currentUserKey") != null) {
			return "ACTIVE";
		} else {

			return "INACTIVE";
		}

	}
	
	
	
	
	

	
	
	
	
}
