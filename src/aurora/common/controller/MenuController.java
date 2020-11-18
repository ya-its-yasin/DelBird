package aurora.common.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import aurora.common.model.Menu;
import aurora.common.model.Module;
import aurora.common.service.MenuService;
import aurora.util.JsonReaderWriter;

@Controller
public class MenuController {
	static Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	
	@RequestMapping(value = "MenuGridData.do")
	public @ResponseBody
	List<JSONObject> getMenuGridData(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = menuService.getMenuList(request);
		return ar;
	}

	@RequestMapping(value = "ParentMenuCombo.do")
	public @ResponseBody
	List<JSONObject> getMenuComboList(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = menuService.getListForCombo(request);
		return ar;

	}

	@RequestMapping(value = "getMenuByKey.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getMenuByKey(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = new HashMap<String, Object>();
		map = menuService.getMenuByKey(request);
		return map;

	}

	@RequestMapping(value = "saveMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> saveMenu(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("menu") Menu menu,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.saveMenu(menu, request);
		return map;
	}

	@RequestMapping(value = "updateMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> updateMenu(HttpServletRequest request,
			HttpServletResponse response,@ModelAttribute("menu") Menu menu,BindingResult bindingResult) throws ParseException {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.updateMenu(menu, request);
		return map;
	}

	@RequestMapping(value = "deleteMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deleteMenu(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.deleteMenu(request);
		return map;
	}

	@RequestMapping(value = "getFirstMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getFirstMenu(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.getFirstMenu(request);
		return map;
	}

	@RequestMapping(value = "getLastMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getLastMenu(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.getLastMenu(request);
		return map;

	}

	@RequestMapping(value = "getNextMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getNextMenu(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.getNextMenu(request);
		return map;
	}

