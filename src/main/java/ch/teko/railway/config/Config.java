package ch.teko.railway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for accessing configurations
 */
@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "railway")
public class Config {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private List<String> times = new ArrayList<>();

    public void setTimes(final List<String> times) {
        this.times = times;
    }

    public List<String> getTimes() {
        return times;
    }

    /**
     * Get lunch times for the timetable
     *
     * @return List of local times when the station should lunch a train
     */
    public List<LocalTime> getLunchTimes() {
        return times.stream()
                .map(date -> LocalTime.parse(date, formatter))
                .collect(Collectors.toList());
    }
}