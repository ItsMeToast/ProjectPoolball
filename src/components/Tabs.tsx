import React from "react";
import ReactDOM from "react-dom/client";
import TabController from "./Tabs/TabController";

const root = ReactDOM.createRoot(document.getElementById("tabs") as HTMLElement);
root.render(<TabController />);
