package models.entity;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
public class TbTemplate extends Model {

	public static final  String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Id
	public Integer id;
	public String name;
	@Column(name="comment", columnDefinition="TEXT")
	public String comment;
	@Column(name="definition", columnDefinition="LONGTEXT")
	public String definition;
	public Timestamp registDatetime;


	public TbTemplate(){

	}


	public TbTemplate(Integer id, String name, String comment, String definition){
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.definition = definition;
	}

	public String getRegistDatetimeLabel(){
		if(this.registDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.registDatetime);
	}



}
