import "./HomeTab.css";
import * as $ from "jquery";

function HomeTab() {
  return (
    <div className="home-tab h-100 w-100 justify-content-center align-items-center">
      <div className="home-display-box container flex-column text-center justify-content-center">
        <div className="row">
          <div className="home-intro no-interact col">Welcome to Poolball GMs</div>
        </div>
        <div className="row">
          <div className="home-title no-interact col">Season 0</div>
        </div>
        <div className="row">
          <div className="col">
            <img id="home-team" className="home-team no-interact" src="../public/HSN.png" />
          </div>
        </div>
      </div>
    </div>
  );
}

export default HomeTab;
