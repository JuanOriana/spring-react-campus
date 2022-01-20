import React, { useEffect, useState } from "react";
import { usePagination } from "../../../hooks/usePagination";
import {
  GeneralTitle,
  SectionHeading,
  Separator,
} from "../../../components/generalStyles/utils";
import AnnouncementUnit from "../../../components/AnnouncementUnit";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import BasicPagination from "../../../components/BasicPagination";
import {
  FormWrapper,
  FormInput,
  FormButton,
  FormLabel,
  FormArea,
  ErrorMessage,
} from "../../../components/generalStyles/form";
import { useForm } from "react-hook-form";

type FormData = {
  title: string;
  content: string;
};

function CourseAnnouncements() {
  const { course, isTeacher } = useCourseData();
  const [announcements, setAnnouncements] = useState(new Array(0));
  const maxPage = 3;
  const [currentPage, pageSize] = usePagination(10);

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  useEffect(() => {
    setAnnouncements([
      {
        announcementId: 1,
        title: "Hola",
        content:
          "xAAdddxAAdddxAAdddxAAdddxAAdddxAAdddxAdvxAAdddxAAdddxAAdddxAAdddAdddxAAdddxAAddd",
        author: { name: "juan", surname: "oriana" },
        date: "hoy",
      },
    ]);
  }, []);

  function renderTeacherForm() {
    return (
      <>
        <FormWrapper reduced={true} acceptCharset="utf-8" onSubmit={onSubmit}>
          <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
            Nuevo Anuncio
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
          <FormLabel htmlFor="content">Contenido</FormLabel>
          <FormArea
            style={{ width: "95%", resize: "none" }}
            cols={50}
            rows={10}
            {...register("content", {
              required: true,
              validate: {
                length: (content) => content.length > 2,
              },
            })}
          ></FormArea>
          {errors.content?.type === "required" && (
            <ErrorMessage>El contentido es requerido</ErrorMessage>
          )}
          {errors.content?.type === "length" && (
            <ErrorMessage>
              El contenido debe tener mas de 2 caracteres de largo
            </ErrorMessage>
          )}
          <FormButton>Crear anuncio</FormButton>
        </FormWrapper>
        <Separator reduced={true}>.</Separator>
      </>
    );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Anuncios
      </SectionHeading>
      {isTeacher && renderTeacherForm()}
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        {announcements.length === 0 && (
          <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
            No hay anuncios
          </GeneralTitle>
        )}

        {announcements.map((announcement) => (
          <AnnouncementUnit
            course={course}
            announcement={announcement}
            isTeacher={isTeacher}
          />
        ))}

        <BasicPagination
          currentPage={currentPage}
          pageSize={pageSize}
          maxPage={maxPage}
          baseURL={`/course/${course.courseId}/announcements`}
        />
      </div>
    </>
  );
}

export default CourseAnnouncements;
