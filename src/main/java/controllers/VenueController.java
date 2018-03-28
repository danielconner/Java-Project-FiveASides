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
            String location = DBHelper.getUserLocation(loggedInUser);
            List<Venue> venues = DBHelper.locations(location);
            model.put("venues", venues);
            model.put("user", loggedInUser);
            model.put("template", "templates/Venue/index.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());

            get("/venues/games", (req, res) -> {
                int venueId= Integer.parseInt(req.queryParams("venue"));
                Venue venue = DBHelper.find(venueId, Venue.class);
                List<Game> games = DBHelper.gamesAtVenue(venue);
                Map<String, Object> model = new HashMap<>();
                String loggedInUser = LoginController.getLoggedInUserName(req, res);
                model.put("venue", venue);
                model.put("games", games);
                model.put("user", loggedInUser);
                model.put("template", "templates/Venue/games/index.vtl");
                return new ModelAndView(model, "templates/layout.vtl");

            }, new VelocityTemplateEngine());


    }


}
