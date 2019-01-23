package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.carshelf.cars.model.CarEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationStatus;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
//import java.awt.print.car;
import java.io.Serializable;

@ViewScoped
@Named
public class CarBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;
    private Long id;
    private CarEntity car;

    public void openCar() {
        System.out.println("Opening car " + id);
        car = em.find(CarEntity.class, id);
    }

    @Transactional
    public void reserve(Long id) {
        System.out.println("Trying to reserve car " + id
                + " for user " + currentUser.getUser().getId());

        CarEntity car = em.find(CarEntity.class, id);

        ReservationEntity reservation = new ReservationEntity();
        reservation.setCar(car);
        reservation.setUser(currentUser.getUser());
        reservation.setStatus(ReservationStatus.ACTIVE);

        em.persist(reservation);
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