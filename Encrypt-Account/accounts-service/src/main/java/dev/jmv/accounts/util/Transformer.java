package dev.jmv.basic.util;

import dev.jmv.basic.dto.BasicDTO;
import dev.jmv.basic.entity.Basic;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class Transformer {
    public static List<Basic> dtoToEntity(List<BasicDTO> basicDTOS) {
        log.debug("Converting DTO to Entity");
        var basicList = new ArrayList<Basic>();

        basicDTOS.forEach(
                basic -> {
                    basicList.add(
                            Basic.builder()
                                    .id(basic.getId())
                                    .title(basic.getTitle())
                                    .build());
                }
        );
        return basicList;
    }

    public static BasicDTO entityToDto(Basic basic) {
        log.debug("Converting Entity to DTO");

        return BasicDTO.builder()
                .id(basic.getId())
                .title(basic.getTitle())
                .build();
    }

    public static Basic dtoToEntity(BasicDTO basicDTO) {
        log.debug("Converting DTO to Entity");
        return Basic.builder()
                .id(basicDTO.getId())
                .title(basicDTO.getTitle())
                .build();
    }

    public static List<BasicDTO> entityToDto(List<Basic> basics) {
        log.debug("Converting Entity to DTO");
        var basicDTOList = new ArrayList<BasicDTO>();

        basics.forEach(
                basic -> {
                    basicDTOList.add(
                            BasicDTO.builder()
                                    .id(basic.getId())
                                    .title(basic.getTitle())
                                    .build());
                }
        );
        return basicDTOList;
    }
}
