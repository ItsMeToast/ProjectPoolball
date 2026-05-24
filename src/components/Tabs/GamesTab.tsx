import "./GamesTab.css";
import React, { useState } from "react";
import * as $ from "jquery";


async function handleSimulate() {
  var game = await fetch("http://localhost:8080/simulate").then(game => game.json());
  console.log(game)
  var results = document.getElementById("results") as HTMLTextAreaElement
  results.innerText=game.homeTeam + " " + game.homeScore + " : "  + game.awayScore + " " + game.awayTeam;

  
  var longresults = document.getElementById("long") as HTMLTextAreaElement
  longresults.innerText = ""

  for (let player of game.playerRecords) {
    longresults.innerText += (
      "\r\n" + player.firstName + " " + player.lastName + ": " 
      + player.shots + " shots, "
      + player.goals + " goals, "
      + player.assists + " assists, "
      + player.blocks + " blocks, "
      + player.steals + " steals, "
    );
  }

}

function GamesTab() {
  return (
    <div className="games-tab h-100 w-100 justify-content-center align-items-center">
      <center>
        <button id="simbutton" onClick={() => handleSimulate()}>Simulate Game</button>
        <div id="results"></div>
        <div id="long"></div>
      </center>
    </div>
  );
}

export default GamesTab;
