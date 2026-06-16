import { ReactElement, JSXElementConstructor, ReactNode, ReactPortal } from "react";

const response = await fetch("http://localhost:8080/players");
const body = await response.json();

import type { Player } from "@types";

function PlayersTab() {
  // console.log(body)

  return (
    <table className="players-table table w-25 text-center table-bordered p1 table-striped table-hover" align="center">
      <tbody>
        <tr>
          <td>PLAYER ID</td>
          <td>First Name</td>
          <td>Last Name</td>
          <td>Injury</td>
        </tr>
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
