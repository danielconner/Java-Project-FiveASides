package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;


public class PlayerController {

    public PlayerController() {
        this.setupEndPoints();
    }

    private void setupEndPoints() {

        get("/players", (req, res) -> {
            List<Player> players = DBHelper.getAll(Player.class);
            Map<String, Object> model = new HashMap<>();
            model.put("players", players);
            model.put("template", "templates/Player/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");

        }, new VelocityTemplateEngine());


    }
}
