package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
   public void deleteAllUsers() {
      List<User> users = listUsers();
      for(User user : users) {
         sessionFactory.getCurrentSession().delete(user);
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findOwner(String car_model, String car_series) {
      TypedQuery<User> findUserQuery = sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.car.model = :car_model AND u.car.series = :car_series")
              .setParameter("car_model", car_model)
              .setParameter("car_series", car_series);
      List<User> findUserList = findUserQuery.getResultList();
      if (!findUserList.isEmpty()) {
         return findUserList.get(0);
      }
      return null;
   }
}
