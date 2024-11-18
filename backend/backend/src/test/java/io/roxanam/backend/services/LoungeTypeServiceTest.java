package io.roxanam.backend.services;


import io.roxanam.backend.exceptions.LoungeTypeFindException;
import io.roxanam.backend.models.LoungeType;
import io.roxanam.backend.repositories.LoungeTypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoungeTypeServiceTest {

    private static final Long LOUNGE_TYPE_ID = 1L;
    private static final String LOUNGE_TYPE_NAME = "massage";
    private static final String LOUNGE_TYPE_PHOTO = "m.img";

    @Mock
    private LoungeTypeRepository loungeTypeRepository;
    @InjectMocks
    private LoungeTypeService loungeTypeService;
    private LoungeType loungeType = new LoungeType(LOUNGE_TYPE_ID, LOUNGE_TYPE_NAME, LOUNGE_TYPE_PHOTO);;


    @Test
    void testSaveLoungeType() {
       when(loungeTypeRepository.save(loungeType)).thenReturn(loungeType);
       assertThat(loungeTypeService.save(loungeType)).isEqualTo(loungeType);
    }

    @Test
    void testFindByNameSuccess() {
        when(loungeTypeRepository.findByName(LOUNGE_TYPE_NAME))
                .thenReturn(Optional.of(loungeType));
        assertThat(loungeTypeService.findByName(LOUNGE_TYPE_NAME))
                .isEqualTo(loungeType);
    }

    @Test
    void testFindByNameFail() {
        when(loungeTypeRepository.findByName(LOUNGE_TYPE_NAME)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> loungeTypeService.findByName(LOUNGE_TYPE_NAME))
                .isInstanceOf(LoungeTypeFindException.class)
                .hasMessage("Can not find lounge type with name: " + LOUNGE_TYPE_NAME);
    }

    @Test
    void testFindByIdSuccess() {
        when(loungeTypeRepository.findById(LOUNGE_TYPE_ID))
                .thenReturn(Optional.of(loungeType));
        assertThat(loungeTypeService.findById(LOUNGE_TYPE_ID))
                .isEqualTo(loungeType);
    }

    @Test
    void testFindByIdFail() {
        when(loungeTypeRepository.findById(LOUNGE_TYPE_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> loungeTypeService.findById(LOUNGE_TYPE_ID))
                .isInstanceOf(LoungeTypeFindException.class)
                .hasMessage("Can not find lounge type with id: " + LOUNGE_TYPE_ID);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(loungeTypeRepository).deleteById(LOUNGE_TYPE_ID);
        assertThat(loungeTypeService.deleteById(LOUNGE_TYPE_ID)).isEqualTo("LoungeType deleted successfully");
    }
    
    @Test
    public void testDeleteByName() {
        doNothing().when(loungeTypeRepository).deleteByName(LOUNGE_TYPE_NAME);
        assertThat(loungeTypeService.deleteByName(LOUNGE_TYPE_NAME)).isEqualTo("LoungeType deleted successfully");
    }
    
   @Test
    public void testUpdateSuccess() {
        when(loungeTypeRepository.findById(LOUNGE_TYPE_ID)).thenReturn(Optional.of(loungeType));
        when(loungeTypeRepository.save(loungeType)).thenReturn(loungeType);

        assertThat(loungeTypeService.updateById(LOUNGE_TYPE_ID, loungeType)).isEqualTo("LoungeType updated successfully");
   }

   @Test
    public void testUpdateFail() {
        when(loungeTypeRepository.findById(LOUNGE_TYPE_ID)).thenReturn(Optional.empty());
        when(loungeTypeRepository.save(loungeType)).thenReturn(null);
        assertThatThrownBy(() -> loungeTypeService.updateById(LOUNGE_TYPE_ID, loungeType))
               .isInstanceOf(LoungeTypeFindException.class)
               .hasMessage("Can not find lounge type with id: " + LOUNGE_TYPE_ID);
   }

   @Test
    public void testFindAll() {
       List<LoungeType> loungeTypes = List.of(loungeType);

       when(loungeTypeRepository.findAll()).thenReturn(loungeTypes);
       assertThat(loungeTypeService.findAll()).isEqualTo(loungeTypes);
   }
}
