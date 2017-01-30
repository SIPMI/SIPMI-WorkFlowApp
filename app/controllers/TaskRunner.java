package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.dao.TbFunctionDao;
import models.dao.TbWorkDao;
import models.dao.TbWorkParamDao;
import models.dao.TbWorkflowDao;
import models.entity.TbFunction;
import models.entity.TbTask;
import models.entity.TbWork;
import models.entity.TbWorkParam;
import models.entity.TbWorkflow;
import models.util.ImageUtil;
import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS;

public class TaskRunner {
	// バッチ処理用にシングルトンにしておく
	private static TaskRunner taskRunner = new TaskRunner();

    private TaskRunner(){
        System.out.println("インスタンスを作成しました。");
    }

    public static TaskRunner getInstance(){
        return taskRunner;
    }

    public void execute(TbTask tbTask){

    	Long workflowId = tbTask.workflowId.longValue();

    	Logger.info("task id :" + tbTask.workflowId);

    	TbWorkflow workflow = TbWorkflowDao.findWorkflowById(workflowId);
    	List<TbWork> workList = TbWorkDao.findWorkListById(workflowId);


    	if(workflow != null && workList != null && workList.size() != 0){

    		Logger.info("task start");
    		TbWorkflowDao.updateForExecute(workflow);

//    		try{

	    		for(TbWork work: workList){

	    			TbWorkDao.updateForExecute(work);

//	    			try{

	    				executeWork(work);
	    				TbWorkDao.updateForSuccess(work);

//	    			}catch(Exception e){
//	    				TbWorkDao.updateForError(work);
//	    				throw e;
//	    			}

	    		}

	    		TbWorkflowDao.updateForSuccess(workflow);
	        	Logger.info("task done");

//    		}catch(Exception e){
//    			TbWorkflowDao.updateForError(workflow);
//    			e.printStackTrace();
//    			Logger.info("task error");
//    		}
    	}else{
    		Logger.info("workflow not found");
    	}

		tbTask.delete();

    }

    private void executeWork(TbWork work){

    	Logger.info("execute " + work.task);

    	// 自分のワークのパラメータを取得
		//TbWorkParam selfParam = null;
		//TbWorkParam selfParam2 = null;
		List<TbWorkParam> paramList = TbWorkParamDao.findWorkParamListByWorkId(work.id.longValue());
		//if(paramList != null && paramList.size() != 0){
		//	selfParam = paramList.get(0);
		//	if(paramList.size() > 1){
		//		selfParam2 = paramList.get(1);
		//	}
		//	Logger.info("get param :" + selfParam.paramStr);
		//}else{
		//	Logger.info("get param : null");
		//}


    	switch(work.task){
    		case "math_number":
	    		// 親のワークのパラメータに保存
    			TbWorkParamDao.registWorkParam(work.parentId, 2, "InputVal", paramList.get(0).paramText);
    			break;

	    	case "InputData":
	    	case "InputData2":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(work.parentId, 1, "InputData", paramList.get(0).paramText);
	    		break;

	    	case "ConvertGrayScale":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(
	    				work.parentId,
	    				1,
	    				"ConvertGrayScaleData for Visualize",
	    				ImageUtil.convertGrayScale(paramList.get(0).paramText)
	    				);
	    		break;

	    	case "Binarization":
	    		// 親のワークのパラメータに保存
	    		TbWorkParamDao.registWorkParam(
	    				work.parentId,
	    				1,
	    				"Binarization for Visualize",
	    				ImageUtil.binarize((paramList.get(0).paramText), Double.parseDouble(paramList.get(1).paramText))
	    				);
	    		break;

	    	case "Visualize":
	    		// 結果が自分のワークのパラメータに保存されているので
	    		// ここでは何もしない
	    		break;

	    	default:

	    		if(work.task.startsWith("InputData")){
	    			// 親のワークのパラメータに保存
		    		TbWorkParamDao.registWorkParam(work.parentId, 1, "InputData", paramList.get(0).paramText);

	    		}else{

		    		//ファンクションの取得
		    		TbFunction func = TbFunctionDao.findFunctionByTypeName(work.task);
		    		if(func == null){
		    			Logger.info("function not found !!");
		    			break;
		    		}

		    		//リモートのファンクション呼び出し
		    		//String url = "http://localhost:9000/convertGrayScale";
		    		String url = func.url;
		    		String req = paramList.get(0).paramText;
		    		ObjectNode node = Json.newObject();
		    		node.put("img", paramList.get(0).paramText);
		    		if(paramList.size() > 1){
		    			node.put("threshold", paramList.get(1).paramText);
		    		}

		    		Promise<WS.Response> response = WS.url(url)
		    											//.setQueryParameter("img", req)
		    											//.post(Json.newObject().put("img", req));
		    											.post(node);

		        	// 親のワークのパラメータに保存
		    		TbWorkParamDao.registWorkParam(work.parentId, 1, "Remote Function for Visualize", response.get().asJson().findValue("img").asText());

	    		}
	    	}

    }


}
