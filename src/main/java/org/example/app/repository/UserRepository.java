package org.example.app.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.app.db_connect.DbConnectInit;
import org.example.app.db_connect.IConnection;
import org.example.app.entity.User;
import org.example.app.repository.interfaces.IRepository;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.UserFilters;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;
import java.util.Optional;

public class UserRepository implements IRepository<User> {
    private final IConnection connection;
    private static final Logger DbConnectInitLogger = Logger.getLogger(DbConnectInit.class.getName());

    public UserRepository(IConnection connection) {
        this.connection = connection;
    }

    @Override
    public ActionAnswer create(User obj) {
        ActionAnswer actionAnswer = new ActionAnswer();
        boolean isEmailExists = checkEmailExists(obj.getEmail());
        if (isEmailExists) {
            actionAnswer.addError("Email already exists");
            return actionAnswer;
        }
        try(Session session = this.connection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(obj);
            session.getTransaction().commit();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User created successfully");
            return actionAnswer;
        } catch (HeadlessException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    public boolean checkEmailExists(String emailValue) {
        try (Session session = this.connection.getSessionFactory().openSession()) {
            User user = session.get(User.class, emailValue);
            return user != null;
        } catch (Exception e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<List<User>> readAll(List<String> excludeColumns, int limit, int offset) {
        try (Session session = this.connection.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            cq.from(User.class);
            Query<User> query = session.createQuery(cq);
            if (limit > 0) {
                query.setMaxResults(limit);
            }
            if (offset > 0) {
                query.setFirstResult(offset);
            }
            List<User> result = query.getResultList();
            if (result.isEmpty()) {
                return Optional.empty();
            }
            if (excludeColumns != null && !excludeColumns.isEmpty()) {
                List <User> filterResult = UserFilters.filterUsers(result, excludeColumns);
                return Optional.of(filterResult);
            }
            return Optional.of(result);
        } catch (HibernateException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public ActionAnswer update(User obj) {
        ActionAnswer actionAnswer = new ActionAnswer();
        try (Session session = this.connection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(obj);
            session.getTransaction().commit();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User update successfully");
            return actionAnswer;
        } catch (HeadlessException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    @Override
    public ActionAnswer delete(Long id) {
        ActionAnswer actionAnswer = new ActionAnswer();
        try (Session session = this.connection.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            actionAnswer.setIsSuccess();
            actionAnswer.setSuccessMsg("User deleted successfully");
            return actionAnswer;
        } catch (HeadlessException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            actionAnswer.addError(e.getMessage());
            return actionAnswer;
        }
    }

    @Override
    public Optional<List<User>> readById(Long id, List<String> excludeColumns) {
        try (Session session = this.connection.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            cq.where(cb.equal(root.get("id"), id));
            List <User> result = session.createQuery(cq).getResultList();
            if (result.isEmpty()) {
                return Optional.empty();
            }
            if (excludeColumns != null && !excludeColumns.isEmpty()) {
                List <User> filterResult = UserFilters.filterUsers(result, excludeColumns);
                return Optional.of(filterResult);
            }
            return Optional.of(result);
        } catch (HibernateException e) {
            DbConnectInitLogger.log(Level.SEVERE, e.getMessage());
            return Optional.empty();
        }
    }

}
