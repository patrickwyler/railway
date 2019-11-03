package ch.teko.railway.unittests;

import ch.teko.railway.entities.Train;
import ch.teko.railway.mappers.TrainMapper;
import ch.teko.railway.models.TrainModel;
import ch.teko.railway.repositories.TrainRepository;
import ch.teko.railway.services.impl.TrainServiceImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@Getter(AccessLevel.PROTECTED)
@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainServiceImplUnitTest {

    public static final String NAME = "BLS 1";

    TrainModel TRAIN_MODEL;

    Train TRAIN;

    @InjectMocks
    TrainServiceImpl trainServiceImpl;

    @Mock
    TrainRepository trainRepository;

    @Mock
    TrainMapper trainMapper;

    @Before
    public void setUp() {
        TRAIN_MODEL = TrainModel.builder()
                .id(1L)
                .name(NAME)
                .build();

        TRAIN = Train.builder()
                .id(1L)
                .name(NAME)
                .build();
    }

    @Test
    public void testGetAllTrains() {
        // Arrange
        final List<Train> results = new ArrayList<>();
        results.add(TRAIN);
        when(getTrainRepository().findAll()).thenReturn(results);

        // Act
        getTrainServiceImpl().getAllTrains();

        // Assert
        verify(getTrainMapper(), times(1)).trainsToTrainModels(results);
    }

    @Test
    public void testGetAllStations_noResult() {
        // Arrange
        final List<Train> results = new ArrayList<>();
        when(getTrainRepository().findAll()).thenReturn(results);

        // Act
        getTrainServiceImpl().getAllTrains();

        // Assert
        verify(getTrainMapper(), times(1)).trainsToTrainModels(results);
    }

}
