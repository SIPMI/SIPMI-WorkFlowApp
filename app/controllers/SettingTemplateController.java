package controllers;

import models.dao.TbFunctionDao;
import models.dao.TbTemplateDao;
import models.dao.TbUploadFileDao;
import models.entity.TbTemplate;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import views.html.setting_template;
import views.html.setting_template_edit;

import java.util.List;

public class SettingTemplateController extends BaseController {

	private static String PAGE_TITLE = "Setting Template";

	public static Result showPage() {

		List<TbTemplate> templateList = TbTemplateDao.findTemplateList();
		return ok(setting_template.render(PAGE_TITLE, templateList));
    }


	public static Result deleteTemplate() {

		DynamicForm requestData = Form.form().bindFromRequest();
	    String id = requestData.get("deleteTemplateId");
		TbTemplateDao.deleteTemplateById(Long.valueOf(id));

		return redirect("/template_list");
    }


	public static Result showEditPage(Long id) {

		Form<TbTemplate> forms = Form.form(TbTemplate.class);
		TbTemplate template = TbTemplateDao.findTemplateById(id);
		String workFlowXml;
		if (template == null) {
			template = new TbTemplate();
			workFlowXml = "";
        }else{
			workFlowXml = template.definition;
		}

		return ok(setting_template_edit.render(
					PAGE_TITLE,
					forms.fill(template),
					TbFunctionDao.findFunctionList(),
					TbUploadFileDao.findUploadFileList(),
					workFlowXml
					)
				);

    }


	public static Result registTemplate() {

		Form<TbTemplate> form = Form.form(TbTemplate.class).bindFromRequest();
		if (form.hasErrors()) {
			return redirect("/template_list");
		}
		TbTemplateDao.registTemplate(form.get());

		return redirect("/template_list");
	}


}
