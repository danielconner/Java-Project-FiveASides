package controllers;

import com.codeclan.db.Seeds;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {


    public static void main(String[] args) {

        Seeds.seedData();

        staticFileLocation("/public");

        LoginController loginController = new LoginController();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//             this is to check to see if a user is logged in this needs to be on every route
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
//            this is for the layout page so it shows whos logged in

            model.put("template","templates/main.vtl");

            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}


