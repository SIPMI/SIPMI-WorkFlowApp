package models.dao;

import java.sql.Timestamp;
import java.util.List;

import models.entity.TbWorkflow;
import models.util.WorkflowStatusEnum;
import play.db.ebean.Model.Finder;

public class TbWorkflowDao {

	public static List<TbWorkflow> findWorkflowList(){
		Finder<Long, TbWorkflow> finder = new Finder<Long, TbWorkflow>(Long.class,TbWorkflow.class);
	    return finder.orderBy().asc("id").findList();
	}


	public static String findWorkflowXmlById(Long id){

		TbWorkflow tbWorkflow = findWorkflowById(id);
        String xml = "";
        if(tbWorkflow != null){
        	xml = tbWorkflow.workflowXml;
        }
		return xml;
	}

	public static void registWorkflow(TbWorkflow data){

		data.status = WorkflowStatusEnum.UNEXECUTED.getCode();
		data.save();
	}

	public static TbWorkflow findWorkflowById(Long id){
		Finder<Long, TbWorkflow> workFinder = new Finder<Long, TbWorkflow>(Long.class, TbWorkflow.class);
        return workFinder.byId(id);
	}

	public static void updateForExecute(TbWorkflow data){
		data.startDatetime = new Timestamp(System.currentTimeMillis());
		data.status = WorkflowStatusEnum.PROCESSING.getCode();
//		data.save();
		data.update(data.id);
	}

	public static void updateForSuccess(TbWorkflow data){
		data.endDatetime = new Timestamp(System.currentTimeMillis());
		data.status = WorkflowStatusEnum.FINISHED.getCode();
//		data.save();
		data.update(data.id);
	}

	public static void updateForError(TbWorkflow data){
		//data.endDatetime = new Timestamp(System.currentTimeMillis());
		data.status = WorkflowStatusEnum.ERROR.getCode();
//		data.save();
		data.update(data.id);
	}

}
