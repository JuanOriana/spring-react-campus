import { AdminTitle, LinkRedirectionButton } from "./styles";
import React from "react";

function AdminPortal() {
  return (
    <>
      <AdminTitle>Centro de Administracion de Campus</AdminTitle>
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
          Crear nuevo usuario
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/new">
          Crear nuevo curso
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/select">
          Anadir usuario a curso
        </LinkRedirectionButton>
        <LinkRedirectionButton to="/admin/course/all">
          Ver todos los cursos
        </LinkRedirectionButton>
      </div>
    </>
  );
}

export default AdminPortal;
