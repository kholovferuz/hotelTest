package hotelstructure.demo.Controller;

import hotelstructure.demo.Entity.Hotel;
import hotelstructure.demo.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    // READ all hotels
    @GetMapping
    public List<Hotel> getAllHotels() {
        List<Hotel> hotelList = hotelRepository.findAll();
        return hotelList;
    }

    // CREATE new Hotel
    @PostMapping
    public String addHotel(@RequestBody Hotel hotel) {
        boolean existsByName = hotelRepository.existsByName(hotel.getName());
        Hotel newHotel=new Hotel();
        if (!existsByName) {
            newHotel.setName(hotel.getName());
        } else {
            return "This hotel already exists";
        }

        hotelRepository.save(newHotel);
        return "Hotel added";
    }

    //UPDATE hotel by id
    @PutMapping("/{id}")
    public String updateSubject(@PathVariable Integer id, @RequestBody Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        boolean update = false;
        if (optionalHotel.isPresent()) {
            Hotel editedHotel = optionalHotel.get();
            boolean existsByName = hotelRepository.existsByName(hotel.getName());
            if (!existsByName) {
                editedHotel.setName(hotel.getName());
            } else {
                return "This hotel already exists";
            }

            hotelRepository.save(editedHotel);
            update = true;
        }
        return update ? "Hotel updated" : "Hotel with this id is not found";
    }

    // DELETE hotel by id
    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Integer id) {
        List<Hotel> hotelList = hotelRepository.findAll();
        boolean delete = false;
        for (Hotel hotel : hotelList) {
            if (hotel.getId() == id) {
                hotelRepository.deleteById(id);
                delete = true;
                break;
            }
        }
        return delete ? "Hotel deleted" : "Hotel with this id is not found";
    }
}
