package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Carriage;
import ch.teko.railway.mappers.CarriageMapper;
import ch.teko.railway.models.CarriageModel;
import ch.teko.railway.repositories.CarriageRepository;
import ch.teko.railway.services.CarriageService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarriageServiceImpl implements CarriageService {

    CarriageRepository carriageRepository;
    CarriageMapper carriageMapper;

    @Autowired
    public CarriageServiceImpl(CarriageRepository carriageRepository, CarriageMapper carriageMapper) {
        this.carriageRepository = carriageRepository;
        this.carriageMapper = carriageMapper;
    }

    @Override
    public List<CarriageModel> getAllCarriages() {
        return getCarriageMapper().carriagesToCarriageModels(getCarriageRepository().findAll());
    }

    @Override
    public CarriageModel createCarriage(CarriageModel carriageModel) {
        final Carriage carriage = getCarriageRepository().save(getCarriageMapper().carriageModelToCarriage(carriageModel));
        return getCarriageMapper().carriageToCarriageModel(carriage);
    }
    

    @Override
    public CarriageModel getCarriageById(long id) {
        final Carriage carriage = getCarriageRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid carriage Id:" + id));

        return getCarriageMapper().carriageToCarriageModel(carriage);
    }

    @Override
    public CarriageModel updateCarriage(CarriageModel carriageModel) {
        getCarriageById(carriageModel.getId());

        Carriage carriage = getCarriageRepository().save(getCarriageMapper().carriageModelToCarriage(carriageModel));

        return getCarriageMapper().carriageToCarriageModel(carriage);
    }

    @Override
    public void deleteCarriageById(long id) {
        getCarriageRepository().deleteById(id);
    }
}
