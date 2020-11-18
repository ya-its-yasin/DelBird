package aurora.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//import ccs.common.dao.PasswordPolicyDAO;
import aurora.common.dao.UserDao;
//import ccs.common.model.PasswordPolicy;
import aurora.common.model.SystemSetup;
import aurora.common.model.User;
import aurora.common.model.UserLog;
import aurora.common.service.SystemSetupService;
import aurora.common.service.UserService;


public class AuthenticationManagerExtended implements AuthenticationManager {

	private static Logger logger = Logger
			.getLogger(AuthenticationManagerExtended.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemSetupService systemSetupService;
	
	
	@Autowired
	private UserDao userDao;
	
	/*@Autowired
	PasswordPolicyDAO passwordPolicyDAO;*/
	

	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		logger.info("inside>>>>>>>>>>>>>>>>>>>>>>>>>AuthenticationManagerExtended>>>>>>>>>>>>>>>>>>>");
		String loginId = "", password = "";
		//PasswordPolicy passwordPolicy=new PasswordPolicy();
		UserLog log = new UserLog();
		List<?> list = new ArrayList();
		long loginAttempts=0;
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		
		HttpServletRequest httpServletRequest = attr.getRequest();
		HttpSession session = attr.getRequest().getSession();
		int access = 0;

		try {
			
			loginId = (String) authentication.getPrincipal();
			password = (String) authentication.getCredentials();
			
			 SystemSetup systemSetup = systemSetupService.getSystemSetup();
				
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
							session.setAttribute("user", uList.get(0));
							session.setAttribute("sysSetup", systemSetup);
							list=uList;
						}
						else{
							log.setActivityFlag("LF");
							log.setUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							log.setCreatedUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							userService.enterUserLog(log);
						}
						//passwordPolicy=passwordPolicyDAO.getPasswordPolicybyCompanyKey(uList.get(0).getUsrCompanyKey());
						
					/*if(passwordPolicy==null){
						passwordPolicy=systemSetup.getPasswordPolicy();
						
						
					}
					loginAttempts=passwordPolicy.getLoginAttempts();
				
					if(uList.get(0).getUserPassword().equals(encPassword)){
						long expDays=passwordPolicy.getExpiryDays();
						Boolean pwdExpired=userService.booleanPwdExpiredDet(uList.get(0).getUserKey(),expDays);
						
						
						
						if(passwordPolicy.getFirstLogonChange().equals('Y')){
							
							List<UserLog> userLogLogin=userService.getUserLoginLogListbyFlag(uList.get(0).getUserKey(), "LI",loginAttempts);
							if(userLogLogin.size()==0){
								uList.get(0).setPasswordPolicyResult("FLC");
							}
						}*/
						/*if(pwdExpired){
							
							uList.get(0).setPasswordPolicyResult("PWD_EXP");
							
						}*/
						
						
						
					/*}else{
						log.setActivityFlag("LF");
						log.setUser(Long.parseLong(uList.get(0).getUserKey() + ""));
						log.setCreatedUser(Long.parseLong(uList.get(0).getUserKey() + ""));
						userService.enterUserLog(log);
						
						//if(passwordPolicy.getLockOnFailure().equals('Y')){
						List<UserLog> loginFaliure=userService.getUserLoginLogListbyFlag(uList.get(0).getUserKey(), "LF",loginAttempts);
						long loginFalSize=loginFaliure.size();
							if(loginFalSize>=loginAttempts)
							{
							uList.get(0).setUsrLockYN('Y');
							userService.updateUserOnLoginFal(uList.get(0));
							log.setActivityFlag("LOCK");
							log.setUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							log.setCreatedUser(Long.parseLong(uList.get(0).getUserKey() + ""));
							log.setRemark("Locked Due to Incorret Password");
							userService.enterUserLog(log);
							
							throw new Exception("User Locked!");
							}
							
							long attemptsLeft=loginAttempts-loginFalSize;
							throw new Exception("Login Attempts left "+attemptsLeft);
					
						}	
						
						throw new Exception("The username or password you entered is incorrect !");
					}*/
					
					
					
					}
				}
			
		} catch (Exception e) {
		if(e.getMessage().contains("Cannot open connection")){
			
			throw new BadCredentialsException("Please Configure DB");
		}else{
			throw new BadCredentialsException(e.getMessage());
		}
			
		}
		
		if (access ==0 && list.size() <= 0) {
			
			logger.debug("Bad credentials");
			throw new BadCredentialsException("The username or password you entered is incorrect !");
		}
		if (access == 0) {
			logger.debug("UnAuthorized");
			throw new BadCredentialsException("UnAuthorized !");
		}

		 
		/*UserDetails user = new User(loginId, password, true, true, true, true,
				getAuthorities(access));*/
		/*Map<String, Object> sessionMap = new HashMap<String, Object>();
		String role = "welcome.do";
		sessionMap.put("1", role);*/
	
		Authentication authentication1 = new UsernamePasswordAuthenticationToken(
				authentication.getName(), authentication.getCredentials(),
				getAuthorities(access));
		SecurityContextHolder.getContext().setAuthentication(authentication1);

		
		
		return authentication1;// UsernamePasswordAuthenticationToken(user,
								// sessionMap ,getAuthorities(1));
	}

	@SuppressWarnings("deprecation")
	public Collection<GrantedAuthority> getAuthorities(Integer access) {
		// Create a list of grants for this user
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);

		// All users are granted with ROLE_USER access
		// Therefore this user gets a ROLE_USER by default
		if(access==1)
			authList.add(new SimpleGrantedAuthority("ROLE_APPLICATIONUSER"));
		
		
		
		return authList;
	}
}
