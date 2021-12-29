import type { NextPage } from "next";
import AnnouncementUnit from "../components/AnnouncementUnit";
import FileUnit from "../components/FileUnit";
import AdminSectionsCol from "../components/AdminSectionsCol";
const Portal: NextPage = () => {
  return (
    <div>
      Hola
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
