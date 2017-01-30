package models.vo;

import java.sql.Timestamp;

import models.entity.TbWorkflow;

public class TbWorkflowVo {

	public Integer id;
	public String status;
	public Timestamp startDatetime;
	public Timestamp endDatetime;
	public String workflowXml;

	public TbWorkflow toEntitiy(){
		return new TbWorkflow(id, status, startDatetime, endDatetime, workflowXml);
	}


}
