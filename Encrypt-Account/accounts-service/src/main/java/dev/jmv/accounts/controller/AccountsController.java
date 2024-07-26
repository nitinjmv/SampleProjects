package dev.jmv.accounts.controller;

import dev.jmv.basic.dto.APIResponse;
import dev.jmv.basic.dto.BasicDTO;
import dev.jmv.basic.service.BasicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.jmv.basic.Constants.BASICS_ENDPOINT;

@Slf4j
@RestController
@RequestMapping(BASICS_ENDPOINT)
@RequiredArgsConstructor
public class AccountsController {

    private final BasicService basicService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> getAllBasics() throws Exception {

        var response = basicService.getAllBasics();

        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .data(response)
                        .resultCount(response.size())
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getById(@PathVariable int id) {
        var response = basicService.getById(id);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .data(response)
                        .build()
        );
    }

    @PostMapping(value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createBasics(@RequestBody BasicDTO basicDTO) {
        var response = basicService.createBasics(basicDTO);

        return new ResponseEntity<>(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .data(response)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createAllBasics(@RequestBody List<BasicDTO> basicDTOS) {

        var response = basicService.createAllBasics(basicDTOS);

        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .data(response)
                        .resultCount(response.size())
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBasic(@RequestBody BasicDTO basicDTO) {
        basicService.delete(basicDTO);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBasicById(@PathVariable int id) {
        basicService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<APIResponse> updateById(@PathVariable int id, @RequestBody BasicDTO basicDTO) {
        basicService.updateById(id, basicDTO);
        return ResponseEntity.accepted().build();
    }
}
