package ch.teko.railway.services.impl;

import ch.teko.railway.config.Config;
import ch.teko.railway.models.*;
import ch.teko.railway.services.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ch.teko.railway.enums.Function.TICKET_INSPECTOR;
import static ch.teko.railway.enums.Function.TRAIN_DRIVER;


@Slf4j
@Service
@Getter(value = AccessLevel.PROTECTED)
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanServiceImpl implements PlanService {

    static final int TICKS_PER_HOUR = 60; // 1h has 60 ticks, one every minute
    static final int TIME_TABLE_HOURS = 29; // 24h + 4h for the last departure of the day at 00:00
    static final int MAX_TICKS = TICKS_PER_HOUR * TIME_TABLE_HOURS; // calculate total ticks => hours * ticks per hour
    static final Date TODAY = new Date();
    static final int AMOUNT_OF_CARRIAGES_PER_TRAIN = 3;

    Config config;
    List<LinePathModel> paths = new ArrayList<>();

    LineService lineService;
    TickerService tickerService;
    CarriageService carriageService;
    EmployeeService employeeService;

    TimeTableModel timeTableModel;

    @Autowired
    public PlanServiceImpl(LineService lineService, TickerService tickerService, CarriageService carriageService, EmployeeService employeeService, Config config) {
        this.lineService = lineService;
        this.tickerService = tickerService;
        this.carriageService = carriageService;
        this.employeeService = employeeService;
        this.config = config;
    }

    @Override
    public void reset() {
        createTimeTable();
    }

    @Override
    public TimeTableModel getTimeTable() {
        loadTimeTableNeeded();

        return getTimeTableModel();
    }

    @Override
    public ResourcePlanModel getResourcesPlan() {
        loadTimeTableNeeded();

        return getResourcePlanModel();
    }

    @Override
    public WorkScheduleModel getWorkSchedule() {
        loadTimeTableNeeded();

        return getWorkScheduleModel();
    }

    /**
     * Get work schedule out of needed employees for today
     *
     * @return Work schedule
     */
    private WorkScheduleModel getWorkScheduleModel() {
        final HashMap<String, List<EmployeeModel>> trainCrew = new HashMap<>();

        // get all available employees for today by function
        final List<EmployeeModel> trainDrivers = getEmployeeService().getAvailableEmployeesWithFunctionOnDate(TRAIN_DRIVER, TODAY);
        final List<EmployeeModel> ticketInspectors = getEmployeeService().getAvailableEmployeesWithFunctionOnDate(TICKET_INSPECTOR, TODAY);

        // calculate needed employees
        final int amountOfTrains = getTickerService().getUsedTrainModels().size();
        final int neededAmountOfEmployees = amountOfTrains * 2;

        // break down employees in teams of two persons on used trains for the current timetable
        AtomicInteger i = new AtomicInteger();
        getTickerService().getUsedTrainModels().forEach(trainModel -> {
            final List<EmployeeModel> crew = new ArrayList<>();
            crew.add(trainDrivers.get(i.get())); // get train driver
            crew.add(ticketInspectors.get(i.get())); // get ticket inspector

            // add crew
            trainCrew.put(trainModel.getName(), crew);

            // up count employee cursor
            i.set(i.get() + 1);
        });

        // build work schedule
        return WorkScheduleModel.builder()
                .amountOfEmployees(neededAmountOfEmployees)
                .amountOfTrains(amountOfTrains)
                .trainCrews(trainCrew)
                .build();
    }

    /**
     * Get resource plan for the needed trains/carriages of the current time table
     *
     * @return resource plan
     */
    private ResourcePlanModel getResourcePlanModel() {
        final HashMap<String, List<String>> trainsWithCarriages = new HashMap<>();
        final List<String> carriages = new ArrayList<>();
        final int amountOfTrains = getTickerService().getUsedTrainModels().size();
        final int neededAmountOfCarriages = amountOfTrains * AMOUNT_OF_CARRIAGES_PER_TRAIN; // each train should have 3 carriages

        // get available carriages
        getCarriageService().getAllCarriages().forEach(carriageModel -> carriages.add(carriageModel.getName()));

        // calculate
        AtomicInteger i = new AtomicInteger();
        getTickerService().getUsedTrainModels().forEach(trainModel -> {
            trainsWithCarriages.put(trainModel.getName(), carriages.subList(i.get(), i.get() + AMOUNT_OF_CARRIAGES_PER_TRAIN));
            i.set(i.get() + AMOUNT_OF_CARRIAGES_PER_TRAIN);
        });

        // build resource plan
        return ResourcePlanModel.builder()
                .amountOfTrains(getTickerService().getUsedTrainModels().size())
                .amountOfCarriages(neededAmountOfCarriages)
                .trainsWithCarriages(trainsWithCarriages)
                .build();
    }

    private synchronized void createTimeTable() {
        // Prepare ticker
        initTicker();

        // Create line paths for our trains
        createLinePaths();

        // Move trains on lines and generate timetable out of the result
        setTimeTableModel(createTimeTableData());
    }

    private void initTicker() {
        // init ticker service
        getTickerService().initalize();
    }

    private void lunchNewTrains() {
        List<LinePathModel> trainPathPositions = createLinePathsForTicks();

        for (LinePathModel linePath : trainPathPositions) {
            StationModel station = getTickerService().get(linePath.getStartStation().getId());

            try {
                // use train from start station
                final TrainModel train = station.getFirstTrainFromRepo();
                // remove from repository of start station
                station.removeFirstTrainFromRepo();

                train.setLineNumber(linePath.getLine().getLineNumber());
                train.setPath(linePath.getPaths());

                getTickerService().add(train);
            } catch (Exception e) {
                log.warn("No available train on station [" + station.getName() + "], train [" + getTickerService().getTime() + "] cancelled!");
            }
        }
    }

    /**
     * Create line path prepared for ticks in minutes
     *
     * @return line path in minutes
     */
    private List<LinePathModel> createLinePathsForTicks() {
        List<LinePathModel> trainPathPositions = new ArrayList<>();

        // create small steps for each tick
        for (LinePathModel linePath : paths) {
            List<LinePartModel> trainPathPos = new ArrayList<>();

            // alternately Station and then trace.. for example: station-trace-station-trace
            for (LinePartModel linePart : linePath.getPaths())
                if (linePart instanceof StationModel) {
                    for (int i = 0; i < ((StationModel) linePart).getStopTimeInMinutes(); i++) {
                        // add station
                        trainPathPos.add(linePart);
                    }
                } else {
                    for (int i = 0; i < ((TraceModel) linePart).getDistanceInMinutes(); i++) {
                        // add trace
                        trainPathPos.add(linePart);
                    }
                }

            // build line path
            trainPathPositions.add(LinePathModel.builder()
                    .line(linePath.getLine())
                    .paths(trainPathPos)
                    .build());
        }

        return trainPathPositions;
    }

    /**
     * Create paths for all lines
     */
    private void createLinePaths() {
        final List<LineModel> lines = getLineService().getAllLines();

        for (LineModel lineModel : lines) {
            StationModel rootStation = (StationModel) lineModel.getLineParts()
                    .stream()
                    .filter(part -> part instanceof StationModel)
                    .filter(part -> ((StationModel) part).isRootStation())
                    .findFirst().get();

            // add paths for all directions
            addToPaths(lineModel);
            addToPathsReverse(lineModel);
            addToPathsRoot1(lineModel, rootStation);
            addToPathsRoot2(lineModel, rootStation);
        }
    }

    /**
     * Create time table
     *
     * @return time table
     */
    private TimeTableModel createTimeTableData() {
        final TimeTableModel timeTableModel = new TimeTableModel();

        // Caluculate time table by going trough the whole day in steps of one minute. On every minute check if new
        // trains should be lunched or not. Driving trains will be moved on their path until they reach their final
        // destination. If a train reaches a station or is departing from a station this event will be logged and
        // used to generate the time table.
        for (int i = 0; i < MAX_TICKS; i++) {

            // check if lunch of new trains is needed
            if (checkIfLunchNewTrainsIsNeeded()) {
                lunchNewTrains();
            }

            for (TrainModel train : getTickerService().getTrainModels()) {
                // check for position, on arrival or departure log the event
                final PositionModel position = train.getPositionOnLine();

                if (position != null) {
                    // log event
                    timeTableModel.addElement(train.getLineNumber(), train, position, getTickerService().getTime());
                }
            }

            // remove all stopped trains
            getTickerService().removeStoppedTrains();

            // up count ticker and move all trains to new position on their path
            getTickerService().tick();
        }

        log.info(timeTableModel.getTimeTable().toString());

        return timeTableModel;
    }

    private boolean checkIfLunchNewTrainsIsNeeded() {
        return getConfig().getLunchTimes().contains(getTickerService().getTime());
    }

    private void addToPaths(final LineModel lineModel) {
        final List<LinePartModel> path = new ArrayList<>(lineModel.getLineParts());

        createPathToDrive(lineModel, path);
    }

    private void addToPathsReverse(final LineModel lineModel) {
        final List<LinePartModel> path = new ArrayList<>(lineModel.getLineParts());
        Collections.reverse(path);

        createPathToDrive(lineModel, path);
    }

    private void addToPathsRoot1(final LineModel lineModel, final StationModel beforeStation) {
        final List<LinePartModel> path = new ArrayList<>(lineModel.getLineParts());

        int index = path.indexOf(beforeStation);
        final List<LinePartModel> pathBefore = path.subList(0, index + 1);
        Collections.reverse(pathBefore);

        createPathToDriveReverse(lineModel, pathBefore);
    }

    private void createPathToDrive(LineModel lineModel, List<LinePartModel> path) {
        List<LinePartModel> linePath = new ArrayList<>(path);
        final List<LinePartModel> pathReverse = path.subList(0, lineModel.getLineParts().size() - 1);
        Collections.reverse(pathReverse);
        linePath.addAll(pathReverse);

        paths.add(LinePathModel.builder()
                .line(lineModel)
                .paths(linePath)
                .build());
    }

    private void createPathToDriveReverse(LineModel lineModel, List<LinePartModel> pathBefore) {
        List<LinePartModel> linePath = new ArrayList<>(pathBefore);

        final List<LinePartModel> pathBeforeReverse = pathBefore.subList(0, pathBefore.size() - 1);
        Collections.reverse(pathBeforeReverse);
        linePath.addAll(pathBeforeReverse);
        paths.add(LinePathModel.builder()
                .line(lineModel)
                .paths(linePath)
                .build());
    }

    private void addToPathsRoot2(final LineModel lineModel, final StationModel beforeStation) {
        final List<LinePartModel> path = new ArrayList<>(lineModel.getLineParts());

        int index = path.indexOf(beforeStation);
        final List<LinePartModel> pathAfter = path.subList(index, path.size());

        createPathToDriveReverse(lineModel, pathAfter);
    }

    /**
     * If there is no timetable => generate timetable
     * Otherwise return the already generated time table
     */
    private void loadTimeTableNeeded() {
        if (getTimeTableModel() == null || getTimeTableModel().getTimeTable().isEmpty()) {
            createTimeTable();
        }
    }
}