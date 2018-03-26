package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Day;
import com.codeclan.models.Player;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.get;

import java.util.*;

public class PlayerController {

    public PlayerController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {
        get("/players", (req, res) -> {
            List<Player> players = DBHelper.getAll(Player.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            List<Day> days = new ArrayList<>();
            Collections.addAll(days, Day.values());
            model.put("days", days);
            model.put("players", players);
            model.put("template", "templates/Player/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());




    }
}
