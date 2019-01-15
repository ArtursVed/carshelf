package lv.tsi.javacourses.carshelf.cars.boundary;


import lv.tsi.javacourses.carshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.carshelf.cars.model.CarEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationStatus;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Named
public class CarReservationBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;

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


    public List<ReservationEntity> getReservations() {
        return em.createQuery("select r from Reservation r",
                ReservationEntity.class)
                .getResultList();
    }
}