	@RequestMapping(value = "getPreviousMenu.do", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getPreviousMenu(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		Map<String, Object> map = menuService.getPreviousMenu(request);
		return map;
	}

	@RequestMapping(value = "MenuList.do")
	public @ResponseBody
	List<JSONObject> getMenuListForAccordion(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		List<JSONObject> ar = new ArrayList<JSONObject>();
		List<Menu> menuHeader = menuService.getMenuListForAccordion(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
		for(Menu menu:menuHeader){
			ar.add(JsonReaderWriter.getJSONObjectFromClassObject(menu));
		}
	//	List<JSONObject> ar = JsonReaderWriter.getJSONList(menuHeader) ;
		
		return ar;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MenuContent.do", method = RequestMethod.GET)
	public @ResponseBody
	List<JSONObject> getMenuContent(HttpServletRequest request,
			HttpServletResponse response) {
 		response.setHeader("Cache-Control", "no-cache");
		List<Menu> menuContent = menuService.getListByParentKey(
				Long.parseLong(request.getParameter("modulekey")),
				Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
		JSONArray arrayParent = new JSONArray();
		
		 
		
		
		for(int i=0;i<menuContent.size();i++)
		{
			
			JSONObject ar=new JSONObject();
			
			if(menuContent.get(i).getParentKey()==null)
			{
				  ar.put("moduleKey",menuContent.get(i).getModuleKey());	
				  ar.put("id",menuContent.get(i).getMenuKey());	
				  ar.put("text",menuContent.get(i).getNameP());	
			/*	  JSONArray arrayChild = new JSONArray();
				  for(int j=0;j<menuContent.size();j++)
					{
					  if(menuContent.get(j).getParentKey()!=null)
					  {
						
						if(menuContent.get(j).getParentKey().intValue() == menuContent.get(i).getMenuKey())
						{
							  JSONObject cl=new JSONObject();
							
							  cl.put("id",menuContent.get(j).getMenuKey());
							  cl.put("text",menuContent.get(j).getNameP());	
							
							  arrayChild.add(cl);
						}
					  }
						
					}*/
				  
			/*	  ar.put("children", arrayChild);*/
				  arrayParent.add(ar);
				  
			}
			
			
			
			
			/*if(ar.size()>0)
			{
				 arrayParent.add(ar);
			}
			*/
				
			
		}
		
	
		return arrayParent;

	}
	
	@RequestMapping(value="ModuleContent.do",method= RequestMethod.GET)
	public @ResponseBody List<JSONObject> getModuleContent(HttpServletRequest request,HttpServletResponse response) 
	{
		
		response.setHeader("Cache-Control", "no-cache");
		List<Module> modules= menuService.getModuleListForAccordian(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
		//ArrayList<JSONObject> ar= new ArrayList<JSONObject>();
		JSONArray jar=new JSONArray();
		for (Module m : modules) {
			//JSONObject json = JSONObject.fromObject(m);
		jar.add(JsonReaderWriter.getJSONObjectFromClassObject(m));
			//ar.add(	JsonReaderWriter.getJSONObjectFromClassObject(m));
		}
		
		
		return jar;
		
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="ModulePanel.do",method= RequestMethod.GET)
	public @ResponseBody List<JSONObject> getModulePanel(HttpServletRequest request,HttpServletResponse response) 
	{
		response.setHeader("Cache-Control", "no-cache");
		List<Menu> menuContent = menuService.getModulePanel(
				Long.parseLong(request.getParameter("modulekey")),
				Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
	
		JSONArray arrayParent = new JSONArray();
		
		 
		
		
		for(int i=0;i<menuContent.size();i++)
		{
			
			JSONObject ar=new JSONObject();
			
			if(menuContent.get(i).getParentKey()==null)
			{
				  	
				  ar.put("id",menuContent.get(i).getMenuKey());	
				  //ar.put("text",menuContent.get(i).getNameP());	
				  if(request.getSession().getAttribute("lang").toString().equalsIgnoreCase("en"))
				  {
					  ar.put("text",menuContent.get(i).getNameP());	
					  ar.put("desc",menuContent.get(i).getDescP());	
				  }
				  else
				  {
					  ar.put("text",menuContent.get(i).getNameS());	
					  ar.put("desc",menuContent.get(i).getDescS());	
				  }
				  
				  ar.put("keyword",menuContent.get(i).getKeyword());	
				  ar.put("url",menuContent.get(i).getUrl());	
				  ar.put("icon",menuContent.get(i).getMenuClass());	
				  
				  JSONArray arrayChild = new JSONArray();
				  for(int j=0;j<menuContent.size();j++)
					{
					  if(menuContent.get(j).getParentKey()!=null)
					  {
						
						if(menuContent.get(j).getParentKey().intValue() == menuContent.get(i).getMenuKey())
						{
							  JSONObject cl=new JSONObject();
							
							  cl.put("id",menuContent.get(j).getMenuKey());
							  
							 
							  if(request.getSession().getAttribute("lang").toString().equalsIgnoreCase("en"))
							  {
								  cl.put("text",menuContent.get(j).getNameP());	
								  cl.put("desc",menuContent.get(j).getDescP());	
							  }
							  else
							  {
								  cl.put("text",menuContent.get(j).getNameS());	
								  cl.put("desc",menuContent.get(j).getDescS());	
							  }
							  
							  cl.put("keyword",menuContent.get(j).getKeyword());	
							 // cl.put("url",menuContent.get(j).getUrl());	
							  cl.put("icon",menuContent.get(j).getMenuClass());	
							  JSONObject jourl=new JSONObject();
							  jourl.put("url",menuContent.get(j).getUrl());
							cl.put("attributes",jourl);
							  arrayChild.add(cl);
						}
					  }
						
					}
				  if(arrayChild.size()>0)
				  {
				  ar.put("children", arrayChild);
				  arrayParent.add(ar);
				  }
				  
			}
			
			
			
			
			/*if(ar.size()>0)
			{
				 arrayParent.add(ar);
			}
			*/
				
			
		}
		System.out.println(arrayParent);
		
		return arrayParent;

		
	
	
	}
	
	
	@RequestMapping(value="ModulePanel1.do",method= RequestMethod.GET)
	public @ResponseBody List<JSONObject> getModulePanel1(HttpServletRequest request,HttpServletResponse response) 
	{
		response.setHeader("Cache-Control", "no-cache");
		List<Menu> menuContent = menuService.getModulePanel1(
				Long.parseLong(request.getParameter("modulekey")),
				Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
	
		JSONArray arrayParent = new JSONArray();
		
		 
		
		
		for(int i=0;i<menuContent.size();i++)
		{
			
			JSONObject ar=new JSONObject();
			
			if(menuContent.get(i).getParentKey()==null)
			{
				  	
				  ar.put("id",menuContent.get(i).getMenuKey());	
				  //ar.put("text",menuContent.get(i).getNameP());	
				  if(request.getSession().getAttribute("lang").toString().equalsIgnoreCase("en"))
				  {
					  ar.put("text",menuContent.get(i).getNameP());	
					  ar.put("desc",menuContent.get(i).getDescP());	
				  }
				  else
				  {
					  ar.put("text",menuContent.get(i).getNameS());	
					  ar.put("desc",menuContent.get(i).getDescS());	
				  }
				  
				  ar.put("keyword",menuContent.get(i).getKeyword());	
				  ar.put("url",menuContent.get(i).getUrl());	
				  ar.put("icon",menuContent.get(i).getMenuClass());	
				  
				  JSONArray arrayChild = new JSONArray();
				  for(int j=0;j<menuContent.size();j++)
					{
					  if(menuContent.get(j).getParentKey()!=null)
					  {
						
						if(menuContent.get(j).getParentKey().intValue() == menuContent.get(i).getMenuKey())
						{
							  JSONObject cl=new JSONObject();
							
							  cl.put("id",menuContent.get(j).getMenuKey());
							  
							 
							  if(request.getSession().getAttribute("lang").toString().equalsIgnoreCase("en"))
							  {
								  cl.put("text",menuContent.get(j).getNameP());	
								  cl.put("desc",menuContent.get(j).getDescP());	
							  }
							  else
							  {
								  cl.put("text",menuContent.get(j).getNameS());	
								  cl.put("desc",menuContent.get(j).getDescS());	
							  }
							  
							  cl.put("keyword",menuContent.get(j).getKeyword());	
							 // cl.put("url",menuContent.get(j).getUrl());	
							  cl.put("icon",menuContent.get(j).getMenuClass());	
							  JSONObject jourl=new JSONObject();
							  jourl.put("url",menuContent.get(j).getUrl());
							cl.put("attributes",jourl);
							  arrayChild.add(cl);
						}
					  }
						
					}
				  if(arrayChild.size()>0)
				  {
				  ar.put("children", arrayChild);
				  arrayParent.add(ar);
				  }
				  
			}
			
			
			
			
			/*if(ar.size()>0)
			{
				 arrayParent.add(ar);
			}
			*/
				
			
		}
		System.out.println(arrayParent);
		
		return arrayParent;

		
	
	
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="MenuPanel.do",method= RequestMethod.GET)
	public @ResponseBody List<JSONObject> getMenContent(HttpServletRequest request,HttpServletResponse response) 
	{
		response.setHeader("Cache-Control", "no-cache");
		List<Menu> content = menuService.getMenuPanel(Long.parseLong(request.getParameter("menuKey")),
				Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
		
		JSONArray arrayParent = new JSONArray();
		
		if(content.size()>0)
		{
			for(int i=0;i<content.size();i++)
			{
				
				JSONObject ar=new JSONObject();
				
				if(content.get(i).getParentKey()!=null)
				{
					
					  ar.put("id",content.get(i).getMenuKey());	
					  if(request.getSession().getAttribute("lang").toString().equalsIgnoreCase("en"))
					  {
						  ar.put("text",content.get(i).getNameP());	
						  ar.put("desc",content.get(i).getDescP());	
					  }
					  else
					  {
						  ar.put("text",content.get(i).getNameS());	
						  ar.put("desc",content.get(i).getDescS());	
					  }
					  
					  ar.put("keyword",content.get(i).getKeyword());	
					  ar.put("url",content.get(i).getUrl());	
					  ar.put("icon",content.get(i).getMenuClass());	
					  arrayParent.add(ar);
					  
				}
				
			}
			
		}
		System.out.println(arrayParent);
		return arrayParent;

	}
	@RequestMapping(value = "swapMenuSeqNO.do", method = RequestMethod.POST)
	public @ResponseBody
	Boolean swapUserTypeSeqNO(HttpServletRequest request,
			HttpServletResponse response) {
		
		response.setHeader("Cache-Control", "no-cache");
		Boolean flag = false;
		try
		{
		Integer currentSeqNo = Integer.parseInt(request.getParameter("currentSeqNo").toString());
		Integer prevSeqNo = Integer.parseInt(request.getParameter("prevSeqNo").toString());
		
		flag = menuService.swapMenuHeadSeqNO(currentSeqNo, prevSeqNo);
		}
		catch(Exception e)
		{
			flag = false;
		}
		return flag;
	}
}
