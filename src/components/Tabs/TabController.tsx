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
    <div className="tab-controller p-0 justify-content-center">
      <div className="tab-nav w-100 text-center">
        <div className="tab-nav-group">
          <button
            type="button"
            className={activeTab === "Home" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Home")}
          >
            Home
          </button>
          <button
            type="button"
            className={activeTab === "News" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("News")}
          >
            News
          </button>
          <button
            type="button"
            className={activeTab === "Teams" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Teams")}
          >
            Teams
          </button>
          <button
            type="button"
            className={activeTab === "Games" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Games")}
          >
            Games
          </button>
          <button
            type="button"
            className={activeTab === "Players" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Players")}
          >
            Players
          </button>
          <button
            type="button"
            className={activeTab === "Minors" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Minors")}
          >
            Minors
          </button>
          <button
            type="button"
            className={activeTab === "Archive" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Archive")}
          >
            Archive
          </button>
          <button
            type="button"
            className={activeTab === "Rules" ? "tab-nav-btn active" : "tab-nav-btn"}
            onClick={() => handleTabSwitch("Rules")}
          >
            Rules
          </button>
        </div>
      </div>

      {/* Render Tab Content */}
      {renderTab()}
    </div>
  );
}

export default TabController;
