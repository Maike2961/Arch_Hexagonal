package io.github.Maike2961.hexagonal.application.ports.out;

import io.github.Maike2961.hexagonal.application.core.domain.Address;

public interface FindAddressZipCodeOutputPort {
    Address find(String zipCode);
}
