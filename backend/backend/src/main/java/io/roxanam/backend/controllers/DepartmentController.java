package io.roxanam.backend.controllers;

import io.roxanam.backend.dtos.LoungeTypeDto;
import io.roxanam.backend.mappers.LoungeTypeMapper;
import io.roxanam.backend.models.LoungeType;
import io.roxanam.backend.services.LoungeTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lounge-types")
@RequiredArgsConstructor
public class LoungeTypeController {
    private final LoungeTypeService loungeTypeService;

    @GetMapping
    public List<LoungeTypeDto> getAll() {
        return loungeTypeService.findAll().stream()
                .map(l -> LoungeTypeMapper.toLoungeTypeDto(l))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoungeTypeDto save(@RequestBody LoungeTypeDto loungeTypeDto) {
        return LoungeTypeMapper.toLoungeTypeDto(loungeTypeService.save(LoungeTypeMapper.toLoungeType(loungeTypeDto)));
    }

    @GetMapping("/id/{id}")
    public LoungeTypeDto getById(@PathVariable("id") Long id) {
        return LoungeTypeMapper.toLoungeTypeDto(loungeTypeService.findById(id));
    }

    @GetMapping("/name/{name}")
    public LoungeTypeDto getById(@PathVariable("name") String name) {
        return LoungeTypeMapper.toLoungeTypeDto(loungeTypeService.findByName(name));
    }

    @DeleteMapping("/name/{name}")
    public String deleteByName(@PathVariable("name") String name) {
        loungeTypeService.deleteByName(name);
        return "Lounge type deleted.";
    }

    @DeleteMapping("/id/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        loungeTypeService.deleteById(id);

        return "Lounge type deleted.";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody LoungeType loungeType) {
        return loungeTypeService.updateById(id, loungeType);
    }

}
