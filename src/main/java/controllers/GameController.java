package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;

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

        get("/games/organise-game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Day> days = new ArrayList<>();
            Collections.addAll(days, Day.values());
            List<Venue> venues = DBHelper.getAll(Venue.class);
            model.put("user", loggedInUser);
            model.put("venues", venues);
            model.put("days", days);
            model.put("template", "templates/Game/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
            }, new VelocityTemplateEngine());

        post("/games", (req, res) -> {
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            Player organiser = DBHelper.findByUsername(loggedInUser);
            String name = req.queryParams("name");
            int venueId = Integer.parseInt(req.queryParams("venue"));
            String time = req.queryParams("time");
            int numberOfRequiredPlayer = Integer.parseInt(req.queryParams("numberOfRequiredPlayer"));
            Day day = returnDayFromString(req.queryParams("day"));
            Venue venue = DBHelper.find(venueId, Venue.class);
            Game newGame = new Game(name, venue, organiser, numberOfRequiredPlayer, day, time);
            DBHelper.save(newGame);
            res.redirect("/games");
            return null;
        }, new VelocityTemplateEngine());

        get("/games/:id", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            String title = game.getTitle();
            Venue venue = game.getVenue();
            Player player = game.getOrganiser();
            Integer requiredPlayers = game.getNumberOfRequiredPlayer();
            List<Player> signedUpPlayers = DBHelper.playersPlaying(game);
            List<Player> invitedPlayers = DBHelper.invitedPlayers(game);
            String day = game.getDay().getDay();
            String time = game.getTime();
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("title", title);
            model.put("venue", venue);
            model.put("player", player);
            model.put("requiredPlayers", requiredPlayers);
            model.put("signedUpPlayers", signedUpPlayers);
            model.put("invitedPlayers", invitedPlayers);
            model.put("day", day);
            model.put("time", time);
            model.put("template", "templates/Game/index.vtl");
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
