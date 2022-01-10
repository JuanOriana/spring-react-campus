import Navbar from "../../Navbar";
import Footer from "../../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
import { PageContainer, PageOrganizer } from "./styles";

function MainLayout() {
  return (
    <PageOrganizer>
      <Navbar currentUser={{ isAdmin: false, image: {}, name: "Juan" }} />
      <PageContainer>
        <Outlet />
      </PageContainer>
      <Footer />
    </PageOrganizer>
  );
}

export default MainLayout;
