package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class SettingController extends Controller {
	public static Result showPage() {
		String title = "Setting";
		return ok(setting.render(title));
    }

}
