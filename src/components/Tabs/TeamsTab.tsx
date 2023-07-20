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
      <div className="teams-display-box container-fluid justify-content-center align-items-center px-4 m-0">
        <div className="row justify-content-center align-items-center w-100">
          {teams.map((team) => (
            <div className="col g-4 text-center justify-content-center align-self-start">
              <TeamCard code={team.code} name={team.name} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default TeamsTab;
