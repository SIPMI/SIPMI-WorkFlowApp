package models.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class TbUploadFile extends Model {

	public static final  String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Id
	public Integer id;
	public String dispName;
	public String typeName;
	public String fileName;
	@Column(name="data_text", columnDefinition="LONGTEXT")
	public String dataText;
//	@Lob
//	public byte[] dataBlob;
	public Timestamp registDatetime;


	public TbUploadFile(){
	}


	public String getRegistDatetimeLabel(){
		if(this.registDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.registDatetime);
	}


	public String getImageSrc(){

		return "data:image/jpg;base64," + this.dataText;
	}


}
