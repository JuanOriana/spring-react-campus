import { ErrorTitle, BackToPortalButton } from "../404/styles";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
//

function Error() {
  const { t } = useTranslation();
  const query = useQuery();
  let error = getQueryOrDefault(query, "code", "404");
  if (error === "NaN") {
    error = "404";
  }
  return (
    <>
      <ErrorTitle>Error {error}</ErrorTitle>
      <p>
        <BackToPortalButton to="/portal">
          {t("404.backToPortalButton")}
        </BackToPortalButton>
      </p>
    </>
  );
}

export default Error;
