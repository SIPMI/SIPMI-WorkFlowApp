package models.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.Version;

import models.util.EnumUtil;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class TbWorkflow extends Model {

	public static final  String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	@Id
	public Integer id;
	public String status;
	public Timestamp startDatetime;
	public Timestamp endDatetime;
	@Required
	@Column(name="workflow_xml", columnDefinition="text")
	@NotNull
	public String workflowXml;


	public TbWorkflow(Integer id, String status, Timestamp startDatetime, Timestamp endDatetime, String workflowXml){

		this.id = id;
		this.status = status;
		this.startDatetime = startDatetime;
		this.endDatetime = endDatetime;
		this.workflowXml = workflowXml;
	}

	public String getStatusLabel(){
		return EnumUtil.getWorkflowStatusLabelByCode(this.status);
	}

	public String getStartDatetimeLabel(){
		if(this.startDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.startDatetime);
	}

	public String getEndDatetimeLabel(){
		if(this.endDatetime == null){
			return "-";
		}
		return new SimpleDateFormat(TIME_FORMAT).format(this.endDatetime);
	}


}
