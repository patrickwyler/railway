package ch.teko.railway.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


/**
 * Abstract entity for station & trace which share their identification and are stored in the same table in the database
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class LinePart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

}
