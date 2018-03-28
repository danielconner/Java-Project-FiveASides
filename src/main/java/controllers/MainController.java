package controllers;

import com.codeclan.db.DBHelper;
import com.codeclan.db.Seeds;
import com.codeclan.models.Venue;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.SparkBase.staticFileLocation;

public class MainController {


    public static void main(String[] args) {

        Seeds.seedData();

        staticFileLocation("/public");

        LoginController loginController = new LoginController();
        PlayerController playerController = new PlayerController();
        GameController gameController = new GameController();
        VenueController venueController = new VenueController();

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String loggedInUser = LoginController.getLoggedInUserName(req, res);
            List<Venue> venues = DBHelper.getAll(Venue.class);
            model.put("venues", venues);
            model.put("user", loggedInUser);
            model.put("template","templates/main.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());
    }
}


