package controllers;

import com.codeclan.models.Day;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

public class LoginController {


    public LoginController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            req.session().attribute("username", username);
            res.redirect("/");
            return null;
        }, new VelocityTemplateEngine());

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "templates/login.vtl");
        }, new VelocityTemplateEngine());

        get("/logout", (req, res) -> {
            req.session().removeAttribute("username");
            res.redirect("/");
            return null;
        }, new VelocityTemplateEngine());

        get("sign_up", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
//            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Day> days = new ArrayList<>();
            Collections.addAll(days, Day.values());
            model.put("days", days);
            model.put("template", "templates/Player/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }

    public static String getLoggedInUserName(Request req, Response res){
        String username = req.session().attribute("username");
        if(username == null || username.isEmpty()){
            res.redirect("/login");
        }
        return username;
    }



}
