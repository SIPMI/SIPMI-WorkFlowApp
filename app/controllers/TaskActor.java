package controllers;

import java.util.List;

import akka.actor.UntypedActor;
import models.entity.TbTask;
import play.Logger;
import play.db.ebean.Model.Finder;

public class TaskActor extends UntypedActor{

	@Override
	public void onReceive(Object message){

//		if(message.equals("Call")){
//
//		}else{
//			unhandled(message);
//		}

		Logger.info("task check start");
		//TODO taskテーブルを見て処理していなければ処理実行
		Finder<Long, TbTask> finder = new Finder<Long, TbTask>(Long.class,TbTask.class);
        List<TbTask> tbTaskList = finder.orderBy("id asc").findPagingList(1).getPage(0).getList();
        if(tbTaskList.size() != 0){
        	TaskRunner.getInstance().execute(tbTaskList.get(0));
        }else{

        }

		Logger.info("task check end");
	}

}
