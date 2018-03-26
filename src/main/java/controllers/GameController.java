package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Game;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;

public class GameController {

    public GameController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        get("/games", (req, res) -> {
            List<Game> games = DBHelper.getAll(Game.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("games", games);
            model.put("template", "templates/Game/show.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
