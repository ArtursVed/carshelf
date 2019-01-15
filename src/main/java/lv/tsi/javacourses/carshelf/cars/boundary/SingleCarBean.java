package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import java.awt.print.Car;
import java.io.Serializable;

@ViewScoped
@Named
public class SingleCarBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    private Long id;
    private CarEntity car;

    public void openCar() {
        System.out.println("Opening car " + id);
        car = em.find(CarEntity.class, id);
    }

    public CarEntity getCar() {
        return car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}