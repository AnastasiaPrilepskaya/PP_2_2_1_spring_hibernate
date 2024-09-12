package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUsersWithCar(String model, int series) {
      List<User> users = new ArrayList<>();
      try (Session session = sessionFactory.openSession()) {
         String hql = "SELECT c.user FROM Car c WHERE c.model = :model AND c.series = :series";
         Query<User> query = session.createQuery(hql, User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         users = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return users;
   }
}