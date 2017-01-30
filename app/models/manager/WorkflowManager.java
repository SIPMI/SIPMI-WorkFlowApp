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

		registWorkList(data.id, vo.taskList, vo.varList);

		TbTaskDao.registTask(data.id);

		return data.id.longValue();
	}


	private static void registWorkList(Integer workflowId, List<String> taskList, List<String> varList){

		int sortNo = taskList.size();
		Integer parentId = null;
		//逆順にしてループ
		Collections.reverse(taskList);
		for(String task : taskList){

			// ワークの登録
			Integer workId = TbWorkDao.registWork(task, sortNo, parentId, workflowId);

			sortNo--;
			if(! "math_number".equals(task)){
				parentId = workId;
			}

			if(task.startsWith("InputData")){
				// ワークに対応するパラメータの登録
	    		TbWorkParamDao.registWorkParam(workId, 1, "InputData", ImageUtil.createImageStringFromPath(varList.get(0)));
	    	}
			else if("math_number".equals(task)){
				// ワークに対応するパラメータの登録
				TbWorkParamDao.registWorkParam(workId, 1, "InputVal", varList.get(1));
	    	}

		}


	}








}
