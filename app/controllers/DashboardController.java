package controllers;

import java.util.List;

import models.dao.TbTemplateDao;
import models.dao.TbWorkflowDao;
import models.entity.TbTemplate;
import models.entity.TbWorkflow;
import play.Logger;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.dashboard;


public class DashboardController extends BaseController {

	private static String PAGE_TITLE = "Dashboard";

	public static Result showPage() {

        List<TbWorkflow> workflowList = TbWorkflowDao.findWorkflowList();
		List<TbTemplate> templateList = TbTemplateDao.findTemplateListOrderByRegistDate();
		return ok(dashboard.render(PAGE_TITLE, workflowList, templateList));
    }

}
