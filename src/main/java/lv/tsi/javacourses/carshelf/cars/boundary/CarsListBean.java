package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
public class CarsListBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;
    private List<String> cars;
    private Long id;
    private boolean delete = false;

    public List<CarEntity> getCar() {
        if (term == null) {
            return em.createQuery("select c from Car c", CarEntity.class).getResultList();
        } else {
            return em.createQuery("select c from Car c where lower(c.type) like :term", CarEntity.class)
                    .setParameter("term", "%" + term.toLowerCase() + "%")
                    .getResultList();
        }
    }

  // @Transactional
  //  public void deletecars(Long id) {
  //    Query query = em.createQuery("DELETE FROM Car c WHERE c.id =" + id);
  //       query.executeUpdate();
 //   }


     @Transactional
    public String deletecars () {
    CarEntity car;
    car =em.find(CarEntity .class,id);
    em.remove(car);
    delete = true;
    return null;
    }

    public List<String> getCars() {
        return cars;
    }

    public void setCars(List<String> cars) {
        this.cars = cars;
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

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}



