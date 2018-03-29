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

        get("/games/local", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            String location = DBHelper.getUserLocation(loggedInUser);
            List<Game> games = DBHelper.gamesByLocation(location);
            model.put("user", loggedInUser);
            model.put("games", games);
            model.put("template", "templates/Game/showLocal.vtl");
            return new ModelAndView(model,"templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/games/:id/edit", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Day> days = new ArrayList<>();
            Collections.addAll(days, Day.values());
            List<Venue> venues = DBHelper.getAll(Venue.class);
            model.put("user", loggedInUser);
            model.put("game", game);
            model.put("venues", venues);
            model.put("days", days);
            model.put("template", "templates/Game/edit.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

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
            String title = req.queryParams("title");
            int venueId = Integer.parseInt(req.queryParams("venue"));
            String time = req.queryParams("time");
            int numberOfRequiredPlayer = Integer.parseInt(req.queryParams("numberOfRequiredPlayer"));
            Day day = returnDayFromString(req.queryParams("day"));
            Venue venue = DBHelper.find(venueId, Venue.class);
            Game newGame = new Game(title, venue, organiser, numberOfRequiredPlayer, day, time);
            DBHelper.save(newGame);
            res.redirect("/games");
            return null;
        }, new VelocityTemplateEngine());

        get("/games/:id", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            String title = game.getTitle();
            Venue venue= game.getVenue();
            Player player = game.getOrganiser();
            Integer requiredPlayers = game.getNumberOfRequiredPlayer();
            List<Player> signedUpPlayers = DBHelper.playersPlaying(game);
            String day = game.getDay().getDay();
            String time = game.getTime();
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("game", game);
            model.put("title", title);
            model.put("venue", venue);
            model.put("player", player);
            model.put("requiredPlayers", requiredPlayers);
            model.put("signedUpPlayers", signedUpPlayers);
            model.put("day", day);
            model.put("time", time);
            model.put("template", "templates/Game/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        post ("/games/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            Game gameToDelete = DBHelper.find(id, Game.class);
            gameToDelete.removeAllPlayersFromGame();
            DBHelper.delete(gameToDelete);
            res.redirect("/games");
            return null;
        }, new VelocityTemplateEngine());

        post("/games/:id", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            String title = req.queryParams("title");
            int venueId = Integer.parseInt(req.queryParams("venue"));
            String time = req.queryParams("time");
            int numberOfRequiredPlayer = Integer.parseInt(req.queryParams("numberOfRequiredPlayer"));
            Day day = returnDayFromString(req.queryParams("day"));
            Venue venue = DBHelper.find(venueId, Venue.class);
            game.setTitle(title);
            game.setVenue(venue);
            game.setNumberOfRequiredPlayer(numberOfRequiredPlayer);
            game.setDay(day);
            game.setTime(time);
            DBHelper.save(game);
            res.redirect("/games");
            return null;
        }, new VelocityTemplateEngine());

        post("/games/:id/addplayer", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            Player player = DBHelper.findByUsername(loggedInUser);
            DBHelper.addPlayerToGame(player, game);
            DBHelper.addGameToPlayer(game, player);
            res.redirect("/games");
            return null;
        }, new VelocityTemplateEngine());

        get("/games/:id/players", (req, res) -> {
            String stringGameId = req.params(":id");
            Integer gameIntId = Integer.parseInt(stringGameId);
            Game game = DBHelper.find(gameIntId, Game.class);
            List<Player> players = DBHelper.playersPlaying(game);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            model.put("user", loggedInUser);
            model.put("players", players);
            model.put("game", game);
            model.put("template", "templates/Game/players.vtl");
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
