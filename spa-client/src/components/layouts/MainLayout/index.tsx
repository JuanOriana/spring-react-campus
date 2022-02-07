import Navbar from "../../Navbar";
import Footer from "../../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
import { PageContainer, PageOrganizer } from "./styles";
// @ts-ignore

function MainLayout() {
  return (
    <PageOrganizer>
      <Navbar />
      <PageContainer>
        <Outlet />
      </PageContainer>
      <Footer />
    </PageOrganizer>
  );
}

export default MainLayout;
