package lv.tsi.javacourses.carshelf.cars.boundary;

import lv.tsi.javacourses.carshelf.cars.model.CarEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationEntity;
import lv.tsi.javacourses.carshelf.cars.model.ReservationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

// TRACE
// DEBUG
// INFO
// WARN
// ERROR
@Named
@ViewScoped
public class ManageCarsBean implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(ManageCarsBean.class);

    @PersistenceContext
    private EntityManager em;
    private List<ReservationEntity> availableResult;
    private List<ReservationEntity> takenResult;

    public void prepare() {
        logger.debug("Preparing cars for manager");
        availableResult = new ArrayList<>();
        List<ReservationEntity> userReservations = em.createQuery(
                "select r from Reservation r " +
                        "where r.status = 'ACTIVE'", ReservationEntity.class)
                .getResultList();

        logger.debug("Selected {} reservations", userReservations.size());

        for (ReservationEntity r : userReservations) {
            Long reservationId = r.getId();
            logger.trace("Checking reservation {}", r);
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
        logger.info("Giving the cars {}", reservation);
        ReservationEntity r = em.merge(reservation);
        r.setStatus(ReservationStatus.TAKEN);
        prepare();
    }

    @Transactional
    public void takeCar(ReservationEntity reservation) {
        logger.info("Getting the cars back {}", reservation);
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