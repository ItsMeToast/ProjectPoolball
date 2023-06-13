import "./HomeTab.css";
import * as $ from "jquery";

function HomeTab() {
  return (
    <div className="home-tab h-100 w-100 justify-content-center align-items-center">
      <div className="home-display-box container flex-column text-center justify-content-center py-5">
        <div className="row">
          <div className="home-intro no-interact col">Welcome to Poolball GMs</div>
        </div>
        <div className="row">
          <div className="home-title no-interact col">Season 0</div>
        </div>
        <div className="row">
          <div className="col">
            <img id="home-team" className="home-team no-interact img-fluid" src="../public/HSN.png" />
          </div>
        </div>
      </div>
    </div>
  );
}

$(function () {
  function logoSwap() {
    let logos = [
      "../public/HSN.png",
      "../public/NYL.png",
      "../public/HAV.png",
      "../public/MBS.png",
      "../public/MCM.png",
      "../public/LAA.png",
      "../public/CHI.png",
      "../public/TOR.png",
    ];

    let glow = [
      "drop-shadow(0 0 20px var(--HSN-main))",
      "drop-shadow(0 0 30px var(--NYL-main))",
      "drop-shadow(0 0 20px var(--HAV-main))",
      "drop-shadow(0 0 20px var(--MBS-main))",
      "drop-shadow(0 0 20px var(--MCM-main))",
      "drop-shadow(0 0 20px var(--LAA-main))",
      "drop-shadow(0 0 20px var(--CHI-main))",
      "drop-shadow(0 0 20px var(--TOR-main))",
    ];

    const url: string = $(".home-team").attr("src") ?? logos[0];
    let index = logos.indexOf(url);

    if (index == logos.length - 1) {
      $(".home-team").attr("src", logos[0]);
      $(".home-team").css("filter", glow[0]);
    } else {
      $(".home-team").attr("src", logos[index + 1]);
      $(".home-team").css("filter", glow[index + 1]);
    }
  }

  setInterval(logoSwap, 1000);
});

export default HomeTab;
