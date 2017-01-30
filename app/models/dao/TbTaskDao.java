package models.dao;

import java.sql.Timestamp;

import models.entity.TbTask;

public class TbTaskDao {

	public static void registTask(Integer id){

		TbTask tbTask = new TbTask();
		tbTask.registDatetime = new Timestamp(System.currentTimeMillis());
		tbTask.workflowId = id;
		tbTask.save();
	}
}
