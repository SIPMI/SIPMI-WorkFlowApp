package models.dao;

import java.sql.Timestamp;
import java.util.List;

import models.entity.TbFunction;
import play.db.ebean.Model.Finder;

public class TbFunctionDao {

	public static List<TbFunction> findFunctionList(){
		Finder<Long, TbFunction> functionFinder = new Finder<Long, TbFunction>(Long.class, TbFunction.class);
        return functionFinder.all();
	}

	public static TbFunction findFunctionById(Long id){
		Finder<Long, TbFunction> functionFinder = new Finder<Long, TbFunction>(Long.class, TbFunction.class);
        return functionFinder.byId(id);
	}


	public static TbFunction findFunctionByTypeName(String name){
        Finder<Long, TbFunction> workFinder = new Finder<Long, TbFunction>(Long.class, TbFunction.class);
        List<TbFunction> functionList = null;

        String q = "type_name = '" + name + "'";
        if(workFinder.where(q).findList() != null){
        	functionList = workFinder.where(q).findList();
        }
        if(functionList != null && functionList.size() > 0){
        	return functionList.get(0);
        }
		return null;
	}


	public static void deleteFunctionById(Long id){

		TbFunction func = findFunctionById(id);
		func.delete();
	}

	public static void registFunction(TbFunction func){

		func.registDatetime = new Timestamp(System.currentTimeMillis());
		if (func.id == null) {
			func.save();
		} else {
			func.update();
		}
	}


}
