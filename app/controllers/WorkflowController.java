package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import models.dao.*;
import models.entity.TbTemplate;
import models.entity.TbWork;
import models.manager.WorkflowManager;
import models.vo.ExTbWorkflowVo;
import play.data.Form;
import play.mvc.Result;
import views.html.workflow;

public class WorkflowController extends BaseController {

	private static String PAGE_TITLE = "Workflow";


	public static Result showPage(Long id) {

		Long workflowId = id;
		if(getIdByRequest() != null && getIdByRequest() != 0){
			workflowId = getIdByRequest();
		}
		Long templateId = null;
		if(workflowId == 0 && getTemplateIdByRequest() != null && getTemplateIdByRequest() != 0){
			templateId = getTemplateIdByRequest();
		}

		String workflowXml = "";
		if(templateId == null){
			workflowXml = TbWorkflowDao.findWorkflowXmlById(workflowId);
		}else{
			workflowXml = TbTemplateDao.findTemplateXmlById(templateId);
		}

		Form<ExTbWorkflowVo> exf = new Form(ExTbWorkflowVo.class);
		return ok(workflow.render(
					PAGE_TITLE,
					TbFunctionDao.findFunctionList(),
					TbUploadFileDao.findUploadFileList(),
					findWorkListById(id),
					workflowXml,
					exf
					)
				);
    }



	public static Result registWork(){

		Long registedId = null;
//		String xml = null;

		Form<ExTbWorkflowVo> exf = new Form(ExTbWorkflowVo.class).bindFromRequest();

		if(!exf.hasErrors()){
			registedId = WorkflowManager.registWorkWithForm(exf);
			return redirect("/workflow/" + registedId.toString());
		}else{
			return redirect("/workflow/" + registedId.toString());
		}

	}



//	private static List<TbWork> findWorkListByRequest(){
//        List<TbWork> workList = null;
//        Long id = null;
//        id = getIdByRequest();
//
//        if(id != null){
//        	workList = TbWorkDao.findWorkListById(id);
//        }
//
//		return workList;
//	}

	private static List<TbWork> findWorkListById(Long id){
        List<TbWork> workList = null;

        if(id != null){
        	workList = TbWorkDao.findWorkListById(id);
        }

		return workList;
	}


//	private static String findWorkflowXmlByRequest(){
//        String workflowXml = "";
//        Long id = null;
//        id = getIdByRequest();
//
//        if(id != null){
//        	workflowXml = TbWorkflowDao.findWorkflowXmlById(id);
//        }
//
//		return workflowXml;
//	}

	public static Long getTemplateIdByRequest(){
		Map<String, String[]> queryStrings = request().queryString();
		Long templateId = null;
		if(queryStrings.get("templateId") != null){
			templateId = Long.parseLong(Arrays.asList(queryStrings.get("templateId")).get(0));
		}
		return templateId;
	}

}
