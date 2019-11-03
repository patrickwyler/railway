package ch.teko.railway.entities;

import ch.teko.railway.enums.Function;
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
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String firstname;

    @Column(length = 100)
    String lastname;

    @Enumerated(EnumType.STRING)
    Function function;
    
    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, orphanRemoval = false)
    @JoinColumn(name = "absence_id")
    List<Absence> absences;
}
