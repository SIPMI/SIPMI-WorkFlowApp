package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("workflow application is ready. !!!!!!"));
    }

    private static String LETS_ENCRYPT_KEY = "H0CnIkYVVFyoJFxuj8Jp-JrVIPXvfzZUQBB09nIPlxc";

    public static Result letsEncrypt(String key) {
        return ok(key + "." + LETS_ENCRYPT_KEY);
    }

}
