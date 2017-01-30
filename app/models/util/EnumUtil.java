package models.util;

public class EnumUtil {

	public static final String WORKFLOW_STATUS_CODE_DEFAULT = "00";
	public static final String WORKFLOW_STATUS_CODE_UNEXECUTED = "10";
	public static final String WORKFLOW_STATUS_CODE_PROCESSING = "20";
	public static final String WORKFLOW_STATUS_CODE_FINISHED = "30";
	public static final String WORKFLOW_STATUS_CODE_ERROR = "90";

	public static final String WORK_STATUS_CODE_DEFAULT = "00";
	public static final String WORK_STATUS_CODE_NOT_YET = "10";
	public static final String WORK_STATUS_CODE_PROCESSING = "20";
	public static final String WORK_STATUS_CODE_DONE = "30";
	public static final String WORK_STATUS_CODE_ERROR = "90";

	public static String getWorkflowStatusLabelByCode(String Status){

		String label;
		switch (Status){
		  case WORKFLOW_STATUS_CODE_DEFAULT:
			  label = WorkflowStatusEnum.DEFAULT.getLabel();
			  break;

		  case WORKFLOW_STATUS_CODE_UNEXECUTED:
			  label = WorkflowStatusEnum.UNEXECUTED.getLabel();
			  break;

		  case WORKFLOW_STATUS_CODE_PROCESSING:
			  label = WorkflowStatusEnum.PROCESSING.getLabel();
			  break;

		  case WORKFLOW_STATUS_CODE_FINISHED:
			  label = WorkflowStatusEnum.FINISHED.getLabel();
			  break;

		  case WORKFLOW_STATUS_CODE_ERROR:
			  label = WorkflowStatusEnum.ERROR.getLabel();
			  break;

		  default:
			  label = WorkflowStatusEnum.DEFAULT.getLabel();

		}

		return label;
	}



	public static String getWorkStatusLabelByCode(String Status){

		String label;
		switch (Status){
		  case WORK_STATUS_CODE_DEFAULT:
			  label = WorkStatusEnum.DEFAULT.getLabel();
			  break;

		  case WORK_STATUS_CODE_NOT_YET:
			  label = WorkStatusEnum.NOT_YET.getLabel();
			  break;

		  case WORK_STATUS_CODE_PROCESSING:
			  label = WorkStatusEnum.PROCESSING.getLabel();
			  break;

		  case WORK_STATUS_CODE_DONE:
			  label = WorkStatusEnum.DONE.getLabel();
			  break;

		  case WORK_STATUS_CODE_ERROR:
			  label = WorkStatusEnum.ERROR.getLabel();
			  break;

		  default:
			  label = WorkStatusEnum.DEFAULT.getLabel();

		}

		return label;
	}

}
