import "../styles/globals.css";
import type { AppProps } from "next/app";
import Fonts from "../components/Fonts";
import Layout from "../components/layouts/Main";
import { ThemeProvider } from "styled-components";

const theme = {
  cyanDarkest: "#176961",
  cyanDark: "#2EC4B6",
  cyanLight: "#CBF3F0",
  successBack: "#45FF9F",
  successFront: "#39614C",
};

function MyApp({ Component, pageProps, router }: AppProps) {
  return (
    <ThemeProvider theme={theme}>
      <Fonts />
      <Layout router={router}>
        <Component {...pageProps} key={router.route} />
      </Layout>
    </ThemeProvider>
  );
}

export default MyApp;
