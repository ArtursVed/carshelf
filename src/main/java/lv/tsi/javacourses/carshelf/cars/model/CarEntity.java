package lv.tsi.javacourses.carshelf.cars.model;

import  javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Entity(name = "Car")
@Table(name = "cars")
public class CarEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty(message = "Type shouldn't be empty")
    @Size(max = 200)
    @Column(name="type", length = 200, nullable = false)
    private String type;
    @NotEmpty
    @Size(min=10, max = 50)
    @Column(name="number", length = 50, nullable = false, unique = true)
    private String number;
    @NotEmpty
    @Size(max = 200)
    @Column(name="fuel", length = 200, nullable = false)
    private String fuel;
    @Min(value=1)
    @NotNull
    @Column(name="year", nullable = false)
    private int year;
    @Size(max = 1000)
    @Column(name="cost", length = 1000)
    private String cost ;

    @Column(name="image")
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", fuel='" + fuel + '\'' +
                ", year=" + year +
                ", cost='" + cost + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}