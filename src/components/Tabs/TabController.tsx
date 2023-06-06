import HomeTab from "./HomeTab";
import TeamsTab from "./TeamsTab";
import NewsTab from "./NewsTab";
import React, { useState } from "react";

function TabController() {
  const [activeTab, setActiveTab] = useState("Home");

  const handleTabSwitch = (tab: string) => {
    setActiveTab(tab);
  };

  const renderTab = () => {
    return <h1>{activeTab}</h1>;
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