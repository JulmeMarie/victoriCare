package com.victoricare.api.dtos.inputs;

import org.springframework.data.domain.Sort.Direction;
import com.victoricare.api.validators.ValidPage;
import lombok.Data;

@ValidPage
@Data
public class PageInputDTO {

    private Integer page = 0;

    private Integer size = 10;

    private String[] sort = new String[] { "id," + Direction.ASC.name() };
}
