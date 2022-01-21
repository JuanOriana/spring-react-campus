import { useCourseData } from "../../../components/layouts/CourseLayout";
import TeacherExams from "./TeacherExams";
import StudentExams from "./StudentExams";
import React from "react";

function CourseExams() {
  const { isTeacher } = useCourseData();
  return (
    <>
      {isTeacher && <TeacherExams />}
      {!isTeacher && <StudentExams />}
    </>
  );
}

export default CourseExams;
