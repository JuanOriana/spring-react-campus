import AdminSectionsCol from "../../../components/AdminSectionsCol";
import {
  ErrorMessage,
  FormButton,
  FormInput,
  FormLabel,
  FormWrapper,
} from "../../../components/generalStyles/form";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import React, { useState } from "react";
import { useForm } from "react-hook-form";

type FormData = {
  fileNumber: number;
  name: string;
  surname: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
};

function AdminNewUser() {
  const [isFileNumberDuplicated, setIsFileNumberDuplicated] = useState(false);
  const [isUsernameDuplicated, setIsUsernameDuplicated] = useState(false);
  const [isEmailDuplicated, setIsEmailDuplicated] = useState(false);
  const nextFileNumber = 42;

  const {
    register,
    handleSubmit,
    reset,
    setError,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    console.log(data);
    if (data.password !== data.confirmPassword) {
      setError("confirmPassword", {
        type: "repeated",
        message: "La contrasenas deben concidir",
      });
    } else {
      reset();
    }
  });
  return (
    <>
      <AdminSectionsCol />
      <FormWrapper
        reduced={true}
        acceptCharset="utf-8"
        style={{ margin: "0px 40px 40px 40px" }}
        onSubmit={onSubmit}
        method="post"
      >
        <GeneralTitle
          className="announcement-title"
          style={{ color: "#176961", alignSelf: "center" }}
        >
          Crear nuevo usuario
        </GeneralTitle>
        <FormLabel htmlFor="fileNumber">Legajo</FormLabel>
        <FormInput
          type="number"
          min="0"
          style={{ fontSize: "26px" }}
          value={nextFileNumber}
          {...register("fileNumber", {
            required: true,
            min: 0,
          })}
        />
        {errors.fileNumber?.type === "required" && (
          <ErrorMessage>El legajo es requeriod</ErrorMessage>
        )}
        {errors.fileNumber?.type === "min" && (
          <ErrorMessage>El legajo debe ser un numero positivo</ErrorMessage>
        )}
        {isFileNumberDuplicated && (
          <ErrorMessage>El legajo ya existe</ErrorMessage>
        )}
        <FormLabel htmlFor="name">Nombre</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("name", {
            required: true,
            pattern: {
              value: /[a-zA-Z ']+/,
              message: "El nombre solo puede tener letras",
            },
          })}
        />
        {errors.name?.type === "required" && (
          <ErrorMessage>El nombre es requerido</ErrorMessage>
        )}
        {errors.name?.message && (
          <ErrorMessage> {errors.name?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="surname">Apellido</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("surname", {
            required: true,
            pattern: {
              value: /[a-zA-Z ']+/,
              message: "El apellido solo puede tener letras",
            },
          })}
        />
        {errors.surname?.type === "required" && (
          <ErrorMessage>El apellido es requerido</ErrorMessage>
        )}
        {errors.surname?.message && (
          <ErrorMessage> {errors.surname?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="username">Nombre de usuario</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("username", {
            required: true,
            validate: {
              length: (username) =>
                username.length >= 6 && username.length <= 50,
            },
            pattern: {
              value: /[a-zA-Z0-9]+/,
              message: "El nombre de usuario solo puede tener letras y numeros",
            },
          })}
        />
        {errors.username?.type === "required" && (
          <ErrorMessage>El nombre de usuario es requerido</ErrorMessage>
        )}
        {errors.username?.type === "length" && (
          <ErrorMessage>
            El largo del nombre de usuario debe estar entre 6 y 50 caracteres
          </ErrorMessage>
        )}
        {errors.username?.message && (
          <ErrorMessage> {errors.username?.message} </ErrorMessage>
        )}
        {isUsernameDuplicated && (
          <ErrorMessage>El nombre de usuario ya existe</ErrorMessage>
        )}
        <FormLabel htmlFor="email">Email</FormLabel>
        <FormInput
          type="email"
          style={{ fontSize: "26px" }}
          {...register("email", {
            required: true,
            pattern: {
              value:
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
              message: "El email debe tener un formato valido",
            },
          })}
        />
        {errors.email?.message && (
          <ErrorMessage> {errors.email?.message} </ErrorMessage>
        )}
        {isEmailDuplicated && <ErrorMessage>El email ya existe</ErrorMessage>}
        <FormLabel htmlFor="password">Contrasena</FormLabel>
        <FormInput
          type="password"
          style={{ fontSize: "26px" }}
          {...register("password", {
            required: true,
            validate: {
              length: (password) =>
                password.length >= 8 && password.length <= 50,
            },
            pattern: {
              value: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/,
              message:
                "La contrasena debe tener una mayuscula, una minsucula y un numero",
            },
          })}
        />
        {errors.password?.type === "required" && (
          <ErrorMessage>La contrasena es requerida</ErrorMessage>
        )}
        {errors.password?.type === "length" && (
          <ErrorMessage>
            El largo de la contrasena debe estar entre 8 y 50 caracteres
          </ErrorMessage>
        )}
        {errors.password?.message && (
          <ErrorMessage> {errors.password?.message} </ErrorMessage>
        )}
        <FormLabel htmlFor="confirmPassword">Confirmar contrasena</FormLabel>
        <FormInput
          type="password"
          style={{ fontSize: "26px" }}
          {...register("confirmPassword", {})}
        />
        {errors.confirmPassword?.message && (
          <ErrorMessage> {errors.confirmPassword?.message} </ErrorMessage>
        )}
        <FormButton>Crear</FormButton>
      </FormWrapper>
    </>
  );
}

export default AdminNewUser;
