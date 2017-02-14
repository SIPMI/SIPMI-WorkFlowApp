package controllers;

import java.io.File;
import java.util.List;

import com.google.common.io.Files;

import models.dao.TbUploadFileDao;
import models.entity.TbUploadFile;
import models.util.ImageUtil;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.setting_file;


public class SettingFileController extends BaseController {

	private static String PAGE_TITLE = "Setting File";

	public static Result showPage() {
		List<TbUploadFile> uploadFileList = TbUploadFileDao.findUploadFileList();
		return ok(setting_file.render(PAGE_TITLE, uploadFileList));

    }

	public static Result registFile() {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart upFile = body.getFile("uploadFile");

		DynamicForm requestData = Form.form().bindFromRequest();
	    String dispName = requestData.get("dispName");

		if (upFile != null) {
			//String fileName = upFile.getFilename();
			String contentType = upFile.getContentType();
			File file = upFile.getFile();

			String fileName = TbUploadFileDao.registUploadFile(dispName, ImageUtil.createImageStringFromFile(file));

			String savePath =  Play.application().path() + "/public/images/upload/" + fileName;
			String savePathUp =  Play.application().path() + "/target/scala-2.10/classes/public/images/upload/" + fileName;

			try{
				File newFile = new File(savePath);
				Files.copy(file, newFile);

				File newFileUp = new File(savePathUp);
				Files.copy(file, newFileUp);

				try{
					  //ファイルがコピーされる時間分少し待つ
						Thread.sleep(1000);
					}catch (InterruptedException e){
				}
			}catch(Exception e){
				return redirect("/file_list");
			}finally{
				return redirect("/file_list");
			}

		} else {
			//flash("error", "Missing file");
			return redirect("/file_list");
		}
	}

	public static Result deleteFile() {

		DynamicForm requestData = Form.form().bindFromRequest();
	    String id = requestData.get("deleteFileId");
		TbUploadFileDao.deleteUploadFileById(Long.valueOf(id));

		return redirect("/file_list");
	}

}
