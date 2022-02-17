import { ErrorTitle, BackToPortalButton } from "../404/styles";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
//

function Error() {
  const { t } = useTranslation();
  const query = useQuery();
  const navigate = useNavigate();
  let auth = useAuth();

  let error = getQueryOrDefault(query, "code", "404");
  if (error === "401") {
    auth.signout(() => navigate("/"));
  }
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
