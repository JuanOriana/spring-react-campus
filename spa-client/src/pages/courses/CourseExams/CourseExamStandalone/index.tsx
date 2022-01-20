import TeacherCourseExamStandalone from "./TeacherCourseExamStandalone";
import StudentCourseExamStandalone from "./StudentCourseExamStandalone";
import { useCourseData } from "../../../../components/layouts/CourseLayout";

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
