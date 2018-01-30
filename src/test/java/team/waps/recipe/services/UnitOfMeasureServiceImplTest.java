package team.waps.recipe.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import team.waps.recipe.commands.UnitOfMeasureCommand;
import team.waps.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import team.waps.recipe.models.UnitOfMeasure;
import team.waps.recipe.repositories.UnitOfMeasureRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    private static final Long UOM_ID1 = 1L;
    private static final Long UOM_ID2 = 2L;
    private static final Long UOM_ID3 = 3L;
    private static final String DESCRIPTION1 = "Description";
    private static final String DESCRIPTION2 = "Description2";
    private static final String DESCRIPTION3 = "Description3";

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureCommand());

    }

    @Test
    public void testListAllUoms() {
        // given
        UnitOfMeasure uom1 = new UnitOfMeasure();
        UnitOfMeasure uom2 = new UnitOfMeasure();
        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom1.setId(UOM_ID1);
        uom1.setDescription(DESCRIPTION1);
        uom2.setId(UOM_ID2);
        uom2.setDescription(DESCRIPTION2);
        uom3.setId(UOM_ID3);
        uom3.setDescription(DESCRIPTION3);
        List<UnitOfMeasure> units = new ArrayList<>();
        units.add(uom1);
        units.add(uom2);
        units.add(uom3);
        when(unitOfMeasureRepository.findAll()).thenReturn(units);

        // when
        Set<UnitOfMeasureCommand> commandSet = unitOfMeasureService.listAllUoms();

        // then
        assertEquals(3, commandSet.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}