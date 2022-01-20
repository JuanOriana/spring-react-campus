import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";

const theme = {
  cyanDarkest: "#176961",
  cyanDark: "#2EC4B6",
  cyanLight: "#CBF3F0",
  successBack: "#45FF9F",
  successFront: "#39614C",
};

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);

// Servicios y modelos
// Type definition typescript
// endpoint -> servicio + modelos
