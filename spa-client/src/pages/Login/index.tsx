import {
  PageContainer,
  PageOrganizer,
} from "../../components/layouts/MainLayout/styles";
import Footer from "../../components/Footer";
import { SectionHeading } from "../../components/generalStyles/utils";
import { LoginWrapper, LoginInput, LoginButton, LoginLabel } from "./styles";

function Login() {
  return (
    <PageOrganizer>
      <PageContainer style={{ justifyContent: "center" }}>
        <LoginWrapper>
          <SectionHeading>Ingresar</SectionHeading>
          <LoginLabel htmlFor="username">Usuario</LoginLabel>
          <LoginInput type="text" id="username" name="username" />
          <LoginLabel htmlFor="password">Contrasena</LoginLabel>
          <LoginInput type="password" id="password" name="password" />
          <div
            style={{ display: "flex", alignItems: "center", margin: "10px 0" }}
          >
            <input type="checkbox" name="rememberMe" id="remember-me" />
            <label
              htmlFor="remember-me"
              style={{ color: "#176961", marginLeft: "5px" }}
            >
              Recuerdame
            </label>
          </div>
          {/*MANEJAR ERROR DEL LOGIN ACA! SET ERROR*/}
          <LoginButton>Ingresar</LoginButton>
        </LoginWrapper>
      </PageContainer>
      <Footer />
    </PageOrganizer>
  );
}

export default Login;
