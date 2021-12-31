import type { NextPage } from "next";
import AnnouncementUnit from "../components/AnnouncementUnit";
import FileUnit from "../components/FileUnit";
import AdminSectionsCol from "../components/AdminSectionsCol";
import CourseSectionsCol from "../components/CourseSectionsCol";
import FileSearcher from "../components/FileSearcher";
const Portal: NextPage = () => {
  return (
    <div>
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
};

export default Portal;
