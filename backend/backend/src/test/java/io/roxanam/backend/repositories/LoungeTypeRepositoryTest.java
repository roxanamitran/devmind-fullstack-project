package io.roxanam.backend.repositories;

import io.roxanam.backend.models.LoungeType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class LoungeTypeRepositoryTest {

    private static final Long LOUNGE_TYPE_ID = 1L;
    private static final String LOUNGE_TYPE_NAME = "Massage";
    private static final String LOUNGE_TYPE_PHOTO = "m.img";
    private static final LoungeType LOUNGE_TYPE = new LoungeType(LOUNGE_TYPE_ID, LOUNGE_TYPE_NAME, LOUNGE_TYPE_PHOTO);

    @Autowired
    private LoungeTypeRepository loungeTypeRepository;


    @Test
    public void testFindByNameWhenPresent() {
        loungeTypeRepository.save(LOUNGE_TYPE);

        Optional<LoungeType> optionalLoungeType = loungeTypeRepository.findByName(LOUNGE_TYPE_NAME);

        assertTrue(optionalLoungeType.isPresent());

        LoungeType loungeType = optionalLoungeType.get();
        assertEquals(LOUNGE_TYPE_ID, loungeType.getId());
        assertEquals(LOUNGE_TYPE_NAME, loungeType.getName());
        assertEquals(LOUNGE_TYPE_PHOTO, loungeType.getPhoto());
    }

    @Test
    public void testFindByNameWhenDBEmpty() {
        Optional<LoungeType> optionalLoungeType = loungeTypeRepository.findByName(LOUNGE_TYPE_NAME);

        assertTrue(optionalLoungeType.isEmpty());
    }

    @Test
    public void testDeleteByNameWhenPresent() {
        loungeTypeRepository.save(LOUNGE_TYPE);
        loungeTypeRepository.deleteByName(LOUNGE_TYPE_NAME);
        assertTrue(loungeTypeRepository.findByName(LOUNGE_TYPE_NAME).isEmpty());
    }

    @Test
    public void testDeleteByNameWhenNotPresent() {
        loungeTypeRepository.save(LOUNGE_TYPE);
        loungeTypeRepository.deleteByName("TEST");
        assertTrue(loungeTypeRepository.findAll().size() == 1);
    }






}
