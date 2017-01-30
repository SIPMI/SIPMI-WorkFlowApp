package controllers;

import java.util.Arrays;
import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;

public class BaseController extends  Controller {

	//サブクラスで@Overrideすること
	public static Result showPage() {
		return ok("");
	}

	public static Long getIdByRequest(){
		Map<String, String[]> queryStrings = request().queryString();
	    Long id = null;
	    if(queryStrings.get("id") != null){
	        id = Long.parseLong(Arrays.asList(queryStrings.get("id")).get(0));
	    }
	    return id;
    }

}
