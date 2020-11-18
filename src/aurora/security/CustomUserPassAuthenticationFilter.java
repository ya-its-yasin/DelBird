package aurora.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomUserPassAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		final String examLoginValue = request.getParameter("examLogin");
        request.getSession().setAttribute("examLogin", examLoginValue);
        return super.attemptAuthentication(request, response); 
    } 
}
