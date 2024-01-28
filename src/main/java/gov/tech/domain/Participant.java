package gov.tech.domain;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "PARTICIPANT")
public class Participant {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long userId;
    private String name;
    private String restaurant;
    private boolean isInvited;

    @ManyToOne
    @JoinColumn(name = "sessionId", nullable = false, updatable = false )
    private LunchSession lunchSession;

    public Participant(String name, String restaurant, LunchSession lunchSession) {
        this.name = name;
        this.restaurant = restaurant;
        this.lunchSession = lunchSession;
    }

    public Participant() {
        // default constructor
    }

}
