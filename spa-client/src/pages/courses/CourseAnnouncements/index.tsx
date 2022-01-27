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

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

type FormData = {
  title: string;
  content: string;
};

function CourseAnnouncements() {
  const { t } = useTranslation();
  const Course = useCourseData();
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
            {t('CourseAnnouncements.teacher.form.title')}
          </GeneralTitle>
          <FormLabel htmlFor="title">{t('CourseAnnouncements.teacher.form.announcementTitleLabel')}</FormLabel>
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
            <ErrorMessage>{t('CourseAnnouncements.teacher.error.title.isRequired')}</ErrorMessage>
          )}
          {errors.title?.type === "length" && (
            <ErrorMessage>
              {t('CourseAnnouncements.teacher.error.title.length')}
            </ErrorMessage>
          )}
          <FormLabel htmlFor="content">{t('CourseAnnouncements.teacher.form.announcementContentLabel')}</FormLabel>
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
            <ErrorMessage>{t('CourseAnnouncements.teacher.error.content.isRequired')}</ErrorMessage>
          )}
          {errors.content?.type === "length" && (
            <ErrorMessage>
              {t('CourseAnnouncements.teacher.error.content.length')}
            </ErrorMessage>
          )}
          <FormButton>{t('CourseAnnouncements.teacher.form.announcementCreateButton')}</FormButton>
        </FormWrapper>
        <Separator reduced={true}>.</Separator>
      </>
    );
  }

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t('Announcements.title')}
      </SectionHeading>
      {Course.isTeacher && renderTeacherForm()}
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        {announcements.length === 0 && (
          <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
            {t('Announcements.noAnnouncements')}
          </GeneralTitle>
        )}

        {announcements.map((announcement) => (
          <AnnouncementUnit
            course={Course}
            announcement={announcement}
            isTeacher={Course.isTeacher}
          />
        ))}

        <BasicPagination
          currentPage={currentPage}
          pageSize={pageSize}
          maxPage={maxPage}
          baseURL={`/course/${Course.courseId}/announcements`}
        />
      </div>
    </>
  );
}

export default CourseAnnouncements;
