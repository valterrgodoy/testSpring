package br.com.valtergodoy.testSpring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder

//Annotations for Bean Validation added here to preserve the DataBase integrity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotEmpty
    private String identity;

    @Size(max = 200)
    private String street;

    @Size(max = 50)
    private String number;

    private String complement;

    @Size(max = 200)
    private String neighbourhood;

    @Size(max = 200)
    private String city;

    @Size(max = 100)
    private String state;

    @Size(max = 100)
    private String country;

    @Size(max = 20)
    private String zipcode;

}
