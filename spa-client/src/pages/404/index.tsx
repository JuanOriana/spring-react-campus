import { ErrorTitle, BackToPortalButton } from "./styles";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

function Custom404() {
    const { t } = useTranslation();
  return (
    <>
      <ErrorTitle>{t('404.title')}</ErrorTitle>
      <p>
        <BackToPortalButton to="/portal">{t('404.backToPortalButton')}</BackToPortalButton>
      </p>
    </>
  );
}

export default Custom404;
