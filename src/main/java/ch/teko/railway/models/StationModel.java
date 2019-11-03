package ch.teko.railway.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationModel extends LinePartModel {

    Long id;

    String name;

    boolean rootStation;

    int stopTimeInMinutes;

    String errorMessage;

    List<TrainModel> repository;

    public void addTrain(final TrainModel trainModel) {
        log.info("Put train back to repo: " + trainModel.getName() + " station: " + name + " : "+ this.hashCode());
        repository.add(0, trainModel);
    }

    public void removeFirstTrainFromRepo() {
        getRepository().remove(0);
    }

    public TrainModel getFirstTrainFromRepo() {
        return getRepository().get(0);
    }

}
