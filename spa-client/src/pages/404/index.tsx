import { ErrorTitle, BackToPortalButton } from "./styles";
import React from "react";

function Custom404() {
  return (
    <>
      <ErrorTitle>Error 404!</ErrorTitle>
      <p>
        <BackToPortalButton to="/portal">Volver al portal</BackToPortalButton>
      </p>
    </>
  );
}

export default Custom404;
