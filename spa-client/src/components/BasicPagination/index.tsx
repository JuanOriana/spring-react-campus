import React from "react";
import PropTypes, { InferType } from "prop-types";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../generalStyles/pagination";
import { Link } from "react-router-dom";

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
  return (
    <PaginationWrapper style={{ ...style }}>
      {currentPage > 1 && (
        <Link to={`${baseURL}?page=${currentPage - 1}&pageSize=${pageSize}`}>
          <PaginationArrow
            xRotated={true}
            src="/images/page-arrow.png"
            alt="Pagina previa"
          />
        </Link>
      )}
      Pagina {currentPage} de {maxPage}
      {currentPage < maxPage && (
        <Link to={`${baseURL}?page=${currentPage + 1}&pageSize=${pageSize}`}>
          <PaginationArrow
            src="/images/page-arrow.png"
            alt="Siguiente pagina"
          />
        </Link>
      )}
    </PaginationWrapper>
  );
}

export default BasicPagination;
