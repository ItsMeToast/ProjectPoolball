package net.poolballgms.poolballweb;

import gamesimulator.GameRecord;
import gamesimulator.GameTester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Trait;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@SpringBootApplication
@RestController
@CrossOrigin
public class PoolballWebApplication {
    private final RecordRepository recordRepository;
    private final PlayerDataRepository playerDataRepository;

    public PoolballWebApplication(RecordRepository recordRepository, PlayerDataRepository playerDataRepository) {
        this.recordRepository = recordRepository;
        this.playerDataRepository = playerDataRepository;
    }

    @GetMapping("/clients")
    public List<PlayerRecordJPA> getRecords() {
        return recordRepository.findAll();
    }

    @GetMapping("/{id}")
    public PlayerRecordJPA getClient(@PathVariable Long id) {
        return recordRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    // Test this using Postman by sending any POST request to /players
    @PostMapping("/players")
    public ResponseEntity createPlayer() throws URISyntaxException {
        PlayerData player = new PlayerData();

        Player newPlayer = new Player(Trait.SUPERSTAR);

        player.updateFromPlayer(newPlayer);
        player.setSeason(10);

        PlayerData savedClient = playerDataRepository.save(player);
        return ResponseEntity.created(new URI("/players/" + savedClient.getId())).body(savedClient);
    }

    @GetMapping("/players")
    public List<PlayerData> getPlayers() {
        PlayerData pd = playerDataRepository.findById(1L).orElseThrow(RuntimeException::new);

        System.out.println(pd.getPlaystyle().equals(Playstyle.FINISHER)); // Shows that ENUM is retrieved correctly

        System.out.println(pd.getStats());

        return playerDataRepository.findAll();
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
        return GameTester.testAgedGame(ageNum);
    }
}

