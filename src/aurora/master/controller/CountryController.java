package aurora.master.controller;

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

import aurora.master.service.CountryService;

@Controller
public class CountryController {

	static Logger logger = Logger.getLogger(CountryController.class);

	@Autowired
	private CountryService countryService;



	@RequestMapping(value = "CountryGridData.do")
	public @ResponseBody
	List<JSONObject> getCountryGridData(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = countryService.getCountryList(request);
		return ar;
	}
	
	@RequestMapping(value = "getCountryByKey.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getCountryByKey(HttpServletRequest request,
			HttpServletResponse response,long key) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = new HashMap<String, Object>();
		map = countryService.getCountryByKey(key);
		return map;

	}

	@RequestMapping(value = "getAllCountries.do")
	public @ResponseBody
	List<JSONObject> getAllCountries(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = countryService.getAllCountries(request);
		return ar;
	}
	
	@RequestMapping(value = "saveCountry.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveCountry(HttpServletRequest request,
			HttpServletResponse response,  @ModelAttribute("country") Country country,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = countryService.saveCountry(country, request);
		String message=map.get("message").toString();	
		if(country.getStatus()=='A')
		{			
			map=countryService.getCountryByKey(country.getCntKey());
			map.put("message", message);	
		}
		return map;
	}

	@RequestMapping(value = "updateCountry.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateCountry(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("country") Country country,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = countryService.updateCountry(country, request);
		String message=map.get("message").toString();	
		if(country.getStatus()=='A')
		{			
			map=countryService.getCountryByKey(country.getCntKey());
			map.put("message", message);	
		}
		return map;
	}

	@RequestMapping(value = "deleteCountry.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deleteCountry(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = countryService.deleteCountry(request);
		return map;
	}
}
