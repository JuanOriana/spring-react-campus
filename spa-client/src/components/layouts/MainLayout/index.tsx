import { ThemeProvider } from "styled-components";
import Navbar from "../../Navbar";
import Footer from "../../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
import { PageContainer, PageOrganizer } from "./styles";

const theme = {
  cyanDarkest: "#176961",
  cyanDark: "#2EC4B6",
  cyanLight: "#CBF3F0",
  successBack: "#45FF9F",
  successFront: "#39614C",
};

function MainLayout() {
  return (
    <ThemeProvider theme={theme}>
      <PageOrganizer>
        <Navbar currentUser={{ isAdmin: false, image: {}, name: "Juan" }} />
        <PageContainer>
          <Outlet />
        </PageContainer>
        <Footer />
      </PageOrganizer>
    </ThemeProvider>
  );
}

export default MainLayout;
