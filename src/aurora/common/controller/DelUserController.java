package aurora.common.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import aurora.master.controller.CountryController;
import aurora.master.model.Country;
import aurora.common.model.DelUser;
import aurora.common.model.SystemSetup;
import aurora.common.model.User;
import aurora.common.model.UserLog;
import aurora.common.service.DelUserService;

@Controller
public class DelUserController {

	static Logger logger = Logger.getLogger(DelUserController.class);

	@Autowired
	private DelUserService delUserService;

	@RequestMapping(value = "addDelUser.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> addDelUser(HttpServletRequest request,
			HttpServletResponse response,  @ModelAttribute("delUser") DelUser delUser,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = delUserService.addDelUser(delUser, request);
		/*	map=countryService.getCountryByKey(country.getCntKey());
		map.put("message", message);
		*/
		return map;
	}	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "DelLoginValidation.do", method = RequestMethod.POST)
	public @ResponseBody String getDelValidateUser( HttpServletRequest request,
			HttpServletResponse response)  {
		
		String loginId = "", password = "";
		//PasswordPolicy passwordPolicy=new PasswordPolicy();
	    ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	    HttpSession session = attr.getRequest().getSession();
		JSONObject jsonObject = new JSONObject();
		try {
			
			loginId = (String) request.getParameter("loginId");
			password = (String) request.getParameter("password");
			
			// SystemSetup systemSetup = systemSetupService.getSystemSetup();
				
				// check login credentials with application login table
				List<DelUser>  DelList = DelUserService.getDelUserServ(loginId, password);
				
				if(DelList.size()>0)
				{
					jsonObject.put("keyword", "SUCCESS");
					jsonObject.put("data", DelList);
				}
				//Added password policy check to login
	/*			if(uList.size()>0){
					
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
			*/
				
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
	

}
