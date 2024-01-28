package gov.tech.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "LUNCH_SESSION")
public class LunchSession {
    @Id
    private String sessionId;
    private boolean isActive;
    private String result;

    @OneToMany(mappedBy = "lunchSession")
    private List<Participant> participants;
}
