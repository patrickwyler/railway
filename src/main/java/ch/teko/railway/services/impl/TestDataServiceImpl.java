package ch.teko.railway.services.impl;

import ch.teko.railway.enums.Function;
import ch.teko.railway.models.*;
import ch.teko.railway.services.*;
import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service for generating our test data.
 */
@Slf4j
@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDataServiceImpl implements TestDataService {

    static final String START_STATION_NAME = "Jegenstorf";
    static final String MIDDLE_STATION_NAME_1 = "Zollikofen";
    static final String ROOT_STATION_NAME = "Bern";
    static final String MIDDLE_STATION_NAME_2 = "Ostermundigen";
    static final String END_STATION_NAME = "Thun";

    StationService stationService;
    TrainService trainService;
    TraceService traceService;
    LineService lineService;
    EmployeeService employeeService;
    CarriageService carriageService;

    Faker faker = new Faker();

    @Autowired
    public TestDataServiceImpl(StationService stationService, TrainService trainService, TraceService traceService, LineService lineService, EmployeeService employeeService, CarriageService carriageService) {

        this.stationService = stationService;
        this.trainService = trainService;
        this.traceService = traceService;
        this.lineService = lineService;
        this.employeeService = employeeService;
        this.carriageService = carriageService;
    }

    /**
     * This method creates all needed testdata
     */
    @Override
    public void createTestData() {
        createLine();
        createCarriages();
        createEmployees();
    }

    /**
     * Create carriages
     */
    private void createCarriages() {
        for (int i = 0; i < 100; i++) {
            getCarriageService().createCarriage(CarriageModel.builder()
                    .name("W" + i)
                    .amountOfSeats(80)
                    .available(true)
                    .commissioningDate(new Date())
                    .lastAuditDate(new Date())
                    .nextAuditDate(new Date())
                    .typeDesignation(getFaker().code().ean8())
                    .build());
        }
    }

    /**
     * Create employees
     */
    private void createEmployees() {
        // create employees without absences
        for (int i = 0; i < 100; i++) {
            createEmployee(Function.TRAIN_DRIVER, false);
            createEmployee(Function.TICKET_INSPECTOR, false);
        }

        // create employees with absences
        for (int i = 0; i < 15; i++) {
            createEmployee(Function.TRAIN_DRIVER, true);
            createEmployee(Function.TICKET_INSPECTOR, true);
        }
    }

    /**
     * Create employee
     *
     * @param function function
     * @param hasAbsence w/ absence or w/o absence
     */
    private void createEmployee(Function function, boolean hasAbsence) {
        EmployeeModel employeeModel = EmployeeModel.builder()
                .firstname(getFaker().name().firstName())
                .lastname(getFaker().name().lastName())
                .function(function)
                .absences(hasAbsence ? Collections.singletonList(createAbsence()) : new ArrayList<>())
                .build();
        getEmployeeService().createEmployee(employeeModel);
    }

    /**
     * Create absence
     *
     * @return Absence
     */
    private AbsenceModel createAbsence() {
        return getEmployeeService().createAbsence(AbsenceModel.builder().time(new Date()).build());
    }

    /**
     * Create line
     */
    private void createLine() {
        // create stations

        StationModel startStation = StationModel.builder()
                .name(START_STATION_NAME)
                .rootStation(false)
                .stopTimeInMinutes(3)
                .repository(createTrains("Regio Express", 0, 15))
                .build();
        startStation = getStationService().createStation(startStation);

        StationModel middleStation1 = StationModel.builder()
                .name(MIDDLE_STATION_NAME_1)
                .rootStation(false)
                .stopTimeInMinutes(3)
                .build();
        middleStation1 = getStationService().createStation(middleStation1);

        StationModel rootStation = StationModel.builder()
                .name(ROOT_STATION_NAME)
                .rootStation(true)
                .stopTimeInMinutes(5)
                .repository(createTrains("Regio Express", 100, 30))
                .build();
        rootStation = getStationService().createStation(rootStation);

        StationModel middleStation2 = StationModel.builder()
                .name(MIDDLE_STATION_NAME_2)
                .rootStation(false)
                .stopTimeInMinutes(3)
                .build();
        middleStation2 = getStationService().createStation(middleStation2);

        StationModel endStation = StationModel.builder()
                .name(END_STATION_NAME)
                .rootStation(false)
                .stopTimeInMinutes(3)
                .repository(createTrains("Regio Express", 200, 15))
                .build();
        endStation = getStationService().createStation(endStation);

        // create traces

        TraceModel trace1 = TraceModel.builder()
                .distanceInMinutes(6)
                .name("Speedtrace")
                .build();
        trace1 = getTraceService().createTrace(trace1);

        TraceModel trace2 = TraceModel.builder()
                .distanceInMinutes(6)
                .name("Speedtrace")
                .build();
        trace2 = getTraceService().createTrace(trace2);

        TraceModel trace3 = TraceModel.builder()
                .distanceInMinutes(4)
                .name("Speedtrace")
                .build();
        trace3 = getTraceService().createTrace(trace3);

        TraceModel trace4 = TraceModel.builder()
                .distanceInMinutes(5)
                .name("Speedtrace")
                .build();
        trace4 = getTraceService().createTrace(trace4);

        // add  parts
        List<LinePartModel> parts = new ArrayList<>();
        parts.add(startStation);
        parts.add(trace1);
        parts.add(middleStation1);
        parts.add(trace2);
        parts.add(rootStation);
        parts.add(trace3);
        parts.add(middleStation2);
        parts.add(trace4);
        parts.add(endStation);

        // build line
        LineModel line1 = LineModel.builder()
                .lineNumber(1)
                .name("Line 1")
                .lineParts(parts)
                .build();

        lineService.createLine(line1);
    }

    /**
     * Create trains
     *
     * @param name name
     * @param startNumberFrom start number from xx
     * @param amount amount of trains which should be generated
     * @return List of trains
     */
    private List<TrainModel> createTrains(String name, int startNumberFrom, int amount) {
        List<TrainModel> trains = new ArrayList<>();

        for (int i = startNumberFrom; i < startNumberFrom + amount; i++) {
            trains.add(getTrainService().createTrain(TrainModel.builder()
                    .name(name + " " + i)
                    .amountOfSeats(2)
                    .available(true)
                    .commissioningDate(new Date())
                    .lastAuditDate(new Date())
                    .nextAuditDate(new Date())
                    .typeDesignation(getFaker().code().ean8())
                    .build()));
        }

        return trains;
    }
}