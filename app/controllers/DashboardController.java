package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class DashboardController extends Controller {
	public static Result showPage() {
		String title = "Dashborad";
		return ok(dashboard.render(title));
    }

}
