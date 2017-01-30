package models.dao;

import java.util.List;

import models.entity.TbWorkParam;
import play.db.ebean.Model.Finder;

public class TbWorkParamDao {

	public static void registTbWorkParam(){

		TbWorkParam tbWorkParam = new TbWorkParam();
		tbWorkParam.save();
	}


	public static List<TbWorkParam> findWorkParamListByWorkId(Long id){
		Finder<Long, TbWorkParam> workParamFinder = new Finder<Long, TbWorkParam>(Long.class, TbWorkParam.class);

		List<TbWorkParam> workParamList = null;
        String q = "work_id = " + id;
        if(workParamFinder.where(q).findList() != null){
        	workParamList = workParamFinder.where(q).findList();
        }
		return workParamList;

	}

	public static void registWorkParam(Integer workId, Integer paramNo, String paramStr, String paramText){

		TbWorkParam param = new TbWorkParam();
		param.workId = workId;
		param.paramNo = paramNo;
		param.paramStr = paramStr;
		param.paramText = paramText;

		param.save();

	}


}
