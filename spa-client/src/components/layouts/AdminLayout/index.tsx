import Navbar from "../../Navbar";
import Footer from "../../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
import { PageContainer, PageOrganizer } from "../MainLayout/styles";

function AdminLayout() {
  return (
    <PageOrganizer>
      <Navbar />
      <PageContainer style={{ flexDirection: "row", alignItems: "start" }}>
        <Outlet />
      </PageContainer>
      <Footer />
    </PageOrganizer>
  );
}

export default AdminLayout;
