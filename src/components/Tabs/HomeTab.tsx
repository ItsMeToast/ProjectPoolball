import "./HomeTab.css";
import * as $ from "jquery";

function HomeTab() {
  return (
    <div className="home-tab h-100 w-100 justify-content-center align-items-center">
      <div className="home-display-box container flex-column text-center justify-content-center align-items-center">
        <div className="row">
          <div className="home-title col">Season 0</div>
        </div>
        <div className="row">
          <div className="col">
            <img id="home-team" className="home-team" src="../public/HSN.png" />
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

    const url: string = $(".home-team").attr("src") ?? logos[0];
    let index = logos.indexOf(url);

    console.log(index);

    if (index == logos.length - 1) {
      $(".home-team").attr("src", logos[0]);
    } else {
      $(".home-team").attr("src", logos[index + 1]);
    }
  }

  setInterval(logoSwap, 1000);
});

export default HomeTab;
