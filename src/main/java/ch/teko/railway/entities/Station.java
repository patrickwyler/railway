package ch.teko.railway.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Station extends LinePart {

    @Column(length = 100)
    String name;

    boolean rootStation;

    @Column(length = 100)
    int stopTimeInMinutes;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, orphanRemoval = false)
    @JoinColumn(name = "repository_id")
    List<Train> repository;
}

