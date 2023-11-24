package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.AddressOutputDTO;
import com.victoricare.api.entities.Address;
import jakarta.annotation.Nullable;

public class AddressMapper {

    public static AddressOutputDTO toOutput(@Nullable Address address) {
        if (address == null)
            return null;
        return AddressOutputDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .addtionnal(address.getAddtionnal())
                .zipCode(address.getZipCode())
                .town(address.getTown())
                .country(address.getCountry())
                .build();
    }

}
