import {
  BigWrapper,
  GeneralTitle,
  SectionHeading,
  Separator,
} from "../../../components/generalStyles/utils";
import {
  getQueryOrDefault,
  getQueryOrDefaultMultiple,
  useQuery,
} from "../../../hooks/useQuery";
import { useNavigate } from "react-router-dom";
import { usePagination } from "../../../hooks/usePagination";
import React, { useEffect, useState } from "react";
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
import { handleService } from "../../../scripts/handleService";
import { courseService, fileService } from "../../../services";
import LoadableData from "../../../components/LoadableData";
import { renderToast } from "../../../scripts/renderToast";
import { FileModel } from "../../../types";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

type FormData = {
  file: FileList;
  category: number;
};

function CourseFiles() {
  const { t } = useTranslation();
  const course = useCourseData();
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage, pageSize] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const [isLoading, setIsLoading] = useState(true);
  const [reload, setReload] = useState(false);
  const [maxPage, setMaxPage] = useState(1);
  const [categories, setCategories] = useState(new Array(0));
  const [extensions, setExtensions] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const queryStringed = getQueryOrDefault(query, "query", "");
  const extensionTypes = getQueryOrDefaultMultiple(query, "extension-type");
  const categoryTypes = getQueryOrDefaultMultiple(query, "category-type");

  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getFiles(
        course.courseId,
        categoryTypes.map((type) => parseInt(type)),
        extensionTypes.map((type) => parseInt(type)),
        queryStringed,
        orderProperty,
        orderDirection,
        currentPage,
        pageSize
      ),
      navigate,
      (fileData) => {
        setFiles(fileData ? fileData.getContent() : []);
        setMaxPage(fileData ? fileData.getMaxPage() : 1);
      },
      () => setIsLoading(false)
    );
  }, [currentPage, pageSize, reload, course.courseId]);

  useEffect(() => {
    handleService(
      fileService.getCategories(),
      navigate,
      (fileCategories) => {
        setCategories(fileCategories ? fileCategories.getContent() : []);
      },
      () => {
        return;
      }
    );
    handleService(
      fileService.getExtensions(),
      navigate,
      (fileExtensions) => {
        setExtensions(fileExtensions ? fileExtensions.getContent() : []);
      },
      () => {
        return;
      }
    );
  }, [navigate]);

  function onDelete(id: number) {
    if (!window.confirm("Elminar este archivo?")) return;
    fileService
      .deleteFile(id)
      .then(() => {
        renderToast("👑 Archivo eliminado exitosamente!", "success");
        setFiles((oldFiles) =>
          oldFiles.filter((file: FileModel) => file.fileId !== id)
        );
      })
      .catch(() =>
        renderToast("No se pudo borrar el archivo, intente de nuevo", "error")
      );
  }

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm<FormData>({ criteriaMode: "all" });
  const onSubmit = handleSubmit((data: FormData) => {
    courseService
      .newFile(course.courseId, data.file[0], data.category ? data.category : 1)
      .then((response) => {
        if (!response.hasFailed()) {
          renderToast("👑 Archivo creado exitosamente!", "success");
          navigate(
            `/course/${course.courseId}/files?page=1&pageSize=${pageSize}`
          );
          setReload(!reload);
          reset();
        } else {
          renderToast("No se pudo crear el archivo, intente de nuevo", "error");
        }
      })
      .catch(() =>
        renderToast("No se pudo crear el archivo, intente de nuevo", "error")
      );
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
            {t("CourseFiles.teacher.form.title")}
          </GeneralTitle>
          <FormLabel htmlFor="file">
            {t("CourseFiles.teacher.form.file")}
          </FormLabel>
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
            <ErrorMessage>
              {t("CourseFiles.teacher.error.file.isRequired")}
            </ErrorMessage>
          )}
          {errors.file?.type === "size" && (
            <ErrorMessage>
              {t("CourseFiles.teacher.error.file.size")}
            </ErrorMessage>
          )}
          <FormLabel htmlFor="categoryId">
            {t("CourseFiles.teacher.form.category")}
          </FormLabel>
          <FormSelect
            style={{ fontSize: "26px" }}
            {...register("category")}
            defaultValue={1}
          >
            {categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {t("Category." + category.categoryName)}
              </option>
            ))}
          </FormSelect>
          <FormButton>
            {t("CourseFiles.teacher.form.uploadFileButton")}
          </FormButton>
        </FormWrapper>
        <Separator reduced={true}>.</Separator>
      </>
    );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("CourseFiles.title")}
      </SectionHeading>
      {course.isTeacher && renderTeacherForm()}
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <LoadableData isLoading={isLoading}>
          <BigWrapper style={{ display: "flex", flexDirection: "column" }}>
            <FileSearcher
              orderDirection={orderDirection}
              orderProperty={orderProperty}
              categoryType={categoryTypes}
              categories={categories}
              extensionType={extensionTypes}
              extensions={extensions}
            />
            <FileGrid>
              {files.length === 0 && (
                <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
                  {t("CourseFiles.noResults")}
                </GeneralTitle>
              )}
              {files.map((file) => (
                <FileUnit
                  key={file.fileId}
                  isTeacher={course.isTeacher}
                  file={file}
                  onDelete={onDelete}
                />
              ))}
            </FileGrid>
          </BigWrapper>
          <PaginationWrapper style={{ alignSelf: "center" }}>
            {currentPage > 1 && (
              <button
                onClick={() => {
                  query.set("page", String(currentPage - 1));
                  navigate(
                    `/course/${course.courseId}/files?${query.toString()}`
                  );
                }}
                style={{ background: "none", border: "none" }}
              >
                <PaginationArrow
                  xRotated={true}
                  src="/images/page-arrow.png"
                  alt={t("BasicPagination.alt.beforePage")}
                />
              </button>
            )}
            {t("BasicPagination.message", {
              currentPage: currentPage,
              maxPage: maxPage,
            })}
            {currentPage < maxPage && (
              <button
                onClick={() => {
                  query.set("page", String(currentPage + 1));
                  navigate(
                    `/course/${course.courseId}/files?${query.toString()}`
                  );
                }}
                style={{ background: "none", border: "none" }}
              >
                <PaginationArrow
                  src="/images/page-arrow.png"
                  alt={t("BasicPagination.alt.nextPage")}
                />
              </button>
            )}
          </PaginationWrapper>
        </LoadableData>
      </div>
    </>
  );
}

export default CourseFiles;
