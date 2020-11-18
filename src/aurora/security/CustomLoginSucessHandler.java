package aurora.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	
	 @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException {
            //authentication.getAuthorities() to check role
		 String role="";
		 Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
		 for (GrantedAuthority grantedAuthority:authorities){
			 if(grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_APPLICATIONUSER"))
				 role="welcome.do";
			 else  if(grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_EXAMLOGINUSER"))
				 role="Exam.do";
			
		 }
           
            response.sendRedirect(role);
      }
	
}
