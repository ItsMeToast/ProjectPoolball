package net.poolballgms.poolballweb;

import gamesimulator.GameRecord;
import gamesimulator.GameTester;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playertypes.Nationality;
import playertypes.Player;
import playertypes.Playstyle;
import playertypes.Trait;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@SpringBootApplication
@RestController
@CrossOrigin
public class PoolballWebApplication {
    private final PlayerRepository playerRepository;
    private final PlayerDataRepository playerDataRepository;

    public PoolballWebApplication(PlayerRepository pRep, PlayerDataRepository playerDataRepository) {
        this.playerDataRepository = playerDataRepository;
        this.playerRepository = pRep;
    }

    @GetMapping("/clients")
    public List<PlayerJPA> getRecords() {
        return playerRepository.findAll();
    }

    @GetMapping("/{id}")
    public PlayerJPA getClient(@PathVariable Long id) {
        return playerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    // Test this using Postman by sending any POST request to /players
    @PostMapping("/players")
    public ResponseEntity createPlayer() throws URISyntaxException {
        Player newPlayer = new Player(Trait.SUPERSTAR);
        PlayerJPA pJPA = new PlayerJPA(newPlayer.getFirstName(), newPlayer.getLastName(), newPlayer.getNationality());

        playerRepository.save(pJPA);

        PlayerData player = new PlayerData(newPlayer, pJPA.getPlayerID(), 3);

        PlayerData savedClient = playerDataRepository.save(player);
        return ResponseEntity.created(new URI("/players/" + savedClient.getPlayerID())).body(savedClient);
    }

    // Test this using Postman by sending any POST request to /new
    @PostMapping("/new")
    public ResponseEntity newPlayer() throws URISyntaxException {
        PlayerJPA pJPA = new PlayerJPA("Arizona", "Zarinsk", Nationality.random());
        PlayerJPA savedClient = playerRepository.save(pJPA);
        return ResponseEntity.created(new URI("/test/" + savedClient.getPlayerID())).body(savedClient);
    }

    // Test this using Postman by sending any POST request to /age
    @PostMapping("/age")
    public List<ResponseEntity> agePlayers() throws URISyntaxException {
        List<PlayerData> allPlayers = playerDataRepository.findAll();
        int maxSeason = Collections.max(allPlayers, new Comparator<PlayerData>(){
            @Override
            public int compare(PlayerData o1, PlayerData o2) {
                return o1.getSeason() - o2.getSeason();
            }
        }).getSeason();

        List<PlayerData> curPlayers = playerDataRepository.findBySeason(maxSeason);

        List<ResponseEntity> response = new ArrayList<>();

        for (PlayerData pd : curPlayers) {
            PlayerData agedPlayerData = new PlayerData();
            agedPlayerData.setPlayerID(pd.getPlayerID());
            agedPlayerData.setSeason(pd.getSeason()+1);
            agedPlayerData.setAge(pd.getAge()+1);
            agedPlayerData.setFirstName(pd.getFirstName());
            agedPlayerData.setLastName(pd.getLastName());
            agedPlayerData.setPlaystyle(pd.getPlaystyle());
            agedPlayerData.setPotential(pd.getPotential());
            agedPlayerData.setTrait(pd.getTrait());
            agedPlayerData.setStats(pd.getStats());

            PlayerData savedClient = playerDataRepository.save(agedPlayerData);
            response.add(ResponseEntity.created(new URI("/players/" + savedClient.getPlayerID())).body(savedClient));
        }

        return response;
    }

    @GetMapping("/players")
    public List<PlayerData> getPlayers() {
        PlayerData pd = playerDataRepository.findById(new PlayerDataID(1L, 10)).orElseThrow(RuntimeException::new);

        System.out.println(pd.getPlaystyle().equals(Playstyle.FINISHER)); // Shows that ENUM is retrieved correctly

        System.out.println(pd.getStats());

        return playerDataRepository.findAll();
    }

    @PostMapping("/clients")
    public ResponseEntity createClient(@RequestBody PlayerJPA client) throws URISyntaxException {
        PlayerJPA savedClient = playerRepository.save(client);
        return ResponseEntity.created(new URI("/clients/" + savedClient.getPlayerID())).body(savedClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateClient(@PathVariable Long id, @RequestBody PlayerJPA client) {
        PlayerJPA currentClient = playerRepository.findById(id).orElseThrow(RuntimeException::new);
        currentClient.setFirstName(client.getFirstName());
        currentClient.setLastName(client.getLastName());
        currentClient.setNationality(client.getNationality());
        currentClient = playerRepository.save(client);

        return ResponseEntity.ok(currentClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClient(@PathVariable Long id) {
        playerRepository.deleteById(id);
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

