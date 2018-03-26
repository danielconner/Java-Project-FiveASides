package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Day;
import com.codeclan.models.Player;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.get;
import static spark.Spark.post;

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
            List<Day> days = new ArrayList<>();
            Collections.addAll(days, Day.values());
            model.put("user", loggedInUser);
            model.put("days", days);
            model.put("players", players);
            model.put("template", "templates/Player/show.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/players/filter_by_day", (req, res) -> {
            String stringDay = req.queryParams("day");
            Map<String, Object> model = new HashMap<>();
            List<Player> players = DBHelper.getAll(Player.class);
            List<Player> filteredPlayers = new ArrayList<>();
            for (Player player : players) {
                if (player.playerByDay(stringDay)) {
                    filteredPlayers.add(player);
                }
            }
            model.put("day", stringDay);
            model.put("filteredPlayers", filteredPlayers);
            model.put("templates", "templates/players/filter_by_day");
            res.redirect("/players/filter_to_day");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

    }
}
