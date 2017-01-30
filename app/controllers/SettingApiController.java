package controllers;

import java.util.List;

import models.dao.TbFunctionDao;
import models.entity.TbFunction;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import views.html.*;

public class SettingApiController extends BaseController {

	private static String PAGE_TITLE = "Setting API";

	public static Result showPage() {

		List<TbFunction> functionList = TbFunctionDao.findFunctionList();
		return ok(setting_api.render(PAGE_TITLE, functionList));
    }


	public static Result deleteApi() {

		DynamicForm requestData = Form.form().bindFromRequest();
	    String id = requestData.get("deleteApiId");
		TbFunctionDao.deleteFunctionById(Long.valueOf(id));

		return redirect("/api_list");
    }


	public static Result showEditPage(Long id) {

		Form<TbFunction> forms = Form.form(TbFunction.class);
		TbFunction func = TbFunctionDao.findFunctionById(id);
		if (func == null) {
			func = new TbFunction();
        }

		return ok(setting_api_edit.render(PAGE_TITLE, forms.fill(func)));

    }



	public static Result registApi() {

		Form<TbFunction> form = Form.form(TbFunction.class).bindFromRequest();
		if (form.hasErrors()) {
			return redirect("/api_list");
		}
		TbFunctionDao.registFunction(form.get());

		return redirect("/api_list");
	}


}
