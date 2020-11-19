package aurora.common.service;

@Service
public class DelUserService {
	
	
	static Logger logger = Logger.getLogger(DelUserService.class);

	@Autowired
	private DelUserDAO DelUserDAO;
	
	@Autowired
	MessageSource messageSource;
	
	@Transactional
	public Map<String, Object> addDelUser(DelUser delUser,
			HttpServletRequest request) {
		DelUser s= null;
		JSONObject jsonObject=null;
		String msg = ConstantsMsg.SUCCESS;
		String lMsg = "messages.userAdd.success";
		String fMsg=messageSource.getMessage(lMsg, null, request.getLocale());
		try {
			
			/*
			 * country.setCreatedDateTime(UserDateFormat.getNewDateTimeFormat(ConstantsMsg.DD_MON_YYYY_HH_MM_SS_A));
			country.setCreatedUser(Long.parseLong(request.getSession().getAttribute("currentUserKey").toString()));
			s= (Country) countryDAO.add(country);
			*/
			
			s= (DelUser) delUserDAO.add(delUser);		
			
			jsonObject=JsonReaderWriter.getJSONObjectFromClassObject(s);
		} 
		catch (Exception e) {
			msg = ConstantsMsg.FAILURE;
			fMsg = e.getMessage();
			logger.error("Transaction Failed in Add Method >>", e);
		}

		return JsonReaderWriter.mapOK(jsonObject, msg,fMsg);

	}
	
	
}


