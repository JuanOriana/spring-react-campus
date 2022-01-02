import React from "react";
import FileSearcher from "../../components/FileSearcher";
import CourseSectionsCol from "../../components/CourseSectionsCol";
import AdminSectionsCol from "../../components/AdminSectionsCol";
import AnnouncementUnit from "../../components/AnnouncementUnit";
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
      <FileSearcher
        orderDirection={"desc"}
        orderProperty={"date"}
        categoryType={[1]}
        categories={[
          { categoryName: "Hola", categoryId: 1 },
          { categoryName: "Dos", categoryId: 2 },
        ]}
        extensionType={[2]}
        extensions={[
          { fileExtensionName: "Otros", fileExtensionId: 1 },
          { fileExtensionName: "Hola", fileExtensionId: 2 },
          { fileExtensionName: "Dos", fileExtensionId: 3 },
        ]}
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
      <AnnouncementUnit
        course={{ courseId: 2 }}
        isGlobal={true}
        isTeacher={false}
        announcement={{
          title: "Hola",
          content:
            "xAAdddxAAdddxAAdddxAAdddxAAdddxAAdddxAdvxAAdddxAAdddxAAdddxAAdddAdddxAAdddxAAddd",
          author: { name: "juan", surname: "oriana" },
          date: "hoy",
        }}
      />
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
