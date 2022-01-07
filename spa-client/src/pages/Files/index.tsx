import { SectionHeading } from "../../components/generalStyles/utils";
import FileSearcher from "../../components/FileSearcher";
import React, { useEffect, useState } from "react";
import {
  PaginationArrow,
  PaginationWrapper,
} from "../../components/generalStyles/pagination";
import FileUnit from "../../components/FileUnit";
import { usePagination } from "../../hooks/usePagination";
import { BigWrapper, GeneralTitle } from "../../components/generalStyles/utils";
import { FileGrid } from "./styles";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
import { useNavigate } from "react-router-dom";

function Files() {
  const query = useQuery();
  const navigate = useNavigate();
  const [currentPage] = usePagination(10);
  const [files, setFiles] = useState(new Array(0));
  const orderDirection = getQueryOrDefault(query, "order-direction", "desc");
  const orderProperty = getQueryOrDefault(query, "order-property", "date");
  const maxPage = 3;

  useEffect(() => {
    setFiles([
      {
        fileId: 1,
        name: "archivoprueba",
        downloads: 10,
        categories: [{ name: "hola" }],
        extension: { fileExtensionName: "csv" },
        course: { courseId: 1, subject: { name: "paw" } },
      },
    ]);
  }, []);

  return (
    <>
      <SectionHeading>Archivos</SectionHeading>
      <BigWrapper>
        <FileSearcher
          orderDirection={orderDirection}
          orderProperty={orderProperty}
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
        <FileGrid>
          {files.length === 0 && (
            <GeneralTitle style={{ width: "100%", textAlign: "center" }}>
              No hay resultados!
            </GeneralTitle>
          )}
          {files.map((file) => (
            <FileUnit key={file.fileId} isGlobal={true} file={file} />
          ))}
        </FileGrid>
      </BigWrapper>
      <PaginationWrapper style={{ alignSelf: "center" }}>
        {currentPage > 1 && (
          <button
            onClick={() => {
              query.set("page", String(currentPage - 1));
              navigate(`/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              xRotated={true}
              src="/images/page-arrow.png"
              alt="Pagina previa"
            />
          </button>
        )}
        Pagina {currentPage} de {maxPage}
        {currentPage < maxPage && (
          <button
            onClick={() => {
              query.set("page", String(currentPage + 1));
              navigate(`/files?${query.toString()}`);
            }}
            style={{ background: "none", border: "none" }}
          >
            <PaginationArrow
              src="/images/page-arrow.png"
              alt="Pagina siguiente"
            />
          </button>
        )}
      </PaginationWrapper>
    </>
  );
}

export default Files;
