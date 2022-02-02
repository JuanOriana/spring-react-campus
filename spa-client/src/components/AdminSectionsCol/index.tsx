import React from "react";
import PropTypes, { InferProps } from "prop-types";
import type Section from "../../types/Section";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  AdminSectionsColWrapper,
  AdminSectionsColTitle,
  AdminSectionsItem,
} from "./styles";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

AdminSectionsCol.propTypes = {
  isSmall: PropTypes.bool,
};

function AdminSectionsCol({
  isSmall,
}: InferProps<typeof AdminSectionsCol.propTypes>) {
  const { t } = useTranslation();
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
      <AdminSectionsColTitle>{t('AdminSectionCol.title')}</AdminSectionsColTitle>
      {sections.map((section) => (
        <AdminSectionsItem
          isActive={pathname === section.path}
          key={section.path}
        >
          <Link to={section.path}>{'> ' + t( 'AdminSectionCol.' + section.name )}</Link>
        </AdminSectionsItem>
      ))}
    </AdminSectionsColWrapper>
  );
}

export default AdminSectionsCol;
