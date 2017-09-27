package com.ecnice.privilege.web.controller.fileupload;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class MyMultipartResolver extends CommonsMultipartResolver{
	@Override
	protected MultipartParsingResult parseRequest(HttpServletRequest request)
			throws MultipartException {
			String encoding = "utf-8";
	        FileUpload fileUpload = prepareFileUpload(encoding);
	        final HttpSession session = request.getSession();
	        fileUpload.setProgressListener(new ProgressListener() {
				@Override
	            public void update(long pBytesRead, long pContentLength, int pItems) {
	                int percent = (int) (((float)pBytesRead / (float)pContentLength) * 100);
	                //保存上传的百分比
	                session.setAttribute("percent", percent + "%");
	            }
	        });
	        
	        try {
	            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
	            return parseFileItems(fileItems, encoding);
	        }
	        catch (FileUploadBase.SizeLimitExceededException ex) {
	            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
	        }
	        catch (FileUploadException ex) {
	            throw new MultipartException("Could not parse multipart servlet request", ex);
	        }
	}
}
