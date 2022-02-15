import { AdminTitle, LinkRedirectionButton } from "./styles";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

function AdminPortal() {
  const { t } = useTranslation();
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        width: "100%",
      }}
    >
      <AdminTitle>{t("AdminPortal.title")}</AdminTitle>
      <div
        style={{
          display: "flex",
          flexWrap: "wrap",
          alignItems: "center",
          justifyContent: "space-around",
          width: "100%",
        }}
      >
        <LinkRedirectionButton to="/admin/user/new">
          {t("AdminPortal.createNewUserButton")}
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/new">
          {t("AdminPortal.createNewCourseButton")}
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/select">
          {t("AdminPortal.addUserToCourseButton")}
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/all">
          {t("AdminPortal.seeAllCoursesButton")}
        </LinkRedirectionButton>
      </div>
    </div>
  );
}

export default AdminPortal;
