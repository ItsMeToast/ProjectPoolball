package net.poolballgms.poolballweb;

import gamesimulator.GameRecord;
import gamesimulator.GameTester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@SpringBootApplication
@RestController
@CrossOrigin
public class PoolballWebApplication {

    private final RecordRepository recordRepository;

    public PoolballWebApplication(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/clients")
    public List<PlayerRecordJPA> getRecords() {
        return recordRepository.findAll();
    }

    @GetMapping("/{id}")
    public PlayerRecordJPA getClient(@PathVariable Long id) {
        return recordRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping("/clients")
    public ResponseEntity createClient(@RequestBody PlayerRecordJPA client) throws URISyntaxException {
        PlayerRecordJPA savedClient = recordRepository.save(client);
        return ResponseEntity.created(new URI("/clients/" + savedClient.getId())).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody PlayerRecordJPA client) {
        PlayerRecordJPA currentClient = recordRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setName(client.getName());
        currentClient.setGoals(client.getGoals());
        currentClient = recordRepository.save(client);

        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        recordRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    static void main(String[] args) {
        SpringApplication.run(PoolballWebApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return String.format("<h1>Hello %s!</h1>", name);
    }

    @GetMapping("/simulate")
    public GameRecord simulate(@RequestParam(name = "ageNum", defaultValue = "3") int ageNum) {
        GameRecord rec = GameTester.testAgedGame(ageNum);
        return rec;
    }
}

