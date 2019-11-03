package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Train;
import ch.teko.railway.mappers.TrainMapper;
import ch.teko.railway.models.TrainModel;
import ch.teko.railway.repositories.TrainRepository;
import ch.teko.railway.services.TrainService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainServiceImpl implements TrainService {

    TrainRepository trainRepository;
    TrainMapper trainMapper;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository, TrainMapper trainMapper) {
        this.trainRepository = trainRepository;
        this.trainMapper = trainMapper;
    }

    @Override
    public List<TrainModel> getAllTrains() {
        return getTrainMapper().trainsToTrainModels(getTrainRepository().findAll());
    }

    @Override
    public TrainModel createTrain(final TrainModel trainModel) {
        final Train train = getTrainRepository().save(getTrainMapper().trainModelToTrain(trainModel));
        return getTrainMapper().trainToTrainModel(train);
    }

    @Override
    public TrainModel getTrainById(long id) {
        final Train train = getTrainRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid train Id:" + id));

        return getTrainMapper().trainToTrainModel(train);
    }

    @Override
    public TrainModel updateTrain(TrainModel trainModel) {
        getTrainById(trainModel.getId());

        Train train = getTrainRepository().save(getTrainMapper().trainModelToTrain(trainModel));

        return getTrainMapper().trainToTrainModel(train);
    }

    @Override
    public void deleteTrainById(long id) {
        getTrainRepository().deleteById(id);
    }
}
