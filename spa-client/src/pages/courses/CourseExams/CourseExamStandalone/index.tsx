import TeacherCourseExamStandalone from "./TeacherCourseExamStandalone";
import StudentCourseExamStandalone from "./StudentCourseExamStandalone";
import { useCourseData } from "../../../../components/layouts/CourseLayout";
import React from "react";

function CourseExamStandalone() {
  const { isTeacher } = useCourseData();
  return (
    <>
      {isTeacher && <TeacherCourseExamStandalone />}
      {!isTeacher && <StudentCourseExamStandalone />}
    </>
  );
}

export default CourseExamStandalone;
