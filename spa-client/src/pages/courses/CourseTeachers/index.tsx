import {
  BigWrapper,
  SectionHeading,
} from "../../../components/generalStyles/utils";
import { useCourseData } from "../../../components/layouts/CourseLayout";
import { Link } from "react-router-dom";
import { MailIcon, TeacherIcon, TeacherUnit } from "./styles";
import React from "react";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../../common/i18n/index";
//

interface Teacher {
  image: FileList | null;
  userId: number;
  name: string;
  surname: string;
  email: string;
}
function CourseTeachers() {
    const { t } = useTranslation();
  const teachers: Map<Teacher, number> = new Map();
  teachers.set(
    {
      image: null,
      userId: 1,
      name: "Juan",
      surname: "Doe",
      email: "jdoe@itba.edu.ar",
    },
    1
  );
  const Course = useCourseData();

  return (
    <>
      <SectionHeading style={{ margin: "0 0 20px 20px" }}>
          {t('CourseTeachers.title')}
      </SectionHeading>
      <BigWrapper>
        {Array.from(teachers.keys(), (teacher: Teacher) => (
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
            <Link to={`/course/${Course.courseId}/mail/${teacher.userId}`}>
              <MailIcon
                alt={t('CourseTeachers.alt.mail')}
                title={t('CourseTeachers.alt.title')}
                src="https://i.pinimg.com/originals/3a/4e/95/3a4e95aa862636d6f22c95fded897f94.jpg"
              />
            </Link>
          </TeacherUnit>
        ))}
      </BigWrapper>
    </>
  );
}

export default CourseTeachers;
