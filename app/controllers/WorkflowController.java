package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class WorkflowController extends Controller {
	public static Result showPage() {
		String title = "Workflow";
		return ok(workflow.render(title));
    }

}
