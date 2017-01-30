package models.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import models.entity.TbUploadFile;
import play.db.ebean.Model.Finder;

public class TbUploadFileDao {

	public static TbUploadFile findUploadFileById(Long id){
		Finder<Long, TbUploadFile> workFinder = new Finder<Long, TbUploadFile>(Long.class, TbUploadFile.class);
        return workFinder.byId(id);
	}

	public static String registUploadFile(String dispName, String dataText){

		TbUploadFile file = new TbUploadFile();
		file.dispName = dispName;
		file.dataText = dataText;
		file.registDatetime = new Timestamp(System.currentTimeMillis());
		file.fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(file.registDatetime) + ".jpg";
		file.typeName = "InputData_" + new SimpleDateFormat("yyyyMMddHHmmss").format(file.registDatetime);
		file.save();

		return file.fileName;

	}

	public static List<TbUploadFile> findUploadFileList(){
		Finder<Long, TbUploadFile> finder = new Finder<Long, TbUploadFile>(Long.class,TbUploadFile.class);
	    return finder.orderBy().asc("id").findList();
	}

	public static void deleteUploadFileById(Long id){

		TbUploadFile file = findUploadFileById(id);
		file.delete();
	}


}
