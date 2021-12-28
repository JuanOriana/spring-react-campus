import React from "react";
import Head from "next/head";
import Navbar from "../Navbar";
import PropTypes, { InferProps } from "prop-types";
import Footer from "../Footer";
import { PageContainer, PageOrganizer } from "./styles";

const MainPropTypes = {
  children: PropTypes.object,
  router: PropTypes.object,
};

const Main = ({ children, router }: InferProps<typeof MainPropTypes>) => {
  return (
    <div>
      <Head>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="description" content="Campus" />
        <meta name="author" content="Group 4" />
        <link rel="icon" href="/favicon.ico" type="image/x-icon" />
        <title>Campus</title>
        <meta property="og:type" content="website" />
        <meta property="og:site_name" content="Campus" />
      </Head>
      <PageOrganizer>
        <Navbar
          currentTab={1}
          currentUser={{ isAdmin: false, image: {}, name: "Juan" }}
          router={router}
        />
        <PageContainer>{children}</PageContainer>
        <Footer />
      </PageOrganizer>
    </div>
  );
};

Main.propTypes = MainPropTypes;

export default Main;
