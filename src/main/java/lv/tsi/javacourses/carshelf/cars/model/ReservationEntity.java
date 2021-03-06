package lv.tsi.javacourses.carshelf.cars.model;

import lv.tsi.javacourses.carshelf.auth.model.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Reservation")
@Table(name = "reservations")
public class ReservationEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private CarEntity car;
    @ManyToOne
    private UserEntity user;
    @Column
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Column
    private LocalDateTime created;

    @PrePersist
    public void onCreate() {
        created = LocalDateTime.now();
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

    public void setBook(CarEntity car) {
        this.car = car;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setCar(CarEntity car) {
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "id=" + id +
                ", car=" + car +
                ", user=" + user +
                ", status=" + status +
                ", created=" + created +
                '}';
    }
}