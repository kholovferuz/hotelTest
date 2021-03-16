package hotelstructure.demo.Repository;

import hotelstructure.demo.Entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    boolean existsByName(String name);
}
