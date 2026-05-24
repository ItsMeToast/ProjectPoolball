import "./TeamsTab.css";
import TeamCard from "../Cards/TeamCard";
import * as $ from "jquery";

function TeamsTab() {
  const teams = [
    { id: 1, code: "ATL", name: "Atlanta Albatross" },
    { id: 2, code: "BOS", name: "Boston Revolution" },
    { id: 3, code: "CGY", name: "Calgary Forge" },
    { id: 4, code: "CHI", name: "Chicago Royalty" },
    { id: 5, code: "HAV", name: "Havana Fuego" },
    { id: 6, code: "HSN", name: "Houston Stampede" },
    { id: 7, code: "LAA", name: "Los Angeles Alliance" },
    { id: 8, code: "MBS", name: "Montreal Blue Saints" },
    { id: 9, code: "MCC", name: "Mexico City Curse" },
    { id: 10, code: "MIA", name: "Miami Gators" },
    { id: 11, code: "NOS", name: "New Orleans Surge" },
    { id: 12, code: "NYR", name: "New York Rush" },
    { id: 13, code: "SEA", name: "Seattle Sharks" },
    { id: 14, code: "TOR", name: "Toronto Redcoats" },
  ];

  return (
    <div className="teams-tab h-100 w-100">
      <div className="teams-display-box container-fluid justify-content-center align-items-center px-4 m-0">
        <div className="row justify-content-center align-items-center w-100">
          {teams.map((team) => (
            <div key={team.id} className="col gx-4 gy-5 text-center justify-content-center align-self-start">
              <TeamCard code={team.code} name={team.name} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default TeamsTab;
