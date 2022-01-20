import {
  BigWrapper,
  GeneralTitle,
  SectionHeading,
  Separator,
} from "../../../components/generalStyles/utils";
import { getQueryOrDefault, useQuery } from "../../../hooks/useQuery";
import { useNavigate } from "react-router-dom";
import { usePagination } from "../../../hooks/usePagination";
import React, { useState } from "react";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import FileSearcher from "../../../components/FileSearcher";
import { FileGrid } from "../../Files/styles";
import FileUnit from "../../../components/FileUnit";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../../components/generalStyles/pagination";
import { useForm } from "react-hook-form";
import {
  ErrorMessage,
  FormButton,
  FormInput,
  FormLabel,
  FormSelect,
  FormWrapper,
} from "../../../components/generalStyles/form";

type FormData = {
  file: FileList;
  //DEBERIA SER UN ENUM
  category: object;
};

function CourseFiles() {
  const { course, isTeacher } = useCourseData();
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const maxPage = 3;
  const categories = [
    { categoryName: "Hola", categoryId: 1 },
    { categoryName: "Dos", categoryId: 2 },
  ];

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    reset();
  });

  function renderTeacherForm() {
    return (
      <>
        <FormWrapper
          encType="multipart/form-data"
          acceptCharset="utf-8"
          onSubmit={onSubmit}
        >
          <GeneralTitle style={{ color: "#176961", alignSelf: "center" }}>
            Nuevo archivo
          </GeneralTitle>
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
            <ErrorMessage>El archivo debe ser mas chico que 50mb</ErrorMessage>
          )}
          <FormLabel htmlFor="categoryId">Categoria</FormLabel>
          <FormSelect style={{ fontSize: "26px" }}>
            {categories.map((category) => (
              <option value={category.categoryId}>
                {category.categoryName}
              </option>
            ))}
          </FormSelect>
          <FormButton>Crear Archivo</FormButton>
        </FormWrapper>
        <Separator reduced={true}>.</Separator>
      </>
    );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        Archivos
      </SectionHeading>
      {isTeacher && renderTeacherForm()}
      <BigWrapper style={{ display: "flex", flexDirection: "column" }}>
        <FileSearcher
          orderDirection={orderDirection}
          orderProperty={orderProperty}
          categoryType={[1]}
          categories={categories}
          extensionType={[2]}
          extensions={[
            { fileExtensionName: "Otros", fileExtensionId: 1 },
            { fileExtensionName: "Hola", fileExtensionId: 2 },
            { fileExtensionName: "Dos", fileExtensionId: 3 },
          ]}
        />
        <FileGrid>
          {files.length === 0 && (
            <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
              No hay resultados!
            </GeneralTitle>
          )}
          {files.map((file) => (
            <FileUnit key={file.fileId} isTeacher={isTeacher} file={file} />
          ))}
        </FileGrid>
      </BigWrapper>
      <PaginationWrapper style={{ alignSelf: "center" }}>
        {currentPage > 1 && (
          <button
            onClick={() => {
              query.set("page", String(currentPage - 1));
              navigate(`/course/${course.courseId}/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt="Pagina previa"
            />
          </button>
        )}
        Pagina {currentPage} de {maxPage}
        {currentPage < maxPage && (
          <button
            onClick={() => {
              query.set("page", String(currentPage + 1));
              navigate(`/course/${course.courseId}/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt="Pagina siguiente"
            />
          </button>
        )}
      </PaginationWrapper>
    </>
  );
}

export default CourseFiles;
