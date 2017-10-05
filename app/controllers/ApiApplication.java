package controllers;

import java.util.HashMap;

import models.dao.TbWorkDao;
import models.entity.TbWork;
import models.util.ImageUtil;
import models.vo.ApiParamForm;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class ApiApplication extends Controller {

    public static Result convertGrayScale() {

    	Form<ApiParamForm> f= new Form(ApiParamForm.class).bindFromRequest();
    	ApiParamForm apiParamForm = f.bindFromRequest().get();

    	String res = ImageUtil.convertGrayScale(apiParamForm.img);

    	HashMap<String,String> resMap = new HashMap<String,String>();
    	resMap.put("img", res);

        return ok(Json.toJson(resMap));
    }

    public static Result binarize() {

    	Form<ApiParamForm> f= new Form(ApiParamForm.class).bindFromRequest();
    	ApiParamForm apiParamForm = f.bindFromRequest().get();

    	String res = ImageUtil.binarize(apiParamForm.img, Double.valueOf(apiParamForm.threshold));

    	HashMap<String,String> resMap = new HashMap<String,String>();
    	resMap.put("img", res);

        return ok(Json.toJson(resMap));
    }

	public static Result resultImage(Long id) {

		Logger.info("api!!!!!:" + id);
    	TbWork work = TbWorkDao.findWorkById(id);

		String res = work.getImageSrc();

		HashMap<String,String> resMap = new HashMap<String,String>();
		resMap.put("img", res);

		return ok(Json.toJson(resMap));
	}






}
