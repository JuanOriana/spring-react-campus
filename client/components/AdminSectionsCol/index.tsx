import React from "react";
import PropTypes, { InferProps } from "prop-types";
import {
  AdminSectionsColWrapper,
  AdminSectionsColTitle,
  AdminSectionsItem,
} from "./styles";
import Link from "next/link";
import { useRouter } from "next/router";

AdminSectionsCol.propTypes = {
  isSmall: PropTypes.bool,
};

interface Section {
  path: string;
  name: string;
}

function AdminSectionsCol({
  isSmall,
}: InferProps<typeof AdminSectionsCol.propTypes>) {
  const router = useRouter();
  const pathname = router?.pathname;
  const sections: Section[] = [
    { path: "/admin/user/new", name: "Nuevo usuario" },
    { path: "/admin/course/new", name: "Nuevo curso" },
    { path: "/admin/course/select", name: "Agregar usuario a curso" },
    { path: "/admin/course/all", name: "Ver todos los cursos" },
  ];
  return (
    <AdminSectionsColWrapper isSmall={isSmall!}>
      <AdminSectionsColTitle>Administracion</AdminSectionsColTitle>
      {sections.map((section) => (
        <AdminSectionsItem
          isActive={pathname === section.path}
          key={section.path}
        >
          <Link href={section.path}>{`› ${section.name}`}</Link>
        </AdminSectionsItem>
      ))}
    </AdminSectionsColWrapper>
  );
}

export default AdminSectionsCol;
