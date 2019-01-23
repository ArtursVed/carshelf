package lv.tsi.javacourses.carshelf.cars.boundary;


import lv.tsi.javacourses.carshelf.auth.boundary.CurrentUser;
import lv.tsi.javacourses.carshelf.cars.model.ReservationEntity;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@ViewScoped
public class MyCarsBean implements Serializable {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private CurrentUser currentUser;
    private List<ReservationEntity> availableResult;
    private List<ReservationEntity> inQueueResult;
    private List<ReservationEntity> takenResult;


    public void prepare() {
        availableResult = new ArrayList<>();
        inQueueResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery(
                "select r from Reservation r " +
                        "where r.user = :user and r.status = 'ACTIVE'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser())
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
            } else {
                inQueueResult.add(r);
            }
        }

        takenResult = em.createQuery("select r from Reservation r " +
                "where r.user = :user and r.status = 'TAKEN'", ReservationEntity.class)
                .setParameter("user", currentUser.getUser())
                .getResultList();
    }

    public List<ReservationEntity> getAvailableCars() {
        return availableResult;
    }

    public List<ReservationEntity> getInQueueCars() {
        return inQueueResult;
    }

    public List<ReservationEntity> getTakenCars() {
        return takenResult;
    }


}