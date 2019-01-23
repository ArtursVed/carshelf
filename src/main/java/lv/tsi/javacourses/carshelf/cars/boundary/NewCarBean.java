package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;

@ViewScoped
@Named
public class NewCarBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private CarEntity car = new CarEntity();
    private boolean created = false;

    @Transactional
    public String createCar() {
        em.persist(car);
        car = new CarEntity();
        created = true;
//        return "book-created.xhtml";
        return null;
    }

    public boolean isCreated() {
        return created;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }
}