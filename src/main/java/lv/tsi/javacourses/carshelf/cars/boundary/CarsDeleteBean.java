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
import java.util.Queue;


@Named
@SessionScoped
public class CarsDeleteBean  implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private String term;
    private Long id;
    private CarEntity car;
    private List<String> cars;


    @Transactional
    public void deleteCars(String c) {
        System.out.println("delete method executed");
        Long c_id = ((Long) em.createQuery("select c.id from Car where c.type = :type")
                .setParameter("type", c)

                .getSingleResult());

        CarEntity type = em.find(CarEntity.class, c_id);

        Query q = em.createNativeQuery("DELETE FROM CARSHELF_DB.PUBLIC.CARS where TYPE  = " + c_id);

        q.executeUpdate();
    }



    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void delete(){
        System.out.println("Delete");

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public List<String> getCars() {
        return cars;
    }

    public void setCars(List<String> cars) {
        this.cars = cars;
    }
}
