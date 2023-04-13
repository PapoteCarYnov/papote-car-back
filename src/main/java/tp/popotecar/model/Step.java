package tp.popotecar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "step", schema = "public")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "time")
    @NotNull
    private LocalTime time;

    @OneToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @ManyToOne
    @JoinColumn(name="ride_id", referencedColumnName = "id")
    private Ride ride;

    @OneToMany(mappedBy="startStep", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Price> prices;
}
