import React from "react";
import CourseSectionsCol from "../../components/CourseSectionsCol";
import AdminSectionsCol from "../../components/AdminSectionsCol";
import FileUnit from "../../components/FileUnit";
import ExamUnit from "../../components/ExamUnit";
import StudentExamUnit from "../../components/StudentExamUnit";

function Portal() {
  return (
    <div>
      <ExamUnit
        exam={{ title: "hola", examId: 1 }}
        average={10}
        userCount={18}
        examsSolved={10}
        isTeacher={false}
        isDelivered={true}
        answer={{ score: 10, corrections: "nada" }}
      />
      <StudentExamUnit
        answer={{
          answerId: 1,
          deliveredDate: "hoy",
          student: { name: "pable", surname: "perez" },
          score: 10,
        }}
        examId={1}
        isCorrected={true}
      />

      <CourseSectionsCol
        courseId={1}
        courseName={"PAW"}
        board={"F"}
        code={"98.2"}
        quarter={1}
        year={2012}
      />
      <AdminSectionsCol />

      <FileUnit
        isTeacher={false}
        isGlobal={true}
        isMinimal={false}
        file={{
          fileId: 1,
          name: "archivoprueba",
          downloads: 10,
          categories: [{ name: "hola" }],
          extension: { fileExtensionName: "csv" },
          course: { courseId: 1, subject: { name: "paw" } },
        }}
      />
    </div>
  );
}

export default Portal;
