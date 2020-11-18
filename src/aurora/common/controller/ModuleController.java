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

import aurora.common.model.Module;
import aurora.common.service.ModuleService;

@Controller
public class ModuleController {
	static Logger logger = Logger.getLogger(ModuleController.class);

	@Autowired
	private ModuleService moduleService;

	

	@RequestMapping(value = "ModuleGridData.do")
	public @ResponseBody
	List<JSONObject> getModuleGridData(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = moduleService.getModuleList(request);
		return ar;
	}

	@RequestMapping(value = "ModuleCombo.do")
	public @ResponseBody
	List<JSONObject> getModuleComboList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = moduleService.getListForCombo(request);
		return ar;

	}

	@RequestMapping(value = "getModuleByKey.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getModuleByKey(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = new HashMap<String, Object>();
		map = moduleService.getModuleByKey(request);
		return map;

	}

	@RequestMapping(value = "saveModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveModule(HttpServletRequest request,
			HttpServletResponse response,  @ModelAttribute("module") Module module,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.saveModule(module, request);
		return map;
	}

	@RequestMapping(value = "updateModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateModule(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("module") Module module,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.updateModule(module, request);
		return map;
	}

	@RequestMapping(value = "deleteModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deleteModule(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.deleteModule(request);
		return map;
	}

	@RequestMapping(value = "getFirstModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getFirstModule(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.getFirstModule(request);
		return map;
	}

	@RequestMapping(value = "getLastModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getLastModule(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.getLastModule(request);
		return map;

	}

	@RequestMapping(value = "getNextModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getNextModule(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.getNextModule(request);
		return map;
	}

	@RequestMapping(value = "getPreviousModule.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getPreviousModule(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = moduleService.getPreviousModule(request);
		return map;
	}

}
