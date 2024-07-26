package dev.jmv.basic.service;

import dev.jmv.basic.dto.BasicDTO;
import dev.jmv.basic.exception.RecordNotFoundException;
import dev.jmv.basic.repository.BasicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.jmv.basic.util.Transformer.dtoToEntity;
import static dev.jmv.basic.util.Transformer.entityToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicService {

    private final BasicRepository basicRepository;

    public List<BasicDTO> getAllBasics() throws Exception {
        log.info("Get All Basics");
        return entityToDto(basicRepository.findAll());
    }

    public List<BasicDTO> createAllBasics(List<BasicDTO> basicDTOS) {
        var basics = basicRepository.saveAll(dtoToEntity(basicDTOS));
        return entityToDto(basics);
    }

    public BasicDTO createBasics(BasicDTO basicDTO) {
        var basic = dtoToEntity(basicDTO);
        return entityToDto(basicRepository.save(basic));
    }

    public void delete(BasicDTO basicDTO) {
        basicRepository.delete(dtoToEntity(basicDTO));
    }

    public void delete(int id) {
        basicRepository.deleteById(id);
    }

    public BasicDTO getById(int id) {
        return entityToDto(basicRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not Found")));
    }

    public void updateById(int id, BasicDTO basicDTO) throws RecordNotFoundException {
        basicRepository.findById(id).ifPresentOrElse(basic -> {
            basic.setTitle(basicDTO.getTitle());
            basicRepository.save(basic);
        }, () -> {
            throw new RecordNotFoundException("Record Not Found");
        });
    }
}
