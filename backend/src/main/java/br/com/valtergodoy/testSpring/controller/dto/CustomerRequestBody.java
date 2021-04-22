package br.com.valtergodoy.testSpring.controller.dto;

import br.com.valtergodoy.testSpring.model.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestBody {
    private Long id;

    @NotEmpty
    @Schema(description = "Customer's Name.", example = "Valter Godoy")
    private String name;


    @NotEmpty
    @Schema(description = "Customer's identity, use CPF or other identity.", example = "348.718.778-76")
    private String identity;

    @Size(max = 200)
    @Schema(description = "Street Address", example = "Rua Marília de Dirceu")
    private String street;

    @Size(max = 50)
    @Schema(description = "Number", example = "379")
    private String number;

    @Schema(description = "Complement", example = "Green House.")
    private String complement;

    @Size(max = 200)
    @Schema(description = "Neighbourhood", example = "Parque Olaria")
    private String neighbourhood;

    @Size(max = 200)
    @Schema(description = "City", example = "Santa Bárbara D' Oeste")
    private String city;

    @Size(max = 100)
    @Schema(description = "State", example = "São Paulo")
    private String state;

    @Size(max = 100)
    @Schema(description = "Country", example = "Brazil")
    private String country;

    @Size(max = 20)
    @Schema(description = "ZipCode", example = "13458-025")
    private String zipcode;
}
