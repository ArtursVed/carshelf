package lv.tsi.javacourses.carshelf.auth.boundary;

import lv.tsi.javacourses.carshelf.auth.model.UserEntity;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class UserBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    public List<UserEntity> getUsers() {
        return em.createQuery("select u from User u",
                UserEntity.class).getResultList();
    }
}