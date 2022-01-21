import {
  PageContainer,
  PageOrganizer,
} from "../../components/layouts/MainLayout/styles";
import Footer from "../../components/Footer";
import { SectionHeading } from "../../components/generalStyles/utils";
import { LoginWrapper, LoginInput, LoginButton, LoginLabel } from "./styles";
import { useAuth } from "../../contexts/AuthContext";
import { useLocation, useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import React from "react";

type FormData = {
  username: string;
  password: string;
  rememberMe: boolean;
};

function Login() {
  let navigate = useNavigate();
  let location = useLocation();
  let auth = useAuth();
  // @ts-ignore
  let from = location.state?.from?.pathname || "/";

  const { register, handleSubmit, reset } = useForm<FormData>({
    criteriaMode: "all",
  });
  const onSubmit = handleSubmit(({ username, password }: FormData) => {
    auth.signin(username, () => {
      navigate(from, { replace: true });
    });
  });

  return (
    <PageOrganizer>
      <PageContainer style={{ justifyContent: "center" }}>
        <LoginWrapper onSubmit={onSubmit}>
          <SectionHeading>Ingresar</SectionHeading>
          <LoginLabel htmlFor="username">Usuario</LoginLabel>
          <LoginInput type="text" {...register("username", {})} />
          <LoginLabel htmlFor="password">Contrasena</LoginLabel>
          <LoginInput type="password" {...register("password", {})} />
          <div
            style={{ display: "flex", alignItems: "center", margin: "10px 0" }}
          >
            <input type="checkbox" {...register("rememberMe", {})} />
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
