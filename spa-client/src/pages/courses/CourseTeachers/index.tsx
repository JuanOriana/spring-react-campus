import {
  BigWrapper,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import { Link, useNavigate } from "react-router-dom";
import { MailIcon, TeacherIcon, TeacherUnit } from "./styles";
import React, { useEffect, useState } from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
import { handleService } from "../../../scripts/handleService";
import { courseService } from "../../../services";
import LoadableData from "../../../components/LoadableData";
//

function CourseTeachers() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [teachers, setTeachers] = useState(new Array(1));
  const [isLoading, setIsLoading] = useState(false);
  const course = useCourseData();
  useEffect(() => {
    setIsLoading(true);
    handleService(
      courseService.getTeachers(course.courseId),
      navigate,
      (teacherData) => {
        setTeachers(teacherData ? teacherData.getContent() : []);
      },
      () => setIsLoading(false)
    );
  }, []);

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
        {t("CourseTeachers.title")}
      </SectionHeading>
      <BigWrapper>
        <LoadableData isLoading={isLoading}>
          {teachers.map((teacher) => (
            <TeacherUnit>
              {!teacher.image && (
                <TeacherIcon
                  alt={`${teacher.name} ${teacher.surname}`}
                  src="/resources/images/default-user-image.png"
                />
              )}
              {teacher.image && (
                <TeacherIcon
                  alt={`${teacher.name} ${teacher.surname}`}
                  src={`/user/profile-image/${teacher.userId}`}
                />
              )}
              <div
                style={{
                  display: "flex",
                  width: "200px",
                  flexDirection: "column",
                }}
              >
                <p>
                  {teacher.name} {teacher.surname}
                </p>
                <p>{teacher.email}</p>
              </div>
              <Link to={`/course/${course.courseId}/mail/${teacher.userId}`}>
                <MailIcon
                  alt={t("CourseTeachers.alt.mail")}
                  title={t("CourseTeachers.alt.title")}
                  src="https://i.pinimg.com/originals/3a/4e/95/3a4e95aa862636d6f22c95fded897f94.jpg"
                />
              </Link>
            </TeacherUnit>
          ))}
        </LoadableData>
      </BigWrapper>
    </>
  );
}

export default CourseTeachers;
