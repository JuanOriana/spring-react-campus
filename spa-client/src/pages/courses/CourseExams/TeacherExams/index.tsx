import {
  BigWrapper,
  GeneralTitle,
  SectionHeading,
  Separator,
} from "../../../../components/generalStyles/utils";
import {
  FormWrapper,
  FormInput,
  FormLabel,
  FormArea,
  FormButton,
  ErrorMessage,
} from "../../../../components/generalStyles/form";

import ExamUnit from "../../../../components/ExamUnit";
import { useForm } from "react-hook-form";
import React, { useState } from "react";

type FormData = {
  title: string;
  content: string;
  file: FileList;
  startTime: Date;
  endTime: Date;
};
function TeacherExams() {
  const exams = [{ examId: 1, title: "Examen" }];
  const [isBefore, setIsBefore] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    setIsBefore(false);
    if (data.endTime < data.startTime) setIsBefore(true);
    reset();
  });

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Examenes
      </SectionHeading>
      <FormWrapper
        reduced={true}
        encType="multipart/form-data"
        acceptCharset="utf-8"
        onSubmit={onSubmit}
      >
        <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
          Crear examen
        </GeneralTitle>
        <FormLabel htmlFor="title">Titulo</FormLabel>
        <FormInput
          type="text"
          style={{ fontSize: "26px" }}
          {...register("title", {
            required: true,
            validate: {
              length: (content) => content.length > 2 && content.length < 50,
            },
          })}
        />
        {errors.title?.type === "required" && (
          <ErrorMessage>El titulo es requerido</ErrorMessage>
        )}
        {errors.title?.type === "length" && (
          <ErrorMessage>
            El titulo debe tener entre 2 y 50 caracteres de largo
          </ErrorMessage>
        )}
        <FormLabel htmlFor="content">Instrucciones</FormLabel>
        <FormArea
          style={{ width: "95%", resize: "none" }}
          cols={50}
          rows={10}
          {...register("content", {
            required: true,
            validate: {
              length: (content) => content.length > 2 && content.length < 50,
            },
          })}
        ></FormArea>
        {errors.content?.type === "required" && (
          <ErrorMessage>El contenido es requerido</ErrorMessage>
        )}
        {errors.content?.type === "length" && (
          <ErrorMessage>
            El contenido debe tener entre 2 y 50 caracteres de largo
          </ErrorMessage>
        )}
        <FormLabel htmlFor="file">Archivo</FormLabel>
        <FormInput
          type="file"
          style={{ fontSize: "26px" }}
          {...register("file", {
            validate: {
              required: (file) => file !== undefined && file[0] !== undefined,

              size: (file) =>
                file && file[0] && file[0].size / (1024 * 1024) < 50,
            },
          })}
        />
        {errors.file?.type === "required" && (
          <ErrorMessage>El archivo es requerido</ErrorMessage>
        )}
        {errors.file?.type === "size" && (
          <ErrorMessage>El archivo debe ser mas pequeño que 50mb</ErrorMessage>
        )}
        <FormLabel htmlFor="startTime">Inicio</FormLabel>
        <FormInput
          type="datetime-local"
          style={{ fontSize: "26px" }}
          min={Date.now()}
          {...register("startTime", {
            required: true,
          })}
        />
        {errors.startTime?.type === "required" && (
          <ErrorMessage>La fecha de inicio es requerida</ErrorMessage>
        )}
        <FormLabel htmlFor="endTime">Final</FormLabel>
        <FormInput
          type="datetime-local"
          style={{ fontSize: "26px" }}
          min={Date.now()}
          {...register("endTime", {
            required: true,
          })}
        />
        {errors.endTime?.type === "required" && (
          <ErrorMessage>La fecha de final es requerida</ErrorMessage>
        )}
        {isBefore && (
          <ErrorMessage>
            La fecha de inicio debe ser previa la final
          </ErrorMessage>
        )}
        <FormButton>Crear</FormButton>
      </FormWrapper>
      <Separator reduced={true}>.</Separator>

      <BigWrapper>
        <h3 style={{ margin: "10px 0" }}>Examenes recientes</h3>
        {exams.length === 0 && <p>No hay examenes</p>}
        {exams.map((exam) => (
          //EXAMS SOLVED Y USERCOUNT SALE DE QUE EXAMS EN VERDAD ES UN MAPA
          <ExamUnit
            exam={exam}
            isTeacher={true}
            examsSolved={7}
            userCount={30}
            average={8}
          />
        ))}
      </BigWrapper>
    </>
  );
}

export default TeacherExams;
