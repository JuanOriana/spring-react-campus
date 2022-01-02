import React from "react";
import { FooterContainer } from "./styles";

function Footer() {
  return (
    <FooterContainer>
      Campus © {new Date().getFullYear()} - Todos los derechos reservados
    </FooterContainer>
  );
}

export default Footer;
