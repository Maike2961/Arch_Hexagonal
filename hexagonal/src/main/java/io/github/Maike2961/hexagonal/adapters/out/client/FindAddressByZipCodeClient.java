package io.github.Maike2961.hexagonal.adapters.out.client;

import io.github.Maike2961.hexagonal.adapters.out.client.response.AddressResponse;
import io.github.Maike2961.hexagonal.adapters.out.client.response.zipCodeResponse;
import io.github.Maike2961.hexagonal.application.core.exception.ZipCodeException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class FindAddressByZipCodeClient {

    private final RestClient restClient;

    public FindAddressByZipCodeClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public AddressResponse find(String zipCode) {
        zipCodeResponse response = restClient.get()
                .uri("/{zipCode}/json", zipCode)
                .retrieve()
                .body(zipCodeResponse.class);
        return Optional.ofNullable(response)
                .map(this::toAddressResponse)
                .orElseThrow(() -> new ZipCodeException(zipCode));
    }

    private AddressResponse toAddressResponse(zipCodeResponse response) {
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setStreet(response.logradouro());
        addressResponse.setCity(response.localidade());
        addressResponse.setState(response.estado());
        return addressResponse;
    }
}
