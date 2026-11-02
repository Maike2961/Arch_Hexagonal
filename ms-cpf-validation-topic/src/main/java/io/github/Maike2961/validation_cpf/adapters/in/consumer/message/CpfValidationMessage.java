package io.github.Maike2961.validation_cpf.adapters.in.consumer.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpfValidationMessage {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("isValidCpf")
    private Boolean isValidCpf;
}
