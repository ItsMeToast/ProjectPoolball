const response = await fetch("http://localhost:8080/clients");
const body = await response.json();

function PlayersTab() {
  console.log(body)

  return (
    <table className="players-table">
      <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Team</td>
      </tr>
      <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Team</td>
      </tr>
      <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Team</td>
      </tr>
      <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Team</td>
      </tr>
    </table>
  );
}

export default PlayersTab;
