package br.edu.uepb.diario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericResponseErrorDTO {
    private String error;
}
