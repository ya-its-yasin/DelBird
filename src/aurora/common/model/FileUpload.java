package aurora.common.model;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
public class FileUpload {
	 
		private CommonsMultipartFile actualName;
		
		public CommonsMultipartFile getActualName() {
			return actualName;
		}
		public void setActualName(CommonsMultipartFile actualName) {
			this.actualName= actualName;
		}
		
	}

