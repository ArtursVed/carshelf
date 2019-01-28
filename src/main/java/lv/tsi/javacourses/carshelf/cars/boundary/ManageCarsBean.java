package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationStatus;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class ManageCarsBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    private List<ReservationEntity> availableResult;
    private List<ReservationEntity> takenResult;

    public void prepare() {
        availableResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery(
                "select r from Reservation r " +
                        "where r.status = 'ACTIVE'", ReservationEntity.class)
                .getResultList();

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            Optional<ReservationEntity> firstReservation = em.createQuery(
                    "select r from Reservation r " +
                            "where r.car = :car and r.status <> 'CLOSED' " +
                            "order by r.created", ReservationEntity.class)
                    .setParameter("car", r.getCar())
                    .getResultStream()
                    .findFirst();
            if (firstReservation.isEmpty() || firstReservation.get().getId().equals(reservationId)) {
                availableResult.add(r);
            }
        }

        takenResult = em.createQuery("select r from Reservation r " +
                "where r.status = 'TAKEN'", ReservationEntity.class)
                .getResultList();
    }

    @Transactional
    public void giveCar(ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(ReservationStatus.TAKEN);
        prepare();
    }

    @Transactional
    public void takeCar(ReservationEntity reservation) {
        ReservationEntity r = em.merge(reservation);
        r.setStatus(ReservationStatus.CLOSED);
        prepare();
    }


    public List<ReservationEntity> getAvailableCars() {
        return availableResult;
    }

    public List<ReservationEntity> getTakenCars() {
        return takenResult;
    }

}