import "./TeamsTab.css";
import TeamCard from "../Cards/TeamCard";
import * as $ from "jquery";

function TeamsTab() {
  const teams = [
    { code: "ATL", name: "Atlanta Albatross" },
    { code: "BOS", name: "Boston Revolution" },
    { code: "CGY", name: "Calgary Forge" },
    { code: "CHI", name: "Chicago Royalty" },
    { code: "HAV", name: "Havana Fuego" },
    { code: "HSN", name: "Houston Stampede" },
    { code: "LAA", name: "Los Angeles Alliance" },
    { code: "MBS", name: "Montreal Blue Saints" },
    { code: "MCC", name: "Mexico City Curse" },
    { code: "MIA", name: "Miami Gators" },
    { code: "NOS", name: "New Orleans Surge" },
    { code: "NYR", name: "New York Rush" },
    { code: "SEA", name: "Seattle Sharks" },
    { code: "TOR", name: "Toronto Redcoats" },
  ];

  return (
    <div className="teams-tab h-100 w-100 justify-content-center align-items-center">
      <div className="teams-display-box container justify-content-center align-items-center p-0">
        <div className="row row-cols-auto justify-content-center align-items-center">
          {teams.map((team) => (
            <div className="col w-10 h-10 p-0">
              <TeamCard code={team.code} name={team.name} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default TeamsTab;
