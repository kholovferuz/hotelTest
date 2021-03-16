package hotelstructure.demo.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private Integer floor;
    @Column(nullable = false)
    private Double size;
    @ManyToOne(optional = false)
    private Hotel hotel;
}
