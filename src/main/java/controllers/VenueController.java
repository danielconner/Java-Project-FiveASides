package controllers;

import com.codeclan.db.DBHelper;
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
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


    }
}
