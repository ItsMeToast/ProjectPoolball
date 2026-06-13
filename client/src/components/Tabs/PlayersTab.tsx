import { ReactElement, JSXElementConstructor, ReactNode, ReactPortal } from "react";

const response = await fetch("http://localhost:8080/players");
const body = await response.json();

import type { Player } from "@types";

function PlayersTab() {
  // console.log(body)

  return (
    <table className="players-table" align="center">
      <tbody>
        {body.map((player: Player) => (
          <tr key={player.id}>
            <td>{player.id} </td>
            <td>{player.firstName} </td>
            <td>{player.lastName}</td>
            <td>{player.stats.injury}%</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default PlayersTab;
