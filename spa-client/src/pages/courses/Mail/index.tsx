import {
  FormArea,
  FormButton,
  FormInput,
  FormWrapper,
  FormLabel,
  ErrorMessage,
} from "../../../components/generalStyles/form";
import { GeneralTitle } from "../../../components/generalStyles/utils";
import { useForm } from "react-hook-form";
import React from "react";

const toUser = {
  name: "Juan",
  surname: "Doe",
  userId: 1,
  username: "jdoe",
  email: "jdoe@itba.edu.ar",
  fileNumber: 60000,
};

type FormData = {
  subject: string;
  content: string;
};

function Mail() {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  return (
    <FormWrapper acceptCharset="utf-8" onSubmit={onSubmit}>
      <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
        {`Enviar mail a ${toUser.name} ${toUser.surname}`}
      </GeneralTitle>
      <FormLabel htmlFor="subject">Asunto</FormLabel>

      <FormInput
        type="text"
        style={{ fontSize: "26px" }}
        {...register("subject", {
          required: true,
        })}
      />
      {errors.subject?.type === "required" && (
        <ErrorMessage>El asunto es requerido</ErrorMessage>
      )}
      <FormLabel htmlFor="content">Contenido</FormLabel>
      <FormArea
        style={{ width: "100%", resize: "none" }}
        cols={50}
        rows={10}
        {...register("content", {
          required: true,
        })}
      ></FormArea>
      {errors.content?.type === "required" && (
        <ErrorMessage>El contenido es requerido</ErrorMessage>
      )}
      <FormButton>Enviar</FormButton>
    </FormWrapper>
  );
}

export default Mail;
