package tp.popotecar.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "price", schema = "public")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    @NotNull
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_step_id")
    private Step startStep;

    @ManyToOne
    @JoinColumn(name = "end_step_id", referencedColumnName = "id")
    private Step endStep;
}
