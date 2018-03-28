package com.codeclan.db;

import com.codeclan.models.Day;
import com.codeclan.models.Game;
import com.codeclan.models.Player;
import com.codeclan.models.Venue;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper {

    private static Transaction transaction;
    private static Session session;

    public static void save(Object object) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> List<T> getList(Criteria criteria) {
        List<T> results = null;
        try {
            transaction = session.beginTransaction();
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = criteria.list();
            ;
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public static <T> T getUnique(Criteria criteria) {
        T result = null;
        try {
            transaction = session.beginTransaction();
            result = (T) criteria.uniqueResult();
            ;
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public static <T> void deleteAll(Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Criteria cr = session.createCriteria(classType);
            List<T> results = cr.list();
            for (T result : results) {
                session.delete(result);
            }
            transaction.commit();
        } catch (HibernateException ex) {
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }


    public static void delete(Object object) {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static <T> List<T> getAll(Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(classType);
        return getList(cr);
    }

    public static <T> T find(int id, Class classType) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(classType);
        cr.add(Restrictions.eq("id", id));
        return getUnique(cr);
    }

    public static List<Player> sortPlayersByMostGamesPlayed() {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Player> result = null;
        Criteria cr = session.createCriteria(Player.class);
        cr.addOrder(Order.desc("gamesPlayed"));
        result = getList(cr);
        return result;
    }

    public static Player findByUsername(String username) {
        session = HibernateUtil.getSessionFactory().openSession();
        Player result = null;
        Criteria cr = session.createCriteria(Player.class);
        cr.add(Restrictions.eq("username", username));
        result = getUnique(cr);
        return result;
    }

    public static List<Player> playersPlaying(Game game) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Player.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.createAlias("signedUpForGames", "signedUpForGames");
        cr.add(Restrictions.eq("signedUpForGames.id", game.getId()));
        return getList(cr);
    }


    public static void addPlayerToGame(Player player, Game game) {
        game.addPlayers(player);
        save(game);
    }

    public static void addGameToPlayer(Game game, Player player) {
        player.signUpForGame(game);
        save(player);
    }

    public static List<Game> gamesAtVenue(Venue venue) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Game.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.createAlias("venue", "venue");
        cr.add(Restrictions.eq("venue.id", venue.getId()));
        return getList(cr);

    }

    public static List<Game> gamesPlayerHasSignedUpFor(Player player) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Game.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        cr.createAlias("players", "players");
        cr.add(Restrictions.eq("players.id", player.getId()));
        return getList(cr);
    }

    public static List<Venue> locations(String location) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Venue.class);
        cr.add(Restrictions.eq("location", location));
        return getList(cr);
    }

//    public static List<Game> gamesByLocation(String location) {
//        List<Venue> venues = locations(location);
//        List<Game> gamesAtLocation = new ArrayList<>();
//        for (Venue venue : venues) {
//
//
//
//        }
//    }

    public static boolean userExists(String username) {
        session = HibernateUtil.getSessionFactory().openSession();
        Criteria cr = session.createCriteria(Player.class);
        cr.add(Restrictions.eq("username", username));
        Player user = DBHelper.getUnique(cr);
        if (user == null) {
            return false;
        }
        else {return true;}
    }

}

