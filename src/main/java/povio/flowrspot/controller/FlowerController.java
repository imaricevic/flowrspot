package povio.flowrspot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import povio.flowrspot.model.Flower;
import povio.flowrspot.repository.FlowerRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flowers")
public class FlowerController {

    @Autowired
    private FlowerRepository flowerRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveAll() {
        List<Flower> flowers = flowerRepository.findAll();
        return ResponseEntity.ok(flowers);
    }

    @RequestMapping(value = "/{flowerId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable Long flowerId) {
        Optional<Flower> flower = flowerRepository.findById(flowerId);
        if (!flower.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flower.get());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Flower flower) {
        flower.setId(null);
        flowerRepository.save(flower);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{flowerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Flower flower, @PathVariable Long flowerId) {
        Optional<Flower> newFlower = flowerRepository.findById(flowerId);
        if (!newFlower.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        flower.setId(flowerId);
        flowerRepository.save(flower);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{flowerId}/listOfSightings", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveSightings(@PathVariable Long flowerId) {
        Optional<Flower> flower = flowerRepository.findById(flowerId);
        if (!flower.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(flower.get().getListOfSightings());
    }

}
