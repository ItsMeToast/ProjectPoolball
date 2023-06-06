import React, { useState } from "react";

import HomeTab from "./HomeTab";
import TeamsTab from "./TeamsTab";
import NewsTab from "./NewsTab";
import GamesTab from "./GamesTab";
import PlayersTab from "./PlayersTab";
import MinorsTab from "./MinorsTab";
import ArchiveTab from "./ArchiveTab";
import RulesTab from "./RulesTab";

function TabController() {
  const [activeTab, setActiveTab] = useState("Home");

  const handleTabSwitch = (tab: string) => {
    setActiveTab(tab);
  };

  const renderTab = () => {
    if (activeTab == "Home") {
      return <HomeTab />;
    } else if (activeTab == "News") {
      return <NewsTab />;
    } else if (activeTab == "Teams") {
      return <TeamsTab />;
    } else if (activeTab == "Games") {
      return <GamesTab />;
    } else if (activeTab == "Players") {
      return <PlayersTab />;
    } else if (activeTab == "Minors") {
      return <MinorsTab />;
    } else if (activeTab == "Archive") {
      return <ArchiveTab />;
    } else if (activeTab == "Rules") {
      return <RulesTab />;
    } else {
      setActiveTab("Home");
      return <HomeTab />;
    }
  };

  return (
    <div className="content-box px-0">
      <nav className="sidebar col-2 h-100 container d-flex flex-column px-0">
        <div className={activeTab === "Home" ? "sidebtn active" : "sidebtn"} onClick={() => handleTabSwitch("Home")}>
          Home
        </div>
        <div className={activeTab === "News" ? "sidebtn active" : "sidebtn"} onClick={() => handleTabSwitch("News")}>
          News
        </div>
        <div className={activeTab === "Teams" ? "sidebtn active" : "sidebtn"} onClick={() => handleTabSwitch("Teams")}>
          Teams
        </div>
        <div className={activeTab === "Games" ? "sidebtn active" : "sidebtn"} onClick={() => handleTabSwitch("Games")}>
          Games
        </div>
        <div
          className={activeTab === "Players" ? "sidebtn active" : "sidebtn"}
          onClick={() => handleTabSwitch("Players")}
        >
          Players
        </div>
        <div
          className={activeTab === "Minors" ? "sidebtn active" : "sidebtn"}
          onClick={() => handleTabSwitch("Minors")}
        >
          Minors
        </div>
        <div
          className={activeTab === "Archive" ? "sidebtn active" : "sidebtn"}
          onClick={() => handleTabSwitch("Archive")}
        >
          Archive
        </div>
        <div className={activeTab === "Rules" ? "sidebtn active" : "sidebtn"} onClick={() => handleTabSwitch("Rules")}>
          Rules
        </div>
      </nav>
      <div id="tab-display" className="tab-display col px-0 mx-auto justify-content-center align-items-center">
        {renderTab()}
      </div>
    </div>
  );
}

export default TabController;
