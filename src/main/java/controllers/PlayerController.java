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

        post("/players", (req, res) -> {
            String username = req.queryParams("username");
            String name = req.queryParams("name");
            String location = req.queryParams("location");
//            List<Day> days = new ArrayList<>();
//            Collections.addAll(days, Day.values());
//            String availability[] = req.queryParams("availability").split(",");
            List<Day> availableDays = new ArrayList<>();
//            for (String dayAvailable : availability) {
//                Day day = returnDayFromString(dayAvailable);
//                availableDays.add(day);
//            }
            Player newPlayer = new Player(username, name, availableDays, location);
            DBHelper.save(newPlayer);
            res.redirect("/login");
            return null;
        });

        post("/players/:id/edit", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Player player = DBHelper.find(id, Player.class);
            player.setUsername(req.queryParams("username"));
            player.setName(req.queryParams("name"));
            player.setLocation(req.queryParams("location"));
            List<Day> availableDays = new ArrayList<>();
            DBHelper.save(player);
            String playerId = Integer.toString(id);
            res.redirect("/players");
            return null;
        }, new VelocityTemplateEngine());

        get("/players/filter", (req, res) -> {

            List<Player> players = DBHelper.getAll(Player.class);
            String result = req.queryParams("day");
            Day filteredDay = returnDayFromString(result);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Player> filteredPlayers = new ArrayList<>();
            for (Player player : players) {
                if (player.availableOnDay(filteredDay)) {
                    filteredPlayers.add(player);
                }
            }
            model.put("filteredDay", filteredDay);
            model.put("user", loggedInUser);
            model.put("filteredPlayers", filteredPlayers);
            model.put("template", "templates/Player/filter_by_day.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/players/deleted", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("user", null);
            model.put("template", "templates/Player/delete.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("players/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Player player = DBHelper.find(id, Player.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("player", player);
            model.put("template", "templates/Player/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post("/players/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Player toDelete = DBHelper.find(id, Player.class);
            DBHelper.delete(toDelete);
            res.redirect("/players/deleted");
            return null;
        }, new VelocityTemplateEngine());

        get("/players/:id/edit", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Player player = DBHelper.find(id, Player.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("template", "templates/Player/edit.vtl");
            model.put("user", loggedInUser);
            model.put("player", player);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());



    }

    public Day returnDayFromString(String dayAsString) {
        Day foundDay = null;
        for (Day day : Day.values()) {
            if (day.getDay().equals(dayAsString)) {
                foundDay = day;
            }
        }
        return foundDay;
    }
}
