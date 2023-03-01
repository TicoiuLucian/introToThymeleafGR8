package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "sold = false")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="carid")
    private Integer carId;

    private String manufacturer;

    private String model;

    private Float price;

    private Boolean sold = false;

    private LocalDate soldDate;

    private LocalDate addDate;
}


