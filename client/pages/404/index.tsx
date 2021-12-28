import type { NextPage } from "next";
import { ErrorTitle, BackToPortalButton } from "./styles";

const Custom404: NextPage = () => {
  return (
    <>
      <ErrorTitle>Error 404!</ErrorTitle>
      <p>
        <BackToPortalButton href="/portal">Volver al portal</BackToPortalButton>
      </p>
    </>
  );
};

export default Custom404;
