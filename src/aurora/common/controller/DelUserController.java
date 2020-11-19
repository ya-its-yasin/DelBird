package aurora.common.controller;

import aurora.master.controller.Autowired;
import aurora.master.controller.Logger;
import aurora.master.service.CountryService;

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
