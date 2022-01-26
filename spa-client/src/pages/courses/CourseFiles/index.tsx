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

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

type FormData = {
  file: FileList;
  //DEBERIA SER UN ENUM
  category: object;
};

function CourseFiles() {
  const { t } = useTranslation();
  const { course, isTeacher } = useCourseData();
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const maxPage = 3;
  const categories = [
    { categoryName: "practice", categoryId: 1 },
    { categoryName: "exam", categoryId: 2 },
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
            {t('CourseFiles.teacher.form.title')}
          </GeneralTitle>
          <FormLabel htmlFor="file">{t('CourseFiles.teacher.form.file')}</FormLabel>
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
            <ErrorMessage>{t('CourseFiles.teacher.error.file.isRequired')}</ErrorMessage>
          )}
          {errors.file?.type === "size" && (
            <ErrorMessage>{t('CourseFiles.teacher.error.file.size')}</ErrorMessage>
          )}
          <FormLabel htmlFor="categoryId">{t('CourseFiles.teacher.form.category')}</FormLabel>
          <FormSelect style={{ fontSize: "26px" }}>
            {categories.map((category) => (
              <option value={category.categoryId}>
                {t('Category.' + category.categoryName)}
              </option>
            ))}
          </FormSelect>
          <FormButton>{t('CourseFiles.teacher.form.uploadFileButton')}</FormButton>
        </FormWrapper>
        <Separator reduced={true}>.</Separator>
      </>
    );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t('CourseFiles.title')}
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
              {t('CourseFiles.noResults')}
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
              alt={t('BasicPagination.alt.beforePage')}
            />
          </button>
        )}
        {t('BasicPagination.message', {currentPage: currentPage, maxPage: maxPage})}
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
              alt={t('BasicPagination.alt.nextPage')}
            />
          </button>
        )}
      </PaginationWrapper>
    </>
  );
}

export default CourseFiles;
