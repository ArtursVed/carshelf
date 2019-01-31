package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@Named
@SessionScoped
public class CarsListBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;
    private List<String> cars;


    public List<CarEntity> getCar() {
        if (term == null) {
            return em.createQuery("select c from Car c", CarEntity.class).getResultList();
        } else {
            return em.createQuery("select c from Car c where lower(c.type) like :term", CarEntity.class)
                    .setParameter("term", "%" + term.toLowerCase() + "%")
                    .getResultList();
        }
    }

    @Transactional
    public void deleteFromCars(String car) {
        System.out.println("delete method executed");
        Long car_id = ((Long) em.createQuery("select c.id from Car  c where c.id = id  and  c.type= :type")
                .setParameter("car", car)
                .getSingleResult());
        CarEntity cars = em.find(CarEntity.class, car_id);
        Query q = em.createNativeQuery("DELETE FROM CARS where ID = " + car_id);
        q.executeUpdate();
    }


   // public String removeCar(){

     //   em.remove(term);

     //   Query q = em.createNativeQuery("DELETE FROM CARSHELF_DB.PUBLIC.CARS where ID = " + term   );

      //  q.executeUpdate();

    //    return null;

 //   }


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
}