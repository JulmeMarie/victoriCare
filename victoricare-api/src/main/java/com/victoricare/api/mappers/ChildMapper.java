package com.victoricare.api.mappers;

import com.victoricare.api.dtos.outputs.ChildOutputDTO;
import com.victoricare.api.dtos.outputs.ChildOutputDTO.ChildOutputDTOBuilder;
import com.victoricare.api.entities.Child;
import jakarta.annotation.Nullable;

public class ChildMapper extends PersonMapper {

    public static ChildOutputDTO toOutput(@Nullable Child child) {
        if (child == null)
            return null;

        ChildOutputDTOBuilder<?, ?> builder = (ChildOutputDTOBuilder<?, ?>) setProperties(ChildOutputDTO.builder(),
                child);

        return builder.family(FamilyMapper.toOutput(child.getFamily())).build();
    }

}
