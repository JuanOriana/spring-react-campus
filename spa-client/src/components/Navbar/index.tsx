import React from "react";
import PropTypes, { InferProps } from "prop-types";
import { Link, useLocation, useNavigate } from "react-router-dom";
import type Section from "../../types/Section";

import {
  LogoutButton,
  NavContainer,
  NavSectionItem,
  NavSectionsContainer,
  NavTitle,
  UserWrapper,
} from "./styles";
import { useAuth } from "../../contexts/AuthContext";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

Navbar.propTypes = {
  currentUser: PropTypes.shape({
    isAdmin: PropTypes.bool,
    image: PropTypes.object,
    name: PropTypes.string,
    userId: PropTypes.number,
  }),
};

function Navbar({ currentUser }: InferProps<typeof Navbar.propTypes>) {
  const { t } = useTranslation();
  let navigate = useNavigate();
  let location = useLocation();
  let auth = useAuth();
  let { user } = useAuth();
  const pathname = location?.pathname;
  const sections: Section[] = [
    { path: "/portal", name: "Mis cursos" },
    { path: "/announcements", name: "Mis anuncios" },
    { path: "/files", name: "Mis archivos" },
    { path: "/timetable", name: "Mis horarios" },
  ];
  return (
    <NavContainer>
      <NavTitle>
        <Link to={user?.isAdmin ? "/admin" : "/portal"}>CAMPUS</Link>
      </NavTitle>
      {currentUser && (
        <>
          {!currentUser.isAdmin && (
            <NavSectionsContainer>
              {sections.map((section) => (
                <NavSectionItem
                  active={pathname === section.path}
                  key={section.path}
                >
                  <Link to={section.path}>{t('Navbar.sections.' + section.path)}</Link>
                </NavSectionItem>
              ))}
            </NavSectionsContainer>
          )}
          <UserWrapper>
            <Link to="/user">
              {!currentUser?.image && (
                <img src="/images/default-user-image.png" />
              )}
              {currentUser?.image && (
                <img src={`/user/profile-image/${currentUser.userId}`} />
              )}
              <h4>{currentUser?.name}</h4>
            </Link>
            <LogoutButton
              onClick={() => {
                auth.signout(() => navigate("/"));
              }}
            >
              {" "}
              {t('Navbar.logout')}
            </LogoutButton>
          </UserWrapper>
        </>
      )}
      {!currentUser && <div style={{ width: "120 px" }}></div>}
    </NavContainer>
  );
}

export default Navbar;
