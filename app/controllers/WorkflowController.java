package controllers;

import java.util.List;

import models.dao.TbFunctionDao;
import models.dao.TbUploadFileDao;
import models.dao.TbWorkDao;
import models.dao.TbWorkflowDao;
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

		Form<ExTbWorkflowVo> exf = new Form(ExTbWorkflowVo.class);
		return ok(workflow.render(
					PAGE_TITLE,
					TbFunctionDao.findFunctionList(),
					TbUploadFileDao.findUploadFileList(),
					findWorkListById(id),
					TbWorkflowDao.findWorkflowXmlById(workflowId),
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
	public static Result index() {
	  return ok(new java.io.File("/tmp/test.txt"));
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



}
