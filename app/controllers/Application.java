package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("workflow application is ready. !!!!!!"));
    }

    public static Result letsEncrypt(String key1, String key2) {
        return ok(key1 + "." + key2);
    }

}
