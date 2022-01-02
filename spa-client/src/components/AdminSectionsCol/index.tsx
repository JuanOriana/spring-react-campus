import React from "react";
import PropTypes, { InferProps } from "prop-types";
import { Section } from "../../../types";
import { useLocation } from 'react-router-dom'
import {Link} from "react-router-dom";
import {
  AdminSectionsColWrapper,
  AdminSectionsColTitle,
  AdminSectionsItem,
} from "./styles";

AdminSectionsCol.propTypes = {
  isSmall: PropTypes.bool,
};

function AdminSectionsCol({
  isSmall,
}: InferProps<typeof AdminSectionsCol.propTypes>) {
  const location = useLocation();
  const pathname = location?.pathname;
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
          <Link to={section.path}>{`› ${section.name}`}</Link>
        </AdminSectionsItem>
      ))}
    </AdminSectionsColWrapper>
  );
}

export default AdminSectionsCol;
