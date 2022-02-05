import React from "react";
import { LdsRing } from "./styles";

function Spinner({ multiplier = 1 }) {
  return (
    <LdsRing sizeMultiplier={multiplier}>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </LdsRing>
  );
}

export default Spinner;
