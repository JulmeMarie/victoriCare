package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.PersonOutputDTO;
import com.victoricare.api.dtos.outputs.PersonOutputDTO.PersonOutputDTOBuilder;
import com.victoricare.api.entities.Person;
import jakarta.annotation.Nullable;

public class PersonMapper {

    public static PersonOutputDTO toOutput(@Nullable Person person) {
        if (person == null)
            return null;
        return setProperties(PersonOutputDTO.builder(), person).build();
    }

    public static PersonOutputDTOBuilder<?, ?> setProperties(PersonOutputDTOBuilder<?, ?> builder, Person person) {
        builder.id(person.getId())
                .lastname(person.getLastname())
                .firstname(person.getFirstname())
                .gender(person.getGender())
                .phone(person.getPhone())
                .image(person.getImage())
                .country(person.getCountry())
                .creationDate(person.getCreationDate())
                .updateDate(person.getUpdateDate())
                .deletionDate(person.getDeletionDate())
                .roles(person.getRoles())
                .birthday(person.getBirthday())
                .updateAuthorId(person.getUpdateAuthorId())
                .deletionAuthorId(person.getDeletionAuthorId())
                .creationAuthorId(person.getCreationAuthorId())
                .address(AddressMapper.toOutput(person.getAddress()));
        return builder;
    }
}
