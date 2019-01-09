package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class CarBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;

    public List<CarEntity> getCar() {
        if (term == null) {
            return em.createQuery("select c from Car c", CarEntity.class).getResultList();
        } else {
            return em.createQuery("select c from Car c where lower(c.type) like :term", CarEntity.class)
                    .setParameter("term", "%" + term.toLowerCase() + "%")
                    .getResultList();
        }
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void doSearch() {
        System.out.println("Searching");
    }
}