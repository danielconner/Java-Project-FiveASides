package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.models.Game;
import com.codeclan.models.Venue;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class VenueController {

    public VenueController() {
        this.setupEndPoints();
    }

    public static void setupEndPoints() {

        get("/venues", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Venue> venues = DBHelper.getAll(Venue.class);
            model.put("venues", venues);
            model.put("user", loggedInUser);
            model.put("template", "templates/Venue/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

        get("/venues/:id/games", (req, res) -> {
            int venueId = Integer.parseInt(req.params(":id"));
            Venue venue = DBHelper.find(venueId, Venue.class);
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Game> games = DBHelper.gamesAtVenue(venue);
            model.put("user", loggedInUser);
            model.put("venue", venue);
            model.put("games", games);
            model.put("template", "templates/Venue/games/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}
