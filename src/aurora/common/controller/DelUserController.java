package aurora.common.controller;

import java.text.ParseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aurora.master.controller.CountryController;
import aurora.master.model.Country;
import aurora.common.model.DelUser;
import aurora.common.service.DelUserService;

@Controller
public class DelUserController {

	static Logger logger = Logger.getLogger(DelUserController.class);

	@Autowired
	private DelUserService delUserService;

	@RequestMapping(value = "addUser.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> addUser(HttpServletRequest request,
			HttpServletResponse response,  @ModelAttribute("delUser") DelUser delUser,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = delUserService.addDelUser(delUser, request);
		String message=map.get("message").toString();	
		
	/*	map=countryService.getCountryByKey(country.getCntKey());
		map.put("message", message);
		*/
		return map;
	}	


}
