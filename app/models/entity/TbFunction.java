package models.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class TbFunction extends Model {

	public static final  String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Id
	public Integer id;
	public String name;
	public String typeName;
	@Column(name="url", columnDefinition="TEXT")
	public String url;
	@Column(name="definition", columnDefinition="LONGTEXT")
	public String definition;
	public Timestamp registDatetime;


	public TbFunction(){

	}


	public TbFunction(Integer id, String name, String typeName, String url, String definition){
		this.id = id;
		this.name = name;
		this.typeName = typeName;
		this.url = url;
		this.definition = definition;
	}

	public String getRegistDatetimeLabel(){
		if(this.registDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.registDatetime);
	}



}
