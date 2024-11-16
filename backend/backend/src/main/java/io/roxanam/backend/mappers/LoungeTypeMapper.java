package io.roxanam.backend.mappers;

import io.roxanam.backend.dtos.LoungeTypeDto;
import io.roxanam.backend.models.LoungeType;

public class LoungeTypeMapper {

    public static LoungeType toLoungeType(LoungeTypeDto loungeTypeDto) {
        LoungeType loungeType = new LoungeType();
        loungeType.setName(loungeTypeDto.getName());
        loungeType.setId(loungeTypeDto.getId());
        loungeType.setPhoto(loungeTypeDto.getPhoto());

        return loungeType;
    }

    public static LoungeTypeDto toLoungeTypeDto(LoungeType loungeType) {
        LoungeTypeDto loungeTypeDto = new LoungeTypeDto();
        loungeTypeDto.setId(loungeType.getId());
        loungeTypeDto.setName(loungeType.getName());
        loungeTypeDto.setPhoto(loungeType.getPhoto());

        return loungeTypeDto;
    }


}
