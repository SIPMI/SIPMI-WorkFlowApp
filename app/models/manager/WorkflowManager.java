package models.manager;

import java.util.Collections;
import java.util.List;

import models.dao.TbTaskDao;
import models.dao.TbWorkDao;
import models.dao.TbWorkParamDao;
import models.dao.TbWorkflowDao;
import models.entity.TbWorkflow;
import models.util.ImageUtil;
import models.vo.ExTbWorkflowVo;
import play.data.Form;

public class WorkflowManager {

	public static Long registWorkWithForm(Form<ExTbWorkflowVo> f){

		ExTbWorkflowVo vo = f.get();
		TbWorkflow data = vo.toEntitiy();

		TbWorkflowDao.registWorkflow(data);

		if(vo.taskList == null){
			// 複雑な処理はDemmyロジックを固定で作成
			registWorkListDummy(data.id, vo.taskList, vo.varList);
			TbTaskDao.registTask(data.id);
		}else{
			// 通常の解析フロー
			registWorkList(data.id, vo.taskList, vo.varList);
			TbTaskDao.registTask(data.id);
		}

		return data.id.longValue();
	}


	private static void registWorkList(Integer workflowId, List<String> taskList, List<String> varList){

		int sortNo = taskList.size();
		Integer parentId = null;
		int varListIndex = 0;
		//逆順にしてループ result全部ひっくり返して正常に
		Collections.reverse(taskList);
		Collections.reverse(varList);
		for(String task : taskList){
//
			// ワークの登録
			Integer workId = TbWorkDao.registWork(task, sortNo, parentId, workflowId);

			sortNo--;
			if(! "math_number".equals(task)){
				parentId = workId;
			}

			if(task.startsWith("InputData")){
				// ワークに対応するパラメータの登録
	    		TbWorkParamDao.registWorkParam(workId, 1, "InputData", ImageUtil.createImageStringFromPath(varList.get(0)));
	    	}//1をsortnoに変えれば？2つめしか表示されなくなった parentId?全部消えた taskはstring
			else if("math_number".equals(task)){
				// ワークに対応するパラメータの登録
//				TbWorkParamDao.registWorkParam(workId, 1, "InputVal", varList.get(1));
				TbWorkParamDao.registWorkParam(workId, 1, "InputVal", varList.get(varListIndex));
                varListIndex++;//bloomsoftさん案
								//順番がひっくり返った
//								varListIndex--;
	    	}
		}
	}


	private static void registWorkListDummy(Integer workflowId, List<String> taskList, List<String> varList){

		Integer workId = 0;
		// ワークの登録
//		workId = TbWorkDao.registWork("math_number", 1, null, workflowId);
//		workId = TbWorkDao.registWork("variables_set", 2, null, workflowId);
//		workId = TbWorkDao.registWork("math_number", 3, null, workflowId);
//		workId = TbWorkDao.registWork("variables_get", 4, null, workflowId);
//		workId = TbWorkDao.registWork("logic_compare", 5, null, workflowId);
		workId = TbWorkDao.registWork("controls_whileUntil", 1, null, workflowId);
			TbWorkParamDao.registWorkParam(workId, 1, "var i init", varList.get(1));
			TbWorkParamDao.registWorkParam(workId, 2, "var average init", varList.get(2));
			TbWorkParamDao.registWorkParam(workId, 3, "var average until", varList.get(3));
			TbWorkParamDao.registWorkParam(workId, 4, "var i ++", varList.get(4));
		workId = TbWorkDao.registWork("InputData", 2, null, workflowId);
    		TbWorkParamDao.registWorkParam(workId, 1, "InputData", ImageUtil.createImageStringFromPath(varList.get(0)));
//		workId = TbWorkDao.registWork("variables_get", 8, null, workflowId);
		workId = TbWorkDao.registWork("Binarization", 3, null, workflowId);
//		workId = TbWorkDao.registWork("controls_whileUntil", 10, null, workflowId);
//		workId = TbWorkDao.registWork("variables_set", 11, null, workflowId);
//		workId = TbWorkDao.registWork("variables_get", 12, null, workflowId);
		workId = TbWorkDao.registWork("Visualize", 4, null, workflowId);
//		workId = TbWorkDao.registWork("variables_get", 14, null, workflowId);
		workId = TbWorkDao.registWork("CalcAverageBrightness", 5, null, workflowId);
//		workId = TbWorkDao.registWork("variables_set", 16, null, workflowId);
//		workId = TbWorkDao.registWork("variables_get", 17, null, workflowId);
		workId = TbWorkDao.registWork("OutputData", 6, null, workflowId);
//		workId = TbWorkDao.registWork("math_number", 8, null, workflowId);
//		workId = TbWorkDao.registWork("variables_get", 9, null, workflowId);
//		workId = TbWorkDao.registWork("math_arithmetic", 10, null, workflowId);
//		workId = TbWorkDao.registWork("variables_set", 11, null, workflowId);

	}








}
