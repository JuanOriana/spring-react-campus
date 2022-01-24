import React from "react";
import { FooterContainer } from "./styles";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

function Footer() {
    const { t } = useTranslation();
  return (
    <FooterContainer>
        {t('Footer', {year: new Date().getFullYear()})}
    </FooterContainer>
  );
}

export default Footer;
