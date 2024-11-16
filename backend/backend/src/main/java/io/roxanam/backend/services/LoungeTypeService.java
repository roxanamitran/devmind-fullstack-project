package io.roxanam.backend.services;

import io.roxanam.backend.exceptions.LoungeTypeFindException;
import io.roxanam.backend.models.LoungeType;
import io.roxanam.backend.repositories.LoungeTypeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoungeTypeService {
    private final LoungeTypeRepository loungeTypeRepository;

    public List<LoungeType> findAll() {
        return loungeTypeRepository.findAll();
    }

    public LoungeType findById(Long id) {
        return loungeTypeRepository.findById(id)
                .orElseThrow(() -> new LoungeTypeFindException("Can not find lounge type with id: " + id));
    }

    public LoungeType findByName(String name) {
        return loungeTypeRepository.findByName(name)
                .orElseThrow(() -> new LoungeTypeFindException("Can not find lounge type with name: " + name));
    }


    public LoungeType save(LoungeType loungeType) {
        return loungeTypeRepository.save(loungeType);
    }

    @Transactional
    public void deleteByName(String name) {
        loungeTypeRepository.deleteByName(name);
    }

    @Transactional
    public void deleteById(Long id) {
        loungeTypeRepository.deleteById(id);
    }

    public String updateById(Long id, LoungeType loungeType) {
       LoungeType existingLoungeType = loungeTypeRepository.findById(id)
               .orElseThrow(() -> new LoungeTypeFindException("LoungeType with id: " + id + " not found"));

       existingLoungeType.setName(loungeType.getName());
       existingLoungeType.setPhoto(loungeType.getPhoto());
       loungeTypeRepository.save(existingLoungeType);

       return "LoungeType updated successfully";
    }
}
