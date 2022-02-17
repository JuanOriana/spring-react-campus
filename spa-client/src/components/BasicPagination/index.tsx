import React from "react";
import PropTypes, { InferType } from "prop-types";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../generalStyles/pagination";
import { Link } from "react-router-dom";

// i18next imports
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
//

BasicPagination.propTypes = {
  currentPage: PropTypes.number.isRequired,
  pageSize: PropTypes.number.isRequired,
  maxPage: PropTypes.number.isRequired,
  baseURL: PropTypes.string.isRequired,
  style: PropTypes.object,
};

function BasicPagination({
  currentPage,
  pageSize,
  maxPage,
  baseURL,
  style,
}: InferType<typeof BasicPagination.propTypes>) {
  const { t } = useTranslation();
  return (
    <PaginationWrapper style={{ ...style }}>
      {currentPage > 1 && (
        <Link
          to={`${baseURL}?page=${currentPage - 1}&pageSize=${pageSize}`}
          style={{ alignItems: "center", display: "flex" }}
        >
          <PaginationArrow
            xRotated={true}
            src="./images/page-arrow.png"
            alt={`${t("BasicPagination.alt.beforePage")}`}
          />
        </Link>
      )}
      {t("BasicPagination.message", {
        currentPage: currentPage,
        maxPage: maxPage,
      })}
      {currentPage < maxPage && (
        <Link
          to={`${baseURL}?page=${currentPage + 1}&pageSize=${pageSize}`}
          style={{ alignItems: "center", display: "flex" }}
        >
          <PaginationArrow
            src="./images/page-arrow.png"
            alt={`${t("BasicPagination.alt.nextPage")}`}
          />
        </Link>
      )}
    </PaginationWrapper>
  );
}

export default BasicPagination;
