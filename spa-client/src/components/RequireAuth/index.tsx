import { useAuth } from "../../contexts/AuthContext";
import {
  useLocation,
  Navigate,
  useNavigate,
  Location,
  To,
} from "react-router-dom";
import React, { useEffect } from "react";

function getCorrectPrivilegeRoute(isAdmin: boolean, location: Location): To {
  const startsWithAdmin = location.pathname.startsWith("/admin");
  if ((isAdmin && startsWithAdmin) || (!isAdmin && !startsWithAdmin)) {
    return location;
  }
  if (isAdmin && !startsWithAdmin) {
    return "/admin/";
  }
  return "/";
}

function RequireAuth({ children }: { children: JSX.Element }) {
  const { user, signin } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const readUser = localStorage.getItem("user");
  const isAdmin = localStorage.getItem("isAdmin") === "true";
  const correctRoute = getCorrectPrivilegeRoute(isAdmin, location);

  useEffect(() => {
    if (readUser && readUser !== "")
      signin(readUser, () => navigate(correctRoute));
  }, [correctRoute, navigate, readUser, signin]);

  if (!user && !readUser) {
    // Redirect them to the /login page, but save the current location they were
    // trying to go to when they were redirected. This allows us to send them
    // along to that page after they login, which is a nicer user experience
    // than dropping them off on the home page.
    return <Navigate to="/login" state={{ from: correctRoute }} replace />;
  }

  //TODO: Might remove because of overhead
  if (getCorrectPrivilegeRoute(isAdmin, location) !== location) {
    return <Navigate to={correctRoute} />;
  }
  return children;
}

export default RequireAuth;
