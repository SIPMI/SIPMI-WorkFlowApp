package controllers;

import play.*;
import play.mvc.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("workflow application is ready. !!!!!!"));
    }

    public static Result letsEncrypt(String key) {
        String LETS_ENCRYPT_KEY = Play.application().configuration().getString("lets_encrypt_key");
        return ok(key + "." + LETS_ENCRYPT_KEY);
    }

}
