package models.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import play.db.ebean.Model;

@Entity
public class TbTask extends Model {

	@Id
	public Integer id;
	public Timestamp registDatetime;
	public Integer workflowId;
	@Version
	public Timestamp lastUpdate;


	public TbTask(){
		//this.registDatetime = new Timestamp(System.currentTimeMillis());
	}

	public TbTask(Integer workflowId, Timestamp registDatetime ){
		this.registDatetime = registDatetime;
		this.workflowId = workflowId;
	}



}
